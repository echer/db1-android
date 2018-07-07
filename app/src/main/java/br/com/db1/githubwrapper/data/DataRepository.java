package br.com.db1.githubwrapper.data;

import android.content.Context;

import java.util.List;

import br.com.db1.githubwrapper.data.local.LocalDataSource;
import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.data.model.RepositorioDetalhes;
import br.com.db1.githubwrapper.util.NetworkHelper;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataRepository {

    private NetworkHelper networkHelper;
    private DataSource remoteDataSource;
    private DataSource localDataSource;

    public DataRepository(DataSource remoteDataSource, DataSource localDataSource, NetworkHelper networkHelper) {
        this.remoteDataSource = remoteDataSource;
        this.networkHelper = networkHelper;
        this.localDataSource = localDataSource;
    }

    public Subscription getRepositorios(Context context, int page, DataSource.Callback<List<Repositorio>> onSuccess, DataSource.Callback<Throwable> onError) {
        return getRepositorios(context, null, page, onSuccess, onError);
    }

    public Subscription getRepositorios(Context context, String username, int page, DataSource.Callback<List<Repositorio>> onSuccess, DataSource.Callback<Throwable> onError) {

        Observable<List<Repositorio>> observable;

        if (!networkHelper.isNetworkAvailable(context)){
            if (username != null)
                observable = localDataSource.obtemRepositorios(username);
            else
                observable = localDataSource.obtemRepositorios(page);
        }else {
            if (username != null)
                observable = remoteDataSource.obtemRepositorios(username).doOnNext(repositorios -> ((LocalDataSource) localDataSource).storeRepositorios(repositorios));
            else
                observable = remoteDataSource.obtemRepositorios(page).doOnNext(repositorios -> ((LocalDataSource) localDataSource).storeRepositorios(repositorios));
        }

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        repositorio -> onSuccess.call(repositorio),
                        throwable -> onError.call(throwable));
    }

    public Subscription getRepositorio(Context context, String username, String repo, DataSource.Callback<RepositorioDetalhes> onSuccess, DataSource.Callback<Throwable> onError){

        Observable<RepositorioDetalhes> observable;

        if (!networkHelper.isNetworkAvailable(context)){
            observable = localDataSource.obtemRepositorio(username, repo);
        }else{
            observable = remoteDataSource.obtemRepositorio(username, repo).doOnNext(repositorioDetalhes -> ((LocalDataSource) localDataSource).storeRepositorioDetalhes(repositorioDetalhes));
        }

        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        repositorio -> onSuccess.call(repositorio),
                        throwable -> onError.call(throwable));
    }
}
