package cn.leo.magic.aspect;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import cn.leo.magic.annotation.RunOnBackGround;
import cn.leo.magic.annotation.RunOnUIThread;
import cn.leo.magic.thread.LifeCycleController;
import cn.leo.magic.thread.MagicRunnable;
import cn.leo.magic.thread.ThreadController;

/**
 * Created by Leo on 2018/5/2.
 */
@Aspect
public class ThreadAspect {
    LifeCycleController mLifeCycleController = new LifeCycleController();

    private static final String UI_THREAD =
            "execution(@cn.leo.magic.annotation.RunOnUIThread * *(..))";
    private static final String IO_THREAD =
            "execution(@cn.leo.magic.annotation.RunOnIOThread * *(..))";
    private static final String CALC_THREAD =
            "execution(@cn.leo.magic.annotation.RunOnCalcThread * *(..))";
    private static final String BACK_THREAD =
            "execution(@cn.leo.magic.annotation.RunOnBackGround * *(..))";

    @Pointcut(UI_THREAD)
    public void methodRunOnUIThread() {
    }

    @Pointcut(IO_THREAD)
    public void methodRunOnIOThread() {
    }

    @Pointcut(CALC_THREAD)
    public void methodRunOnCalcThread() {
    }

    @Pointcut(BACK_THREAD)
    public void methodRunOnBackGround() {
    }

    @Around("methodRunOnUIThread()")
    public void aroundJoinPointUI(final ProceedingJoinPoint joinPoint) {
        if (!Thread.currentThread().isInterrupted()) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            RunOnUIThread annotation = method.getAnnotation(RunOnUIThread.class);
            int delayMillis = annotation.value();
            ThreadController.runOnUIThread(getRunnable(joinPoint), delayMillis);
        }
    }

    @Around("methodRunOnIOThread()")
    public void aroundJoinPointIO(final ProceedingJoinPoint joinPoint) {
        ThreadController.runOnIOThread(getRunnable(joinPoint));
    }

    @Around("methodRunOnCalcThread()")
    public void aroundJoinPointCalc(final ProceedingJoinPoint joinPoint) {
        ThreadController.runOnCalcThread(getRunnable(joinPoint));
    }

    @Around("methodRunOnBackGround()")
    public void aroundJoinPointBack(final ProceedingJoinPoint joinPoint) {
        if (!Thread.currentThread().isInterrupted()) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            RunOnBackGround annotation = method.getAnnotation(RunOnBackGround.class);
            int delayMillis = annotation.value();
            ThreadController.runOnBackThread(getRunnable(joinPoint), delayMillis);
        }
    }

    @AfterReturning(pointcut = "methodRunOnIOThread()",
            returning = "retVal")
    public void afterJoinPointIO(Object retVal) {
        if (retVal != null) {
            Log.e("MagicThread:", "线程转换注解的方法不能有返回值");
        }
    }

    public MagicRunnable getRunnable(final ProceedingJoinPoint joinPoint) {
        final Object target = joinPoint.getTarget();
        MagicRunnable runnable = new MagicRunnable() {
            @Override
            public void run() {
                super.run();
                try {
                    joinPoint.proceed();
                    if (target instanceof LifecycleOwner) {
                        mLifeCycleController.unSubscribe((LifecycleOwner) target, this);
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        };
        if (target instanceof LifecycleOwner) {
            mLifeCycleController.subscribe((LifecycleOwner) target, runnable);
        }
        return runnable;
    }

}
