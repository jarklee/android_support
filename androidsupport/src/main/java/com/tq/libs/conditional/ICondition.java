/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.conditional;

import android.support.annotation.CheckResult;

import com.tq.libs.exception.ParameterException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public interface ICondition<T> {

    boolean satisfyToCondition(T object);

    @CheckResult
    List<T> filter(Collection<T> collection);

    abstract class BaseCondition<T> implements ICondition<T> {
        @Override
        public List<T> filter(Collection<T> collection) {
            List<T> temp = new ArrayList<>();
            for (T obj : collection) {
                if (satisfyToCondition(obj)) {
                    temp.add(obj);
                }
            }
            return temp;
        }
    }

    interface ILogicCondition<T> extends ICondition<T> {
        void addCondition(ICondition<T> condition);

        void removeCondition(ICondition<T> condition);
    }

    abstract class BaseLogicCondition<T> implements ILogicCondition<T> {
        protected List<ICondition<T>> conditions;

        protected static final int MIN_FILTER = 100;

        public BaseLogicCondition() {
            conditions = new ArrayList<>();
        }

        @Override
        public void addCondition(ICondition<T> condition) {
            if (condition == null) {
                throw new ParameterException("Condition could not be null");
            }
            synchronized (this) {
                if (!conditions.contains(condition)) {
                    conditions.add(condition);
                }
            }
        }

        @Override
        public synchronized void removeCondition(ICondition<T> condition) {
            synchronized (this) {
                conditions.remove(condition);
            }
        }

        @Override
        public List<T> filter(Collection<T> collection) {
            if (collection == null) {
                return null;
            }
            if (collection.size() > MIN_FILTER) {
                return threadFilter(collection);
            } else {
                return traditionalFilter(collection);
            }
        }

        private List<T> traditionalFilter(Collection<T> collection) {
            List<T> temp = new ArrayList<>();
            for (T obj : collection) {
                if (satisfyToCondition(obj)) {
                    temp.add(obj);
                }
            }
            return temp;
        }

        private List<T> threadFilter(Collection<T> collection) {
            Collection<ConditionCheckerCallable<T>> checkerJobs = new ArrayList<>();
            for (T obj : collection) {
                ConditionCheckerCallable<T> task = new ConditionCheckerCallable<>(this, obj);
                checkerJobs.add(task);
            }
            List<T> temp = new ArrayList<>();
            ExecutorService service = Executors.newFixedThreadPool(5);
            try {
                List<Future<T>> resultTasks = service.invokeAll(checkerJobs);
                for (Future<T> resultTask : resultTasks) {
                    T obj = resultTask.get();
                    if (obj != null) {
                        temp.add(obj);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            service.shutdown();
            return temp;
        }
    }

    class AndLogicCondition<T> extends BaseLogicCondition<T> {

        @Override
        public boolean satisfyToCondition(T object) {
            Collection<ICondition<T>> conditions;
            synchronized (this) {
                conditions = new ArrayList<>(this.conditions);
            }
            boolean isConformed = true;
            for (ICondition<T> condition : conditions) {
                if (!condition.satisfyToCondition(object)) {
                    isConformed = false;
                    break;
                }
            }
            return isConformed;
        }
    }

    class OrLogicCondition<T> extends BaseLogicCondition<T> {

        @Override
        public boolean satisfyToCondition(T object) {
            Collection<ICondition<T>> conditions;
            synchronized (this) {
                conditions = new ArrayList<>(this.conditions);
            }
            boolean isConformed = false;
            for (ICondition<T> condition : conditions) {
                if (condition.satisfyToCondition(object)) {
                    isConformed = true;
                    break;
                }
            }
            return isConformed;
        }
    }

    class ConditionCheckerCallable<T> implements Callable<T> {

        private final ICondition<T> conditionChecker;
        private final T obj;

        public ConditionCheckerCallable(ICondition<T> conditionChecker, T obj) {
            this.conditionChecker = conditionChecker;
            this.obj = obj;
        }

        @Override
        public T call() throws Exception {
            if (conditionChecker.satisfyToCondition(obj)) {
                return obj;
            }
            return null;
        }
    }
}
