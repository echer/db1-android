package br.com.db1.githubwrapper.data.local;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import br.com.db1.githubwrapper.data.DataSource;
import br.com.db1.githubwrapper.di.ApplicationScope;
import br.com.db1.githubwrapper.di.Local;
import br.com.db1.githubwrapper.util.MainUiThread;
import br.com.db1.githubwrapper.util.ThreadExecutor;
import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataSourceModule {

    @Provides
    @ApplicationScope
    @Local
    public DataSource provideLocalDataSource(MainUiThread mainUiThread,
                                             ThreadExecutor threadExecutor, DatabaseDefinition databaseDefinition) {
        return new LocalDataSource(mainUiThread, threadExecutor, databaseDefinition);
    }

    @Provides
    @ApplicationScope
    public DatabaseDefinition provideDatabaseDefinition() {
        return FlowManager.getDatabase(LocalDatabase.class);
    }
}

