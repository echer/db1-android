package br.com.db1.githubwrapper.ui.repositorios;

import android.content.Context;
import android.content.Intent;

import br.com.db1.githubwrapper.data.DataRepository;
import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.data.receivers.ConnectivityBroadcastReceiver;
import br.com.db1.githubwrapper.ui.repositoriodetalhes.RepositorioDetalhesActivity;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static br.com.db1.githubwrapper.util.Constants.Activity.Extras.GITHUB_DETALHES_REPONAME;
import static br.com.db1.githubwrapper.util.Constants.Activity.Extras.GITHUB_DETALHES_USERNAME;

public class RepositoriosPresenter implements RepositoriosContract.Presenter {

    private ConnectivityBroadcastReceiver connectivityBroadcastReceiver;
    private CompositeSubscription compositeSubscription;
    private RepositoriosContract.View view;
    private DataRepository dataRepository;

    public RepositoriosPresenter(CompositeSubscription compositeSubscription, DataRepository dataRepository, ConnectivityBroadcastReceiver connectivityBroadcastReceiver, RepositoriosContract.View view) {
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
    public void obterRepositorios(Context context, String username) {
        view.setProgressBar(true);
        Subscription subscription = dataRepository.getRepositorios(
                context,
                username,
                repositorios -> {
                    view.limparRepositorios();
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

    @Override
    public void abreDetalhesDoRepositorio(int position) {
        Repositorio repositorio = view.obtemRepositorioPelaPosicao(position);

        Intent intent = new Intent(view.getContext(), RepositorioDetalhesActivity.class);
        intent.putExtra(GITHUB_DETALHES_USERNAME, repositorio.getDono().getLogin());
        intent.putExtra(GITHUB_DETALHES_REPONAME, repositorio.getNome());
        view.getContext().startActivity(intent);
    }

}
