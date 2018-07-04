package br.com.db1.githubwrapper.ui.github;

import android.content.Context;

import br.com.db1.githubwrapper.data.DataRepository;
import br.com.db1.githubwrapper.data.receivers.ConnectivityBroadcastReceiver;
import br.com.db1.githubwrapper.di.ActivityContext;
import br.com.db1.githubwrapper.di.ActivityScope;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class GithubModule {

    GithubActivity githubActivity;

    public GithubModule(GithubActivity githubActivity) {
        this.githubActivity = githubActivity;
    }

    @Provides
    @ActivityScope
    public GithubContract.Presenter provideGithubPresenter(GithubContract.View view, DataRepository dataRepository, ConnectivityBroadcastReceiver connectivityBroadcastReceiver) {
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        return new GithubPresenter(compositeSubscription, dataRepository, connectivityBroadcastReceiver, view);
    }

    @Provides
    @ActivityScope
    public GithubContract.View provideGithubView() {
        return githubActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return githubActivity;
    }
}
