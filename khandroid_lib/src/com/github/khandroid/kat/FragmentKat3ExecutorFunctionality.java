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

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.khandroid.fragment.FragmentAttachable;
import com.github.khandroid.fragment.FragmentAttachedFunctionality;
import com.github.khandroid.fragment.HostFragment;
import com.github.khandroid.kat.KhandroidAsyncTask3.TaskListener;
import com.github.khandroid.misc.KhandroidLog;


public class FragmentKat3ExecutorFunctionality<T, U, V> extends FragmentAttachedFunctionality
        implements Kat3Executor<T, U, V>, TaskListener<U, V> {
    private final TaskExecutorListener<U, V> mExecutorListener;
    private final String mTaskFragmentTag;
    private TaskFragment mTaskFragment;


    public <KatHostFragment extends HostFragment & FragmentKat3ExecutorFunctionality.HostingAble<U, V>> 
            FragmentKat3ExecutorFunctionality(KatHostFragment fragment) {
        super(fragment);
        mTaskFragmentTag = fragment.getClass().getSimpleName(); 
        mExecutorListener = fragment.getKatExecutorListener();
    }


    public <KatHostFragment extends HostFragment & FragmentKat3ExecutorFunctionality.HostingAble<U, V>> 
            FragmentKat3ExecutorFunctionality(KatHostFragment fragment, String customTaskTag) {
        super(fragment);
        mTaskFragmentTag = customTaskTag;
        mExecutorListener = fragment.getKatExecutorListener();
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskFragment = (TaskFragment) getFragment().getFragmentManager()
                .findFragmentByTag(mTaskFragmentTag);

        if (mTaskFragment != null) {
            KhandroidLog.d("Found previous TaskFragment");
            mTaskFragment.attach(this);

            AsyncTask.Status status = mTaskFragment.getTaskStatus();
            if (status == AsyncTask.Status.RUNNING) {
                onContinueWithTask();
            } else if (status == AsyncTask.Status.FINISHED) {
                if (!mTaskFragment.isTaskCancelled()) {
                    onTaskHasBeenCompleted(mTaskFragment.getTaskResult());
                } else {
                    onTaskHasBeenCancelled();
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mTaskFragment != null) {
            if (getFragment().getActivity().isFinishing()) {
                mTaskFragment.cancelTask(true);
            }

            mTaskFragment.detach();
        }
    }


    protected void onContinueWithTask() {
        if (mExecutorListener != null) {
            mExecutorListener.onContinueWithTask();
        }
    }


    private void closeTaskFragment() {
        if (mTaskFragment != null) {
            getFragment().getFragmentManager().beginTransaction().remove(mTaskFragment).commitAllowingStateLoss();
            mTaskFragment = null;
        }
    }


    @Override
    public void onTaskCompleted(V result) {
        KhandroidLog.d("About to close task fragment");
        closeTaskFragment();

        if (mExecutorListener != null) {
            mExecutorListener.onTaskCompleted(result);
        }
    }


    @Override
    public void onTaskCancelled() {
        closeTaskFragment();

        if (mExecutorListener != null) {
            mExecutorListener.onTaskCancelled();
        }
    }


    private void onTaskHasBeenCancelled() {
        closeTaskFragment();

        if (mExecutorListener != null) {
            mExecutorListener.onTaskHasBeenCancelled();
        }
    }


    private void onTaskHasBeenCompleted(V taskResult) {
        closeTaskFragment();

        if (mExecutorListener != null) {
            mExecutorListener.onTaskHasBeenCompleted(taskResult);
        }
    }


    @Override
    public void onTaskPublishProgress(U... progress) {
        if (mExecutorListener != null) {
            mExecutorListener.onTaskPublishProgress(progress);
        }
    }


    @Override
    public void execute(KhandroidAsyncTask3<T, U, V> task, T... params) {
        mTaskFragment = new TaskFragment(task, params);
        mTaskFragment.setTargetFragment(getFragment(), 0);
        mTaskFragment.attach(this);
        getFragment().getFragmentManager().beginTransaction().add(mTaskFragment, mTaskFragmentTag)
                .commit();
    }



    private class TaskFragment extends Fragment implements TaskListener<U, V> {
        private KhandroidAsyncTask3<T, U, V> mTask;
        private TaskListener<U, V> mTaskListener;


        public TaskFragment(KhandroidAsyncTask3<T, U, V> task, T... params) {
            super();
            setRetainInstance(true);
            mTask = task;
            mTask.execute(this, params);
            KhandroidLog.d("Creating TaskFragment");
        }


        public void attach(FragmentKat3ExecutorFunctionality<T, U, V> executorFunc) {
            if (executorFunc != null) {
                if (mTaskListener == null && mTaskListener != executorFunc) {
                    KhandroidLog.d("Attaching TaskFragment to executor");
                    mTaskListener = executorFunc;
                } else {
                    throw new IllegalStateException("There is another executor functionality already attached");
                }
            } else {
                throw new IllegalArgumentException("executorFunc is null");
            }
        }


        public void detach() {
            KhandroidLog.d("Detaching TaskFragment");
            mTaskListener = null;
        }


        @Override
        public void onDestroy() {
            super.onDestroy();
            mTask.cancel(true);
            KhandroidLog.d("Destroying TaskFragment");
        }


        @Override
        public void onTaskPublishProgress(U... progress) {
            if (mTaskListener != null) {
                mTaskListener.onTaskPublishProgress(progress);
            }

        }


        @Override
        public void onTaskCancelled() {
            if (mTaskListener != null) {
                mTaskListener.onTaskCancelled();
            }
        }


        @Override
        public void onTaskCompleted(V result) {
            if (mTaskListener != null) {
                mTaskListener.onTaskCompleted(result);
            }
        }


        public AsyncTask.Status getTaskStatus() {
            return mTask.getStatus();
        }


        public V getTaskResult() {
            V ret = null;

            if (mTask.getStatus() == AsyncTask.Status.FINISHED) {
                try {
                    ret = mTask.get();
                } catch (InterruptedException e) {
                    // Cannot happen. We check if it is finished
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // Cannot happen. We check if it is finished
                    e.printStackTrace();
                }
            } else {
                throw new IllegalStateException("Task is not finished.");
            }

            return ret;
        }


        public boolean isTaskCancelled() {
            return mTask.isCancelled();
        }


        public boolean cancelTask(boolean mayInterruptIfRunning) {
            return mTask.cancel(mayInterruptIfRunning);
        }
    }


    @Override
    public boolean cancelTask(boolean mayInterruptIfRunning) {
        boolean ret = false;

        if (mTaskFragment != null) {
            ret = mTaskFragment.cancelTask(mayInterruptIfRunning);
        }

        return ret;
    }


    @Override
    public boolean isExecuting() {
        boolean ret = false;

        if (mTaskFragment != null) {
            ret = true;
        }

        return ret;
    }

    public interface HostingAble<U, V> extends FragmentAttachable.HostingAble {
        TaskExecutorListener<U, V> getKatExecutorListener();
    }
}
