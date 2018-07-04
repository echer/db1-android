package br.com.db1.githubwrapper.ui.github;

import android.content.Context;

import br.com.db1.githubwrapper.data.DataRepository;
import br.com.db1.githubwrapper.data.receivers.ConnectivityBroadcastReceiver;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class GithubPresenter implements GithubContract.Presenter {

    private ConnectivityBroadcastReceiver connectivityBroadcastReceiver;
    private CompositeSubscription compositeSubscription;
    private GithubContract.View view;
    private DataRepository dataRepository;

    public GithubPresenter(CompositeSubscription compositeSubscription, DataRepository dataRepository, ConnectivityBroadcastReceiver connectivityBroadcastReceiver, GithubContract.View view) {
        this.compositeSubscription = compositeSubscription;
        this.view = view;
        this.connectivityBroadcastReceiver = connectivityBroadcastReceiver;
        this.dataRepository = dataRepository;
    }

    @Override
    public void obterRepositorios(Context context, int pagina) {
        view.setProgressBar(true);
        Subscription subscription = dataRepository.getRepositorios(
                context,
                pagina,
                repositorios -> {
                    view.exibirRepositorios(repositorios);
                    view.setProgressBar(false);
                },
                throwable -> {
                    view.setProgressBar(false);
                }
        );
        compositeSubscription.add(subscription);
    }

    @Override
    public void subscribeEventStream() {
        Observable<Boolean> observable = connectivityBroadcastReceiver.registerReceiver();
        Subscription subscription = observable.subscribe(view::showOfflineMsg);
        compositeSubscription.add(subscription);
    }

    @Override
    public void unsubscribeEventStream() {
        compositeSubscription.clear();
        connectivityBroadcastReceiver.unregisterReceiver();
    }

}
