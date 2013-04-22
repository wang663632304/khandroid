package com.github.khandroid.kat;


public interface KatExecutor<T, U, V> {
    public void execute(KhandroidAsyncTask<T, U, V> task,
                        T... params
            );

    boolean cancelTask(boolean mayInterruptIfRunning);
    public boolean isExecuting();
    
    public interface TaskExecutorListener<Progress, Result> {
        void onTaskPublishProgress(Progress... progress);
        void onTaskCancelled();
        void onTaskCompleted(Result result);
        void onContinueWithTask();
    }
}
