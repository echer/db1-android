package br.com.db1.githubwrapper.data.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.db1.githubwrapper.BuildConfig;
import br.com.db1.githubwrapper.data.DataSource;
import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.util.MainUiThread;
import br.com.db1.githubwrapper.util.ThreadExecutor;
import rx.Observable;

import static br.com.db1.githubwrapper.data.remote.RemoteDataSource.Api.QueryParams.QUERY_PARAM_PAGE;

public class RemoteDataSource extends DataSource {

    interface Api{
        String BASE_URL = "https://api.github.com";
        String ENDPOINT_REPOS = "/repositories";
        String ENDPOINT_USER_REPOS = "/users/%s/repos";
        String ENDPOINT_REPO_DETAIL = "/repos/%s/%s";

        interface QueryParams{
            String QUERY_PARAM_PAGE = "page";
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
        queryMap.put(QUERY_PARAM_PAGE, String.valueOf(page));

        return apiService.obtemRepositorios(queryMap)
                .flatMap(response -> Observable.just(response));
    }

}
