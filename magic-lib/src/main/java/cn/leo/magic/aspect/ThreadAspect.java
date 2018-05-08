package cn.leo.magic.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Leo on 2018/5/2.
 */
@Aspect
public class ThreadAspect {
    private static final String UI_THREAD =
            "execution(@cn.leo.magic.annotation.RunOnUIThread * *(..))";
    private static final String IO_THREAD =
            "execution(@cn.leo.magic.annotation.RunOnIOThread * *(..))";
    private static final String BACK_THREAD =
            "execution(@cn.leo.magic.annotation.RunOnBackGround * *(..))";

    @Pointcut(UI_THREAD)
    public void methodRunOnUIThread() {
    }

    @Pointcut(IO_THREAD)
    public void methodRunOnIOThread() {
    }

    @Pointcut(BACK_THREAD)
    public void methodRunOnBackGround() {
    }

    @Around("methodRunOnUIThread()")
    public void aroundJoinPointUI(final ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
        Log.e("----", "methodRunOnUIThread");
    }

    @Around("methodRunOnIOThread()")
    public void aroundJoinPointIO(final ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
        Log.e("----", "methodRunOnIOThread");
    }

    @Around("methodRunOnBackGround()")
    public void aroundJoinPointBack(final ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
        Log.e("----", "methodRunOnBackGround");
    }
}
