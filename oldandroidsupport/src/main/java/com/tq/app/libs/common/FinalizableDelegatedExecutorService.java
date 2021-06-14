package com.tq.app.libs.common;

import java.util.concurrent.ExecutorService;

public class FinalizableDelegatedExecutorService extends DelegatedExecutorService {
    public FinalizableDelegatedExecutorService(ExecutorService e) {
        super(e);
    }

    @Override
    protected void finalize() throws Throwable {
        super.shutdown();
        super.finalize();
    }
}
