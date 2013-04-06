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
