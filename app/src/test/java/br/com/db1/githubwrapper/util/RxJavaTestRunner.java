package br.com.db1.githubwrapper.util;

import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;

import rx.Scheduler;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

public class RxJavaTestRunner extends MockitoJUnitRunner {
    public RxJavaTestRunner(Class<?> klass) throws InvocationTargetException {
        super(klass);
        RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
    }
}
