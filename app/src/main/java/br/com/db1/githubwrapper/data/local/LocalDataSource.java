package br.com.db1.githubwrapper.data.local;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import br.com.db1.githubwrapper.data.DataSource;
import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.data.model.RepositorioDetalhes;
import br.com.db1.githubwrapper.data.model.RepositorioDetalhes_Table;
import br.com.db1.githubwrapper.data.model.Repositorio_Table;
import br.com.db1.githubwrapper.util.MainUiThread;
import br.com.db1.githubwrapper.util.ThreadExecutor;
import rx.Observable;
import rx.schedulers.Schedulers;

public class LocalDataSource extends DataSource {

    private DatabaseDefinition databaseDefinition;

    public LocalDataSource(MainUiThread mainUiThread, ThreadExecutor threadExecutor,
                           DatabaseDefinition databaseDefinition) {
        super(mainUiThread, threadExecutor);
        this.databaseDefinition = databaseDefinition;
    }
    @Override
    public Observable<List<Repositorio>> obtemRepositorios(int page) {
        return Observable.fromCallable(() -> getRepositoriosFromDb(page));
    }

    @Override
    public Observable<List<Repositorio>> obtemRepositorios(String username) {
        return Observable.fromCallable(() -> getRepositoriosFromDb(username));
    }

    @Override
    public Observable<RepositorioDetalhes> obtemRepositorio(String username, String repo) {
        return Observable.fromCallable(() -> getRepositorioDetalhesFromDb(username, repo));
    }

    private List<Repositorio> getRepositoriosFromDb(int page) {
        return SQLite.select()
                .from(Repositorio.class)
                .limit(101)
                .offset((page - 1) * 101)
                .queryList();
    }

    private List<Repositorio> getRepositoriosFromDb(String username) {
        return SQLite.select()
                .from(Repositorio.class)
                .where(Repositorio_Table.dono_login.eq(username))
                .queryList();
    }

    private RepositorioDetalhes getRepositorioDetalhesFromDb(String username, String repo) {
        RepositorioDetalhes repositorioDetalhes = SQLite.select()
                .from(RepositorioDetalhes.class)
                .where(RepositorioDetalhes_Table.dono_login.eq(username))
                .and(RepositorioDetalhes_Table.nome.eq(repo))
                .querySingle();
        return repositorioDetalhes;
    }

    public void storeRepositorios(final List<Repositorio> repositorios){
        Observable.fromCallable(() -> saveRepositorios(repositorios))
                .subscribeOn(Schedulers.io())
                .publish()
                .connect();
    }

    private boolean saveRepositorios(List<Repositorio> repositorios){
        for(Repositorio repositorio:repositorios) {
            repositorio.getDono().save();
            repositorio.save();
        }
        return true;
    }

    public void storeRepositorioDetalhes(final RepositorioDetalhes repositorioDetalhes){
        Observable.fromCallable(() -> saveRepositorioDetalhes(repositorioDetalhes))
                .subscribeOn(Schedulers.io())
                .publish()
                .connect();
    }

    private boolean saveRepositorioDetalhes(RepositorioDetalhes repositorioDetalhes){
        repositorioDetalhes.getDono().save();
        if(repositorioDetalhes.getLicensa() != null)
            repositorioDetalhes.getLicensa().save();
        repositorioDetalhes.save();
        return true;
    }
}
