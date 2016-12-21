/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.command;

import android.support.annotation.CheckResult;

import com.tq.libs.exception.ParameterException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public interface ICommand<T> {

    boolean satisfyToCondition(T object);

    @CheckResult
    List<T> filter(Collection<T> collection);

    ICommand<T> or(ICommand<T> command);

    ICommand<T> and(ICommand<T> command);

    ICommand<T> not(ICommand<T> command);

    abstract class BaseCommand<T> implements ICommand<T> {
        @Override
        public List<T> filter(Collection<T> collection) {
            if (collection == null) {
                return null;
            }
            List<T> temp = new ArrayList<>();
            for (T obj : collection) {
                if (satisfyToCondition(obj)) {
                    temp.add(obj);
                }
            }
            return temp;
        }

        @Override
        public ICommand<T> and(ICommand<T> command) {
            AndCommand<T> andCommand = new AndCommand<>();
            andCommand.addCommand(this);
            andCommand.addCommand(command);
            return andCommand;
        }

        @Override
        public ICommand<T> or(ICommand<T> command) {
            OrCommand<T> orCommand = new OrCommand<>();
            orCommand.addCommand(this);
            orCommand.addCommand(command);
            return orCommand;
        }

        @Override
        public ICommand<T> not(ICommand<T> command) {
            return new InvertCommand<>(this);
        }
    }

    class InvertCommand<T> extends BaseCommand<T> {
        private final ICommand<T> command;

        public InvertCommand(ICommand<T> command) {
            this.command = command;
        }

        @Override
        public boolean satisfyToCondition(T object) {
            return !command.satisfyToCondition(object);
        }
    }

    interface ILogicCommand<T> extends ICommand<T> {
        void addCommand(ICommand<T> condition);

        void removeCommand(ICommand<T> condition);
    }

    abstract class BaseLogicCommand<T> implements ILogicCommand<T> {
        protected List<ICommand<T>> conditions;

        public BaseLogicCommand() {
            conditions = new LinkedList<>();
        }

        @Override
        public BaseLogicCommand<T> and(ICommand<T> command) {
            if (this instanceof AndCommand) {
                addCommand(command);
                return this;
            }
            AndCommand<T> andCommand = new AndCommand<>();
            andCommand.addCommand(this);
            andCommand.addCommand(command);
            return andCommand;
        }

        @Override
        public BaseLogicCommand<T> or(ICommand<T> command) {
            if (this instanceof OrCommand) {
                addCommand(command);
                return this;
            }
            OrCommand<T> andCommand = new OrCommand<>();
            andCommand.addCommand(this);
            andCommand.addCommand(command);
            return andCommand;
        }

        @Override
        public ICommand<T> not(ICommand<T> command) {
            return new InvertCommand<>(this);
        }

        @Override
        public void addCommand(ICommand<T> condition) {
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
        public synchronized void removeCommand(ICommand<T> condition) {
            synchronized (this) {
                conditions.remove(condition);
            }
        }

        @Override
        public List<T> filter(Collection<T> collection) {
            if (collection == null) {
                return null;
            }
            return doFilter(collection);
        }

        private List<T> doFilter(Collection<T> collection) {
            List<T> temp = new ArrayList<>();
            for (T obj : collection) {
                if (satisfyToCondition(obj)) {
                    temp.add(obj);
                }
            }
            return temp;
        }
    }

    class AndCommand<T> extends BaseLogicCommand<T> {

        @Override
        public boolean satisfyToCondition(T object) {
            Collection<ICommand<T>> commands;
            synchronized (this) {
                commands = new LinkedList<>(this.conditions);
            }
            boolean isConformed = true;
            for (ICommand<T> condition : commands) {
                if (!condition.satisfyToCondition(object)) {
                    isConformed = false;
                    break;
                }
            }
            return isConformed;
        }
    }

    class OrCommand<T> extends BaseLogicCommand<T> {

        @Override
        public boolean satisfyToCondition(T object) {
            Collection<ICommand<T>> commands;
            synchronized (this) {
                commands = new LinkedList<>(this.conditions);
            }
            boolean isConformed = false;
            for (ICommand<T> condition : commands) {
                if (condition.satisfyToCondition(object)) {
                    isConformed = true;
                    break;
                }
            }
            return isConformed;
        }
    }
}
