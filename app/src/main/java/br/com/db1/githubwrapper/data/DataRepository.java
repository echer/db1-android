package br.com.db1.githubwrapper.data;

import android.content.Context;

import java.util.List;

import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.util.NetworkHelper;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataRepository {

    private NetworkHelper networkHelper;
    private DataSource remoteDataSource;

    public DataRepository(DataSource remoteDataSource, NetworkHelper networkHelper) {
        this.remoteDataSource = remoteDataSource;
        this.networkHelper = networkHelper;
    }

    public Subscription getRepositorios(Context context, int page, DataSource.Callback<List<Repositorio>> onSuccess, DataSource.Callback<Throwable> onError) {
        return getRepositorios(context, null, page, onSuccess, onError);
    }

    public Subscription getRepositorios(Context context, String username, int page, DataSource.Callback<List<Repositorio>> onSuccess, DataSource.Callback<Throwable> onError) {

        if (!networkHelper.isNetworkAvailable(context))
            return null;

        Observable<List<Repositorio>> observable;

        if (username != null)
            observable = remoteDataSource.obtemRepositorios(username);
        else
            observable = remoteDataSource.obtemRepositorios(page);

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        repositorio -> onSuccess.call(repositorio),
                        throwable -> onError.call(throwable));
    }

    public Subscription getRepositorio(Context context, String username, String repo,DataSource.Callback<Repositorio> onSuccess, DataSource.Callback<Throwable> onError){

        if (!networkHelper.isNetworkAvailable(context))
            return null;

        return remoteDataSource.obtemRepositorio(username, repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        repositorio -> onSuccess.call(repositorio),
                        throwable -> onError.call(throwable));
    }
}
