package br.com.db1.githubwrapper.ui.repositoriodetalhes;

import android.content.Context;

import br.com.db1.githubwrapper.data.DataRepository;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RepositorioDetalhesPresenter implements RepositorioDetalhesContract.Presenter {

    private CompositeSubscription compositeSubscription;
    private RepositorioDetalhesContract.View view;
    private DataRepository dataRepository;

    public RepositorioDetalhesPresenter(CompositeSubscription compositeSubscription, RepositorioDetalhesContract.View view, DataRepository dataRepository) {
        this.compositeSubscription = compositeSubscription;
        this.view = view;
        this.dataRepository = dataRepository;
    }

    @Override
    public void obterRepositorio(Context context, String username, String repo) {
        view.setProgressBar(true);
        Subscription subscription = dataRepository.getRepositorio(
                context,
                username,
                repo,
                repositorio -> {
                    view.exibirRepositorio(repositorio);
                    view.setProgressBar(false);
                },
                throwable -> {
                    view.setProgressBar(false);
                }
        );
        compositeSubscription.add(subscription);
    }
}
