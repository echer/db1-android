package br.com.db1.githubwrapper.data.remote;

import java.util.List;
import java.util.Map;

import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.data.model.RepositorioDetalhes;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

import static br.com.db1.githubwrapper.data.remote.RemoteDataSource.Api.ENDPOINT_REPOS;
import static br.com.db1.githubwrapper.data.remote.RemoteDataSource.Api.ENDPOINT_REPO_DETAIL;
import static br.com.db1.githubwrapper.data.remote.RemoteDataSource.Api.ENDPOINT_USER_REPOS;

public interface GithubApiService {

    @GET(ENDPOINT_REPOS)
    Observable<List<Repositorio>> obtemRepositorios(@QueryMap Map<String, String> queryMap);

    @GET(ENDPOINT_USER_REPOS)
    Observable<List<Repositorio>> obtemRepositoriosPorUsuario(@Path("dono") String dono);

    @GET(ENDPOINT_REPO_DETAIL)
    Observable<RepositorioDetalhes> obtemRepositorioPorUsuario(@Path("dono") String dono, @Path("repo") String repo);
}
