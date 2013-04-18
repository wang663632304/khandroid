package com.github.khandroid.kat;


public interface Kat3Executor<T, U, V> {
    public void execute(KhandroidAsyncTask3<T, U, V> task,
                        T... params
            );

    boolean cancelTask(boolean mayInterruptIfRunning);
    public boolean isExecuting();
    
    public interface TaskExecutorListener<Progress, Result> {
        void onTaskPublishProgress(Progress... progress);
        void onTaskCancelled();
        void onTaskCompleted(Result result);
        void onContinueWithTask();
        void onTaskHasBeenCompleted(Result result);
        void onTaskHasBeenCancelled();
        
    }
}
