package br.com.db1.githubwrapper.ui;

import android.content.Context;
import android.content.IntentFilter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import br.com.db1.githubwrapper.data.DataRepository;
import br.com.db1.githubwrapper.data.DataSource;
import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.data.receivers.ConnectivityBroadcastReceiver;
import br.com.db1.githubwrapper.ui.repositorios.RepositoriosContract;
import br.com.db1.githubwrapper.ui.repositorios.RepositoriosPresenter;
import br.com.db1.githubwrapper.util.NetworkHelper;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresenterRepositoriosTest {

    @Mock
    private DataRepository mockDataRepository;

    @Mock
    List<Repositorio> mockRepositorios;

    @Mock
    private NetworkHelper networkHelper;

    @Mock
    Context mockContext;

    @Mock
    private RepositoriosContract.View mockViewRepositorio;

    @Captor
    private ArgumentCaptor<DataSource.Callback<List<Repositorio>>> onSuccessCaptor;

    @Captor
    private ArgumentCaptor<DataSource.Callback<Throwable>> onErrorCaptor;

    private RepositoriosContract.Presenter repositoriosPresenter;
    private CompositeSubscription mockCompositeSubscription = new CompositeSubscription();

    @Before
    public void setup() {
        IntentFilter intentFilter = new IntentFilter(CONNECTIVITY_ACTION);
        PublishSubject<Boolean> publishSubject = PublishSubject.create();
        ConnectivityBroadcastReceiver connectivityBroadcastReceiver = new ConnectivityBroadcastReceiver(mockContext, networkHelper, intentFilter, publishSubject);
        repositoriosPresenter = new RepositoriosPresenter(mockCompositeSubscription, mockDataRepository, connectivityBroadcastReceiver, mockViewRepositorio);
    }

    @Test
    public void getRepositorios_testCallback() {
        int page = 0;
        when(mockDataRepository
                .getRepositorios(any(Context.class), eq(page), any(DataSource.Callback.class), any(DataSource.Callback.class)))
                .thenReturn(mockCompositeSubscription);

        repositoriosPresenter.obterRepositorios(mockContext, page);

        verify(mockViewRepositorio).setProgressBar(true);
        verify(mockDataRepository).getRepositorios(eq(mockContext), eq(page), onSuccessCaptor.capture(), onErrorCaptor.capture());

        onSuccessCaptor.getValue().call(mockRepositorios);
        onErrorCaptor.getValue().call(any(Throwable.class));

        verify(mockViewRepositorio).exibirRepositorios(mockRepositorios);
    }

    @Test
    public void getRepositoriosPorUsuario_testCallback() {
        String usuario = "echer";
        when(mockDataRepository
                .getRepositorios(any(Context.class), eq(usuario), any(DataSource.Callback.class), any(DataSource.Callback.class)))
                .thenReturn(mockCompositeSubscription);

        repositoriosPresenter.obterRepositorios(mockContext, usuario);

        verify(mockViewRepositorio).setProgressBar(true);
        verify(mockDataRepository).getRepositorios(eq(mockContext), eq(usuario), onSuccessCaptor.capture(), onErrorCaptor.capture());

        onSuccessCaptor.getValue().call(mockRepositorios);
        onErrorCaptor.getValue().call(any(Throwable.class));

        verify(mockViewRepositorio).limparRepositorios();
        verify(mockViewRepositorio).exibirRepositorios(mockRepositorios);
    }

}
