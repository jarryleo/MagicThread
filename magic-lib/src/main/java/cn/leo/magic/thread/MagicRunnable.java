package cn.leo.magic.thread;

import android.support.annotation.CallSuper;

/**
 *
 * @author Leo
 * @date 2018/5/10
 */

public abstract class MagicRunnable implements Runnable {
    private Thread mThread;

    public void stop() {
        if (mThread != null && !mThread.isInterrupted()) {
            mThread.interrupt();
        }
    }
    
    @CallSuper
    @Override
    public void run() {
        mThread = Thread.currentThread();
    }
}
