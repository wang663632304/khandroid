package com.github.khandroid.kat;


public interface Kat3Executor<T, U, V> {
    public void execute(KhandroidAsyncTask3<T, U, V> task,
                        TaskExecutorListener<U, V> listener,
                        T... params
            );


    public void execute(KhandroidAsyncTask3<T, U, V> task,
                        T... params
            );


    public void execute(KhandroidAsyncTask3<T, U, V> task,
                        TaskExecutorListener<U, V> listener
            );
    
    
    public void onTaskCompleted(V result);
    public void onTaskCancelled();
    public void onTaskProgressUpdate(U... progress);
    
    
    public interface TaskExecutorListener<Progress, Result> {
        void onTaskPublishProgress(Progress... progress);
        void onTaskCancelled();
        void onTaskCompleted(Result result);
    }
}
