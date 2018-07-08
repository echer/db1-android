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
    DataSource.Callback<List<Repositorio>> mockOnSuccess;

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
    public void getRepositorios_deveObterDoRepositorioRemotoEArmazenarLocal(){
        //GARANTE QUE A CHAMADA DA VERIFICAÇÃO DE REDE SEMPRE RETORNE TRUE
        when(mockNetworkHelper.isNetworkAvailable(mockContext)).thenReturn(true);

        //GARANTE QUE A CHAMADA SEMPRE RETORNE UM OBSERVER DA LISTA DE REPOSITORIOS
        when(mockRemoteDataSource.obtemRepositorios(anyInt())).thenReturn(Observable.just(mockRepositorios));

        //TESTA O MÉTODO PARA BUSCAR OS REPOSITORIOS
        dataRepository.getRepositorios(mockContext, anyInt(), mockOnSuccess, mockOnError);

        //TESTA O METODO DO REMOTE DATASOURCE PARA OBTER OS REPOSITORIOS
        verify(mockRemoteDataSource).obtemRepositorios(anyInt());
        //TESTA O METODO DO LOCAL DATASROUCE PARA SALVAR OS REPOSITORIOS
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

        //TESTA O METODO DO LOCAL DATASOURCE PARA OBTER OS REPOSITORIOS
        verify(mockLocalDataSource).obtemRepositorios(anyInt());

        //TESTA SE O REMOTE REPOSITORY NÃO FOI CHAMADO
        verify(mockRemoteDataSource, never()).obtemRepositorios(anyInt());

        //TESTA SE O LOCAL REPOSITORY NÃO FOI CHAMADO PARA ARMAZENAR AS FOTOS
        verify(mockLocalDataSource, never()).storeRepositorios(mockRepositorios);

        //TESTA O METODO DO LOCAL DATASOURCE PARA OBTER OS REPOSITORIOS
        verify(mockLocalDataSource).obtemRepositorios(anyInt());

        //VERIFICA SE O CALLBACK DE SUCESSO CHAMOU A LISTA DE REPOSITORIOS
        verify(mockOnSuccess).call(mockRepositorios);

        //VERIFICA SE NÃO ESTOUROU NENHUM ERRO NO CALLBACK
        verify(mockOnError, never()).call(any(Throwable.class));
    }
}
