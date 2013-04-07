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


package com.github.khandroid.misc;

abstract public class StatefulRunnable implements Runnable {
    public final static int STATE_READY_TO_START = 0;
    public final static int STATE_RUNNING = 1;
    public final static int STATE_STOPPED = 2;
    
    int state = STATE_READY_TO_START;

	public int getState() {
		return state;
	}
	

	public void setState(int state) {
		if (this.state <= state) {
			this.state = state;
		} else {
			throw new IllegalStateException("Invalid state. State can change unidirectional, i.e. READY_TO_START -> RUNNING -> STOPPED.");
		}
	}

    
	public boolean isReadyToStart() {
		return (state == STATE_READY_TO_START);
	}
   

	public boolean isRunning() {
		return (state == STATE_RUNNING);
	}

	
	public boolean isStopped() {
		return (state == STATE_STOPPED);
	}

}
