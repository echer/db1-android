package br.com.db1.githubwrapper.data.remote;

import java.util.List;
import java.util.Map;

import br.com.db1.githubwrapper.data.model.Repositorio;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

import static br.com.db1.githubwrapper.data.remote.RemoteDataSource.Api.ENDPOINT_REPOS;

public interface GithubApiService {

    @GET(ENDPOINT_REPOS)
    Observable<List<Repositorio>> obtemRepositorios(@QueryMap Map<String, String> queryMap);
}
