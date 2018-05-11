package cn.leo.magic.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by Leo on 2018/5/9.
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

    public static void runOnUIThread(MagicRunnable runnable) {
        mUIHandler.post(runnable);
    }

    public static void runOnIOThread(MagicRunnable runnable) {
        ThreadPool.execute(runnable);
    }

    public static void runOnBackThread(MagicRunnable runnable) {
        mBackHandler.post(runnable);
    }

    public static void removeTask(MagicRunnable runnable) {
        ThreadPool.cancel(runnable);
        runnable.stop();
    }
}
