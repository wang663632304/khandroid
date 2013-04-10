package com.github.khandroid.kat;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;

import com.github.khandroid.activity.ActivityAttachedFunctionality;
import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.kat.KhandroidAsyncTask3.TaskListener;
import com.github.khandroid.misc.KhandroidLog;


public class ActivityKat3ExecutorFunctionality<T, U, V> extends ActivityAttachedFunctionality
        implements Kat3Executor<T, U, V>, TaskListener<U,V> {
    private KhandroidAsyncTask3<T, U, V> mTask;
    private TaskExecutorListener<U, V> mListener;

// TODO:
// Add int CODE in constructor in order to be able easy to distinguish different executors
// remove mListener and add interface HostingAble with it's methods (or (even better) require method getKatExecutorListener()).    
    public ActivityKat3ExecutorFunctionality(HostActivity activity) {
        super(activity);
    }


    protected void onContinueWithTask() {
        if (mListener != null) {
            mListener.onContinueWithTask();
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KhandroidLog.v("onCreate");
        Object task = getActivity().getLastNonConfigurationInstance();
        KhandroidLog.v("Task: " + task);

        if (task != null && task instanceof KhandroidAsyncTask3) {
            mTask = (KhandroidAsyncTask3<T, U, V>) task;
            mTask.attach(this);

            if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
                onContinueWithTask();
            } else if (mTask.getStatus() == AsyncTask.Status.FINISHED) {
                try {
                    if (!mTask.isCancelled()) {
                        onTaskHasBeenCompleted(mTask.get());
                    } else {
                        onTaskHasBeenCancelled();
                    }
                } catch (InterruptedException e) {
                    // Cannot happen. We check if it is finished
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // Cannot happen. We check if it is finished
                    e.printStackTrace();
                }
            }
        }
    }


    public Object onRetainNonConfigurationInstance() {
        Object ret;

        if (mTask != null) {
            mTask.detach();
            ret = mTask;
        } else {
            ret = null;
        }

        return ret;
    }


    @Override
    public void execute(KhandroidAsyncTask3<T, U, V> task,
                        TaskExecutorListener<U, V> listener,
                        T... params) {

        mListener = listener;
        execute(task, params);
    }


    @Override
    public void execute(KhandroidAsyncTask3<T, U, V> task, T... params) {
        mTask = task;
        mTask.execute(this, params);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public void execute(KhandroidAsyncTask3<T, U, V> task,
                        com.github.khandroid.kat.Kat3Executor.TaskExecutorListener<U, V> listener) {

        mTask = task;
        mTask.execute(this);
    }


    @Override
    public void onTaskCompleted(V result) {
        mTask.detach();
        mTask = null;
        if (mListener != null) {
            mListener.onTaskCompleted(result);
        }
    }

    
    @Override
    public void onTaskPublishProgress(U... progress) {
        if (mListener != null) {
            mListener.onTaskPublishProgress(progress);
        }
    }

    @Override
    public void onTaskCancelled() {
        mTask = null;
        if (mListener != null) {
            mListener.onTaskCancelled();
        }
    }

    
    private void onTaskHasBeenCancelled() {
        mTask = null;
        if (mListener != null) {
            mListener.onTaskHasBeenCancelled();
        }
    }


    private void onTaskHasBeenCompleted(V taskResult) {
        mTask.detach();
        mTask = null;

        if (mListener != null) {
            mListener.onTaskHasBeenCompleted(taskResult);
        }
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTask != null) {
            if (getActivity().isFinishing()) {
                mTask.cancel(true);
            }

            mTask.detach();
        }
    }


    @Override
    public boolean cancelTask(boolean mayInterruptIfRunning) {
        boolean ret = false;
        
        if (mTask != null) {
            ret = mTask.cancel(mayInterruptIfRunning);
        }
        
        return true;
    }


    @Override
    public boolean isExecuting() {
        boolean ret = false;

        if (mTask != null) {
            ret = true;
        }
            
        return ret;
    }
    
    
}
