package cn.leo.magic.thread;

/**
 * Created by Leo on 2018/5/10.
 */

public abstract class MagicRunnable implements Runnable {
    private Thread mThread;

    public void stop() {
        if (mThread != null && !mThread.isInterrupted()) {
            mThread.interrupt();
        }
    }

    @Override
    public void run() {
        mThread = Thread.currentThread();
    }
}
