package br.com.db1.githubwrapper;

import android.support.v7.app.AppCompatActivity;

import br.com.db1.githubwrapper.data.DataRepository;
import br.com.db1.githubwrapper.data.DataRepositoryModule;
import br.com.db1.githubwrapper.di.ApplicationScope;
import br.com.db1.githubwrapper.util.MainUiThread;
import br.com.db1.githubwrapper.util.NetworkHelper;
import br.com.db1.githubwrapper.util.ThreadExecutor;
import dagger.Component;

@ApplicationScope
@Component(modules = {ApplicationModule.class, DataRepositoryModule.class})
public interface ApplicationComponent {

    BaseApplication getBaseApplication();
    DataRepository getDataRepository();
    NetworkHelper getNetworkHelper();

    void inject(AppCompatActivity appCompatActivity);
}