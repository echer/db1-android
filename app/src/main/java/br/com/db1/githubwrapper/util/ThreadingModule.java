package br.com.db1.githubwrapper.util;

import br.com.db1.githubwrapper.di.ApplicationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class ThreadingModule {

    @Provides
    @ApplicationScope
    public ThreadExecutor provideThreadExecutor() {
        return new ThreadExecutor();
    }

    @Provides
    @ApplicationScope
    public MainUiThread provideMainUiThread() {
        return new MainUiThread();
    }
}