package br.com.db1.githubwrapper.data.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.db1.githubwrapper.data.DataSource;
import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.data.model.RepositorioDetalhes;
import br.com.db1.githubwrapper.util.MainUiThread;
import br.com.db1.githubwrapper.util.ThreadExecutor;
import rx.Observable;

import static br.com.db1.githubwrapper.data.remote.RemoteDataSource.Api.QueryParams.QUERY_PARAM_SINCE;

public class RemoteDataSource extends DataSource {

    interface Api {
        String BASE_URL = "https://api.github.com";
        String ENDPOINT_REPOS = "/repositories";
        String ENDPOINT_USER_REPOS = "/users/{dono}/repos";
        String ENDPOINT_REPO_DETAIL = "/repos/{dono}/{repo}";

        interface QueryParams {
            String QUERY_PARAM_SINCE = "since";
        }
    }

    private GithubApiService apiService;

    public RemoteDataSource(MainUiThread mainUiThread,
                            ThreadExecutor threadExecutor, GithubApiService apiService) {
        super(mainUiThread, threadExecutor);
        this.apiService = apiService;
    }

    @Override
    public Observable<List<Repositorio>> obtemRepositorios(int page) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(QUERY_PARAM_SINCE, String.valueOf(page == 1 ? 1 : (page * 100 + 1)));
        return apiService.obtemRepositorios(queryMap)
                .flatMap(response -> Observable.just(response));
    }

    @Override
    public Observable<List<Repositorio>> obtemRepositorios(String username) {
        return apiService.obtemRepositoriosPorUsuario(username)
                .flatMap(response -> Observable.just(response));
    }

    @Override
    public Observable<RepositorioDetalhes> obtemRepositorio(String username, String repo) {
        return apiService.obtemRepositorioPorUsuario(username, repo)
                .flatMap(response -> Observable.just(response));
    }


}
