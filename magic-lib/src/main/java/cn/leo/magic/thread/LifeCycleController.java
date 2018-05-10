package cn.leo.magic.thread;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Leo on 2018/5/9.
 */

public class LifeCycleController implements LifecycleObserver {

    private static ConcurrentHashMap<LifecycleOwner, List<MagicRunnable>> mTasks = new ConcurrentHashMap<>();

    public void subscribe(LifecycleOwner lifecycleOwner, MagicRunnable runnable) {
        lifecycleOwner.getLifecycle().addObserver(this);
        if (!mTasks.contains(lifecycleOwner)) {
            List<MagicRunnable> runnableList = Collections.synchronizedList(new ArrayList<MagicRunnable>());
            runnableList.add(runnable);
            mTasks.put(lifecycleOwner, runnableList);
        } else {
            List<MagicRunnable> runnableList = mTasks.get(lifecycleOwner);
            runnableList.add(runnable);
        }
    }

    public void unSubscribe(LifecycleOwner lifecycleOwner, MagicRunnable runnable) {
        if (mTasks.contains(lifecycleOwner)) {
            List<MagicRunnable> runnableList = mTasks.get(lifecycleOwner);
            runnableList.remove(runnable);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner source) {
        List<MagicRunnable> runnableList = mTasks.get(source);
        for (MagicRunnable runnable : runnableList) {
            ThreadController.removeTask(runnable);
        }
        mTasks.remove(source);
    }

}
