/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.job;

import static com.tq.app.libs.common.BusinessLogicExecutor.dispatch_async;
import static com.tq.app.libs.common.BusinessLogicExecutor.getMainQueue;

@Deprecated
public abstract class Job implements Runnable {

    private final String jobID;
    private Exception error;
    private JobListener jobListener;

    public Job(String jobID) {
        this.jobID = jobID;
    }

    public void setJobListener(JobListener jobListener) {
        this.jobListener = jobListener;
    }

    @Override
    public final void run() {
        Runnable finalTask;
        try {
            doJob();
            finalTask = new Runnable() {
                @Override
                public void run() {
                    if (jobListener != null) {
                        jobListener.onJobSuccess(jobID);
                        jobListener = null;
                    }
                }
            };
        } catch (Exception e) {
            this.error = e;
            finalTask = new Runnable() {
                @Override
                public void run() {
                    if (jobListener != null) {
                        jobListener.onJobFailed(jobID, error);
                        jobListener = null;
                    }
                }
            };
        }
        if (jobListener != null) {
            dispatch_async(getMainQueue(), finalTask);
        }
    }

    public abstract void doJob() throws Exception;

    public interface JobListener {
        void onJobSuccess(String jobID);

        void onJobFailed(String jobID, Exception error);
    }
}
