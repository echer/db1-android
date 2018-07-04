package br.com.db1.githubwrapper.data;

import java.util.List;

import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.util.MainUiThread;
import br.com.db1.githubwrapper.util.ThreadExecutor;
import rx.Observable;

public abstract class DataSource {

    protected MainUiThread mainUiThread;
    protected ThreadExecutor threadExecutor;

    public DataSource(MainUiThread mainUiThread, ThreadExecutor threadExecutor) {
        this.mainUiThread = mainUiThread;
        this.threadExecutor = threadExecutor;
    }

    public interface Callback<T> {
        void call(T t);
    }

    public abstract Observable<List<Repositorio>> obtemRepositorios(int page);

}
