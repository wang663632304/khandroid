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
    
    
    /**
     * Intended to be used then you are interested just in completion of the task.
     * Such case is when you need to put in async task just some small operation like reading from small file or few DB rows.
     * @author ogre
     *
     * @param <Progress>
     * @param <Result>
     */
    abstract public static class TaskExecutorSimpleListener<Progress, Result> implements TaskExecutorListener<Progress, Result> {
        @Override
        public void onTaskCancelled() {
            //empty
        }

        @Override
        public void onTaskCompleted(Result result) {
            //empty
        }

        @Override
        public void onContinueWithTask() {
            //empty
        }
    }
}
