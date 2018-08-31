package cn.leo.magic.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * @author Leo
 * @date 2018/5/9
 */

public class ThreadController {
    private static final HandlerThread mHandlerThread = new HandlerThread("Thread BackGround");
    private static final Handler mBackHandler;
    private static final Handler mUIHandler;

    static {
        mHandlerThread.start();
        mBackHandler = new Handler(mHandlerThread.getLooper());
        mUIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUIThread(MagicRunnable runnable, int delayMillis) {
        if (delayMillis > 0) {
            mUIHandler.postDelayed(runnable, delayMillis);
        } else {
            mUIHandler.post(runnable);
        }
    }

    public static void runOnIOThread(MagicRunnable runnable) {
        IOThreadPool.execute(runnable);
    }

    public static void runOnCalcThread(MagicRunnable runnable) {
        CalcThreadPool.execute(runnable);
    }

    public static void runOnBackThread(MagicRunnable runnable, int delayMillis) {
        if (delayMillis > 0) {
            mBackHandler.postDelayed(runnable, delayMillis);
        } else {
            mBackHandler.post(runnable);
        }
    }

    public static void removeTask(MagicRunnable runnable) {
        mUIHandler.removeCallbacks(runnable);
        mBackHandler.removeCallbacks(runnable);
        IOThreadPool.cancel(runnable);
        CalcThreadPool.cancel(runnable);
        runnable.stop();
    }
}
