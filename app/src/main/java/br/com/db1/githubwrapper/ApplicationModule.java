package br.com.db1.githubwrapper;

import br.com.db1.githubwrapper.di.ApplicationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private BaseApplication baseApplication;

    public ApplicationModule(BaseApplication baseApplication) {
        this.baseApplication = baseApplication;
    }

    @Provides
    @ApplicationScope
    public BaseApplication provideApplication() {
        return baseApplication;
    }

}
