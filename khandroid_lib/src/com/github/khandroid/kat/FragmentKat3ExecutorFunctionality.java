/*
 * Copyright (C) 2012-2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.khandroid.kat;

import android.support.v4.app.Fragment;

import com.github.khandroid.fragment.FragmentAttachedFunctionality;
import com.github.khandroid.fragment.HostFragment;
import com.github.khandroid.kat.Kat3Executor.TaskExecutorListener;


public class FragmentKat3ExecutorFunctionality<T, U, V> extends FragmentAttachedFunctionality
        implements Kat3Executor<T, U, V> {
    private KhandroidAsyncTask3<T, U, V> mTask;
    private TaskExecutorListener<U, V> mListener;
    private final String mTaskFragmentTag;


    public FragmentKat3ExecutorFunctionality(HostFragment fragment) {
        super(fragment);
        mTaskFragmentTag = fragment.getClass().getSimpleName() + this; // host class name + reference of current object
    }
    
    public FragmentKat3ExecutorFunctionality(HostFragment fragment, String customTaskTag) {
        super(fragment);
        mTaskFragmentTag = customTaskTag;
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
    public void onTaskProgressUpdate(U... progress) {
        if (mListener != null) {
            mListener.onTaskPublishProgress(progress);
        }
    }


    @Override
    public void onTaskCancelled() {
        if (mListener != null) {
            mListener.onTaskCancelled();
        }
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
        if (mTask != null) {
            mTask = task;
            mTask.execute(this, params);
        } else {
            throw new IllegalStateException("Cannot execute. There is previus task.");
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void execute(KhandroidAsyncTask3<T, U, V> task,
                        TaskExecutorListener<U, V> listener) {

        mListener = listener;
        mTask.execute(this, (T) null);
    }


    private class TaskFragment extends Fragment implements Kat3Executor<T, U, V> {
        private KhandroidAsyncTask3<T, U, V> mTask;

        public TaskFragment(KhandroidAsyncTask3<T, U, V> task, T... params) {
            super();
            setRetainInstance(true);
            mTask = task;
            mTask.execute(this, params);
        }
        
        
        @Override
        public void onDestroy() {
            super.onDestroy();
            mTask.cancel(true);
        }


 


        @Override
        public void onTaskCompleted(V result) {
            // TODO Auto-generated method stub
            
        }


        @Override
        public void onTaskCancelled() {
            // TODO Auto-generated method stub
            
        }


        @Override
        public void onTaskProgressUpdate(U... progress) {
            // TODO Auto-generated method stub
            
        }
    }

}
