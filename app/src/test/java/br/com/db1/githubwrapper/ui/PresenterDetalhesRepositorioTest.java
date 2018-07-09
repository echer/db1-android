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

import br.com.db1.githubwrapper.data.DataRepository;
import br.com.db1.githubwrapper.data.DataSource;
import br.com.db1.githubwrapper.data.model.RepositorioDetalhes;
import br.com.db1.githubwrapper.data.receivers.ConnectivityBroadcastReceiver;
import br.com.db1.githubwrapper.ui.repositoriodetalhes.RepositorioDetalhesContract;
import br.com.db1.githubwrapper.ui.repositoriodetalhes.RepositorioDetalhesPresenter;
import br.com.db1.githubwrapper.util.NetworkHelper;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresenterDetalhesRepositorioTest {

    @Mock
    private DataRepository mockDataRepository;

    @Mock
    private NetworkHelper networkHelper;

    @Mock
    Context mockContext;

    @Mock
    RepositorioDetalhes mockRepositorioDetalhes;

    @Mock
    RepositorioDetalhesContract.View mockViewRepositorioDetalhes;

    @Captor
    private ArgumentCaptor<DataSource.Callback<RepositorioDetalhes>> onSuccessCaptor;

    @Captor
    private ArgumentCaptor<DataSource.Callback<Throwable>> onErrorCaptor;

    private RepositorioDetalhesContract.Presenter repositorioDetalhesPresenter;
    private CompositeSubscription mockCompositeSubscription = new CompositeSubscription();

    @Before
    public void setup() {
        repositorioDetalhesPresenter = new RepositorioDetalhesPresenter(mockCompositeSubscription, mockViewRepositorioDetalhes, mockDataRepository);
    }

    @Test
    public void getRepositorioDetalhes_testCallback() {
        String usuario = "echer";
        String repo = "db1-android";

        when(mockDataRepository
                .getRepositorio(any(Context.class), eq(usuario), eq(repo), any(DataSource.Callback.class), any(DataSource.Callback.class)))
                .thenReturn(mockCompositeSubscription);

        repositorioDetalhesPresenter.obterRepositorio(mockContext, usuario, repo);

        verify(mockViewRepositorioDetalhes).setProgressBar(true);
        verify(mockDataRepository).getRepositorio(eq(mockContext), eq(usuario), eq(repo), onSuccessCaptor.capture(), onErrorCaptor.capture());

        onSuccessCaptor.getValue().call(mockRepositorioDetalhes);
        onErrorCaptor.getValue().call(any(Throwable.class));

        verify(mockViewRepositorioDetalhes).exibirRepositorio(mockRepositorioDetalhes);
    }
}
