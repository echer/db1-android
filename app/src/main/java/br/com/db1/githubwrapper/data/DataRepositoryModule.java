package br.com.db1.githubwrapper.data;

import br.com.db1.githubwrapper.data.remote.RemoteDataSourceModule;
import br.com.db1.githubwrapper.di.ApplicationScope;
import br.com.db1.githubwrapper.di.Remote;
import br.com.db1.githubwrapper.util.NetworkHelper;
import dagger.Module;
import dagger.Provides;

@Module(includes = {RemoteDataSourceModule.class})
public class DataRepositoryModule {

    @Provides
    @ApplicationScope
    public DataRepository provideDataRepository(@Remote DataSource remoteDataSource, NetworkHelper networkHelper) {
        return new DataRepository(remoteDataSource, networkHelper);
    }

    @Provides
    @ApplicationScope
    public NetworkHelper provideNetworkHelper() {
        return new NetworkHelper();
    }

}
