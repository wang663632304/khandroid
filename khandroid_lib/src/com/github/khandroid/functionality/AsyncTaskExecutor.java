package com.github.khandroid.functionality;

import com.github.khandroid.misc.KhandroidAsyncTask;

import android.os.AsyncTask;

public interface AsyncTaskExecutor {
    public <T> void execute(KhandroidAsyncTask<T, ?, ?> task, T... params);
    public AsyncTask.Status getTaskStatus();
    public boolean isExecuting();
    public boolean cancelExecution(boolean mayInterruptIfRunning);
    public void onTaskCancelled();
    public void onTaskCompleted(KhandroidAsyncTask<?, ?, ?> task);
}
