package br.com.db1.githubwrapper.ui.repositoriodetalhes;

import android.content.Context;

import br.com.db1.githubwrapper.data.DataRepository;
import br.com.db1.githubwrapper.di.ActivityContext;
import br.com.db1.githubwrapper.di.ActivityScope;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class RepositorioDetalhesModule {

    RepositorioDetalhesActivity repositorioDetalhesActivity;

    public RepositorioDetalhesModule(RepositorioDetalhesActivity repositorioDetalhesActivity) {
        this.repositorioDetalhesActivity = repositorioDetalhesActivity;
    }

    @Provides
    @ActivityScope
    public RepositorioDetalhesContract.Presenter provideRepositorioDetalhesPresenter(RepositorioDetalhesContract.View view, DataRepository dataRepository) {
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        return new RepositorioDetalhesPresenter(compositeSubscription, view, dataRepository);
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return repositorioDetalhesActivity;
    }
}
