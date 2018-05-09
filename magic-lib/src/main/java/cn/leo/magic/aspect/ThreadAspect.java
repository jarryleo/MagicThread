package cn.leo.magic.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import cn.leo.magic.thread.ThreadController;

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
        ThreadController.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    @Around("methodRunOnIOThread()")
    public void aroundJoinPointIO(final ProceedingJoinPoint joinPoint) throws Throwable {
        ThreadController.runOnIOThread(new Runnable() {
            @Override
            public void run() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    @Around("methodRunOnBackGround()")
    public void aroundJoinPointBack(final ProceedingJoinPoint joinPoint) throws Throwable {
        ThreadController.runOnBackThread(new Runnable() {
            @Override
            public void run() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}
