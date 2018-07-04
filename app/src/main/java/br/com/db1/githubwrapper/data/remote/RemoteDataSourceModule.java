package br.com.db1.githubwrapper.data.remote;

import br.com.db1.githubwrapper.data.DataSource;
import br.com.db1.githubwrapper.di.ApplicationScope;
import br.com.db1.githubwrapper.di.Remote;
import br.com.db1.githubwrapper.util.MainUiThread;
import br.com.db1.githubwrapper.util.ThreadExecutor;
import br.com.db1.githubwrapper.util.ThreadingModule;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.com.db1.githubwrapper.data.remote.RemoteDataSource.Api.BASE_URL;

@Module(includes = {ThreadingModule.class})
public class RemoteDataSourceModule {

    @Provides
    @ApplicationScope
    @Remote
    public DataSource provideRemoteDataSource(MainUiThread mainUiThread,
                                              ThreadExecutor threadExecutor, GithubApiService apiService) {
        return new RemoteDataSource(mainUiThread, threadExecutor, apiService);
    }

    @Provides
    @ApplicationScope
    public GithubApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(GithubApiService.class);
    }

    @Provides
    @ApplicationScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(rxAdapter)
                .build();
        return retrofit;
    }

    @Provides
    @ApplicationScope
    public OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build();
        return okHttpClient;
    }

    @Provides
    @ApplicationScope
    public Interceptor provideOkHttpInterceptor() {
        return interceptor;
    }

    private Interceptor interceptor = chain -> {
        HttpUrl httpUrl = chain.request().url().newBuilder()
                .build();
        Request newRequest = chain.request().newBuilder().url(httpUrl).build();
        return chain.proceed(newRequest);
    };

}
