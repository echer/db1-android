package br.com.db1.githubwrapper.data;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;

import br.com.db1.githubwrapper.data.local.LocalDataSource;
import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.data.model.RepositorioDetalhes;
import br.com.db1.githubwrapper.data.remote.RemoteDataSource;
import br.com.db1.githubwrapper.util.NetworkHelper;
import br.com.db1.githubwrapper.util.RxJavaTestRunner;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RxJavaTestRunner.class)
public class DataRepositoryTeste {

    @Mock
    LocalDataSource mockLocalDataSource;

    @Mock
    RemoteDataSource mockRemoteDataSource;

    @Mock
    NetworkHelper mockNetworkHelper;

    @Mock
    Context mockContext;

    @Mock
    List<Repositorio> mockRepositorios;

    @Mock
    RepositorioDetalhes mockRepositoriosDetalhes;

    @Mock
    DataSource.Callback<List<Repositorio>> mockOnSuccess;

    @Mock
    DataSource.Callback<RepositorioDetalhes> mockOnSuccessDetalhes;

    @Mock
    DataSource.Callback<Throwable> mockOnError;

    DataRepository dataRepository;

    @Before
    public void setup() {
        dataRepository = new DataRepository(mockRemoteDataSource, mockLocalDataSource,
                mockNetworkHelper);
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void getRepositoriosDetalhesUsuario_deveObterDoRepositorioRemotoEArmazenarLocal(){
        String anyString = "";

        //GARANTE QUE A CHAMADA DA VERIFICAÇÃO DE REDE SEMPRE RETORNE TRUE
        when(mockNetworkHelper.isNetworkAvailable(mockContext)).thenReturn(true);

        //GARANTE QUE A CHAMADA SEMPRE RETORNE UM OBSERVER DOS DETALHES DO REPOSITORIO
        when(mockRemoteDataSource.obtemRepositorio(anyString, anyString)).thenReturn(Observable.just(mockRepositoriosDetalhes));

        //TESTA O MÉTODO PARA BUSCAR OS DETALHES DO REPOSITORIO
        dataRepository.getRepositorio(mockContext, anyString, anyString, mockOnSuccessDetalhes, mockOnError);

        //VERIFICA O METODO DO REMOTE DATASOURCE PARA OBTER OS DETALHES DO REPOSITORIO
        verify(mockRemoteDataSource).obtemRepositorio(anyString, anyString);
        //VERIFICA O METODO DO LOCAL DATASROUCE PARA SALVAR OS DETALHES DO REPOSITORIO
        verify(mockLocalDataSource).storeRepositorioDetalhes(mockRepositoriosDetalhes);

        //VERIFICA SE O CALLBACK DE SUCESSO CHAMOU A OS DETALHES DO REPOSITORIO
        verify(mockOnSuccessDetalhes).call(mockRepositoriosDetalhes);

        //VERIFICA SE NÃO ESTOUROU NENHUM ERRO NO CALLBACK
        verify(mockOnError, never()).call(any(Throwable.class));
    }

    @Test
    public void getRepositoriosUsuario_deveObterDoRepositorioRemotoEArmazenarLocal(){
        //GARANTE QUE A CHAMADA DA VERIFICAÇÃO DE REDE SEMPRE RETORNE TRUE
        when(mockNetworkHelper.isNetworkAvailable(mockContext)).thenReturn(true);

        //GARANTE QUE A CHAMADA SEMPRE RETORNE UM OBSERVER DA LISTA DE REPOSITORIOS
        when(mockRemoteDataSource.obtemRepositorios(anyString())).thenReturn(Observable.just(mockRepositorios));

        //TESTA O MÉTODO PARA BUSCAR OS REPOSITORIOS
        dataRepository.getRepositorios(mockContext, anyString(), mockOnSuccess, mockOnError);

        //VERIFICA O METODO DO REMOTE DATASOURCE PARA OBTER OS REPOSITORIOS
        verify(mockRemoteDataSource).obtemRepositorios(anyString());

        //VERIFICA O METODO DO LOCAL DATASROUCE PARA SALVAR OS REPOSITORIOS
        verify(mockLocalDataSource).storeRepositorios(mockRepositorios);

        //VERIFICA SE O CALLBACK DE SUCESSO CHAMOU A LISTA DE REPOSITORIOS
        verify(mockOnSuccess).call(mockRepositorios);

        //VERIFICA SE NÃO ESTOUROU NENHUM ERRO NO CALLBACK
        verify(mockOnError, never()).call(any(Throwable.class));
    }

    @Test
    public void getRepositorios_deveObterDoRepositorioRemotoEArmazenarLocal(){
        //GARANTE QUE A CHAMADA DA VERIFICAÇÃO DE REDE SEMPRE RETORNE TRUE
        when(mockNetworkHelper.isNetworkAvailable(mockContext)).thenReturn(true);

        //GARANTE QUE A CHAMADA SEMPRE RETORNE UM OBSERVER DA LISTA DE REPOSITORIOS
        when(mockRemoteDataSource.obtemRepositorios(anyInt())).thenReturn(Observable.just(mockRepositorios));

        //TESTA O MÉTODO PARA BUSCAR OS REPOSITORIOS
        dataRepository.getRepositorios(mockContext, anyInt(), mockOnSuccess, mockOnError);

        //VERIFICA O METODO DO REMOTE DATASOURCE PARA OBTER OS REPOSITORIOS
        verify(mockRemoteDataSource).obtemRepositorios(anyInt());

        //VERIFICA O METODO DO LOCAL DATASROUCE PARA SALVAR OS REPOSITORIOS
        verify(mockLocalDataSource).storeRepositorios(mockRepositorios);

        //VERIFICA SE O CALLBACK DE SUCESSO CHAMOU A LISTA DE REPOSITORIOS
        verify(mockOnSuccess).call(mockRepositorios);

        //VERIFICA SE NÃO ESTOUROU NENHUM ERRO NO CALLBACK
        verify(mockOnError, never()).call(any(Throwable.class));
    }

    @Test
    public void getRepositorios_deveObterDoRepositorioLocal(){
        //GARANTE QUE A CHAMADA DA VERIFICAÇÃO DE REDE SEMPRE RETORNE FALSE
        when(mockNetworkHelper.isNetworkAvailable(mockContext)).thenReturn(false);

        //GARANTE QUE A CHAMADA SEMPRE RETORNE UM OBSERVER DA LISTA DE REPOSITORIOS
        when(mockLocalDataSource.obtemRepositorios(anyInt())).thenReturn(Observable.just(mockRepositorios));

        //TESTA O MÉTODO PARA BUSCAR OS REPOSITORIOS
        dataRepository.getRepositorios(mockContext, anyInt(), mockOnSuccess, mockOnError);

        //VERIFICA O METODO DO LOCAL DATASOURCE PARA OBTER OS REPOSITORIOS
        verify(mockLocalDataSource).obtemRepositorios(anyInt());

        //VERIFICA SE O REMOTE REPOSITORY NÃO FOI CHAMADO
        verify(mockRemoteDataSource, never()).obtemRepositorios(anyInt());

        //VERIFICA SE O LOCAL REPOSITORY NÃO FOI CHAMADO PARA ARMAZENAR OS REPOSITORIOS
        verify(mockLocalDataSource, never()).storeRepositorios(mockRepositorios);

        //VERIFICA O METODO DO LOCAL DATASOURCE PARA OBTER OS REPOSITORIOS
        verify(mockLocalDataSource).obtemRepositorios(anyInt());

        //VERIFICA SE O CALLBACK DE SUCESSO CHAMOU A LISTA DE REPOSITORIOS
        verify(mockOnSuccess).call(mockRepositorios);

        //VERIFICA SE NÃO ESTOUROU NENHUM ERRO NO CALLBACK
        verify(mockOnError, never()).call(any(Throwable.class));
    }

    @Test
    public void getRepositoriosUsuario_deveObterDoRepositorioLocal(){
        //GARANTE QUE A CHAMADA DA VERIFICAÇÃO DE REDE SEMPRE RETORNE FALSE
        when(mockNetworkHelper.isNetworkAvailable(mockContext)).thenReturn(false);

        //GARANTE QUE A CHAMADA SEMPRE RETORNE UM OBSERVER DA LISTA DE REPOSITORIOS
        when(mockLocalDataSource.obtemRepositorios(anyString())).thenReturn(Observable.just(mockRepositorios));

        //TESTA O MÉTODO PARA BUSCAR OS REPOSITORIOS
        dataRepository.getRepositorios(mockContext, anyString(), mockOnSuccess, mockOnError);

        //VERIFICA O METODO DO LOCAL DATASOURCE PARA OBTER OS REPOSITORIOS
        verify(mockLocalDataSource).obtemRepositorios(anyString());

        //VERIFICA SE O REMOTE REPOSITORY NÃO FOI CHAMADO
        verify(mockRemoteDataSource, never()).obtemRepositorios(anyString());

        //VERIFICA SE O LOCAL REPOSITORY NÃO FOI CHAMADO PARA ARMAZENAR OS REPOSITORIOS
        verify(mockLocalDataSource, never()).storeRepositorios(mockRepositorios);

        //VERIFICA O METODO DO LOCAL DATASOURCE PARA OBTER OS REPOSITORIOS
        verify(mockLocalDataSource).obtemRepositorios(anyString());

        //VERIFICA SE O CALLBACK DE SUCESSO CHAMOU A LISTA DE REPOSITORIOS
        verify(mockOnSuccess).call(mockRepositorios);

        //VERIFICA SE NÃO ESTOUROU NENHUM ERRO NO CALLBACK
        verify(mockOnError, never()).call(any(Throwable.class));
    }

    @Test
    public void getRepositorioDetalhesUsuario_deveObterDoRepositorioLocal(){
        String anyString = "";

        //GARANTE QUE A CHAMADA DA VERIFICAÇÃO DE REDE SEMPRE RETORNE FALSE
        when(mockNetworkHelper.isNetworkAvailable(mockContext)).thenReturn(false);

        //GARANTE QUE A CHAMADA SEMPRE RETORNE UM OBSERVER DOS DETALHES DO REPOSITORIO
        when(mockLocalDataSource.obtemRepositorio(anyString, anyString)).thenReturn(Observable.just(mockRepositoriosDetalhes));

        //TESTA O MÉTODO PARA BUSCAR OS DETALHES DO REPOSITORIO
        dataRepository.getRepositorio(mockContext, anyString, anyString, mockOnSuccessDetalhes, mockOnError);

        //VERIFICA O METODO DO LOCAL DATASOURCE PARA OBTER OS DETALHES DO REPOSITORIO
        verify(mockLocalDataSource).obtemRepositorio(anyString, anyString);

        //VERIFICA SE O REMOTE REPOSITORY NÃO FOI CHAMADO
        verify(mockRemoteDataSource, never()).obtemRepositorio(anyString, anyString);

        //VERIFICA SE O LOCAL REPOSITORY NÃO FOI CHAMADO PARA ARMAZENAR OS DETALHES DO REPOSITORIO
        verify(mockLocalDataSource, never()).storeRepositorioDetalhes(mockRepositoriosDetalhes);

        //VERIFICA O METODO DO LOCAL DATASOURCE PARA OBTER OS DETALHES DO REPOSITORIO
        verify(mockLocalDataSource).obtemRepositorio(anyString, anyString);

        //VERIFICA SE O CALLBACK DE SUCESSO CHAMOU OS DETALHES DO REPOSITORIO
        verify(mockOnSuccessDetalhes).call(mockRepositoriosDetalhes);

        //VERIFICA SE NÃO ESTOUROU NENHUM ERRO NO CALLBACK
        verify(mockOnError, never()).call(any(Throwable.class));
    }
}
