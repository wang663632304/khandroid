package com.github.khandroid.functionality;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.support.v4.app.Fragment;

import com.github.khandroid.activity.ActivityAttachedFunctionality;
import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.misc.KhandroidAsyncTask;


public class AsyncTaskExecutorImplementation extends ActivityAttachedFunctionality implements AsyncTaskExecutor {
    public AsyncTaskExecutorImplementation(HostActivity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
    }


    @Override
    public <T> void execute(KhandroidAsyncTask<T, ?, ?> task, T... params) {
        

    }


    @Override
    public Status getTaskStatus() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean isExecuting() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean cancelExecution(boolean mayInterruptIfRunning) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void onTaskCancelled() {
        // TODO Auto-generated method stub

    }


    @Override
    public void onTaskCompleted(KhandroidAsyncTask<?, ?, ?> task) {
        // TODO Auto-generated method stub

    }

    
    public class AsyncTaskContanerFragment extends Fragment {
        private AsyncTask<?, ?, ?> mTask;
        
        public void setTask(AsyncTask<?, ?, ?> task) {
            mTask = task;
        }
        
        
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            setRetainInstance(true);
            if (mTask != null && mTask.getStatus() == AsyncTask.Status.PENDING) {
                mTask.execute();
            }
        }
        
        
        public void attach() {
            
        }
    }
}
