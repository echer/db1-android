package br.com.db1.githubwrapper.ui.repositorios;

import android.content.Context;

import br.com.db1.githubwrapper.data.DataRepository;
import br.com.db1.githubwrapper.data.receivers.ConnectivityBroadcastReceiver;
import br.com.db1.githubwrapper.di.ActivityContext;
import br.com.db1.githubwrapper.di.ActivityScope;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class RepositoriosModule {

    RepositoriosActivity repositoriosActivity;

    public RepositoriosModule(RepositoriosActivity repositoriosActivity) {
        this.repositoriosActivity = repositoriosActivity;
    }

    @Provides
    @ActivityScope
    public RepositoriosContract.Presenter provideGithubPresenter(RepositoriosContract.View view, DataRepository dataRepository, ConnectivityBroadcastReceiver connectivityBroadcastReceiver) {
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        return new RepositoriosPresenter(compositeSubscription, dataRepository, connectivityBroadcastReceiver, view);
    }

    @Provides
    @ActivityScope
    public RepositoriosContract.View provideGithubView() {
        return repositoriosActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return repositoriosActivity;
    }
}
