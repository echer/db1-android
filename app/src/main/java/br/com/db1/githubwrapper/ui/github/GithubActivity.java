package br.com.db1.githubwrapper.ui.github;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import br.com.db1.githubwrapper.BaseActivity;
import br.com.db1.githubwrapper.BaseApplication;
import br.com.db1.githubwrapper.R;
import br.com.db1.githubwrapper.data.model.Repositorio;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GithubActivity extends BaseActivity implements GithubContract.View{

    @Inject
    GithubContract.Presenter presenter;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private int pagina;

    private GithubActivityComponent githubActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        ButterKnife.bind(this);
        initDependencyInjection();
    }

    protected void initDependencyInjection() {
        super.initDependencyInjection();
        if (githubActivityComponent == null) {
            githubActivityComponent = DaggerGithubActivityComponent.builder()
                    .applicationComponent(applicationComponent)
                    .githubModule(new GithubModule(this))
                    .build();

            githubActivityComponent.inject(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDependencyInjection();
        registerEventListeners();
    }

    @Override
    protected void onPause() {
        unregisterEventListeners();
        releaseDependencyInjection();
        super.onPause();
    }

    private void releaseDependencyInjection() {
        githubActivityComponent = null;
    }

    @Override
    public void exibirRepositorios(List<Repositorio> repositorios) {

    }

    @Override
    public void showOfflineMsg(boolean networkAvailable) {
        if(!networkAvailable) {
            Toast.makeText(this, getString(R.string.offline_message), Toast.LENGTH_SHORT).show();
            return;
        }

        presenter.obterRepositorios(this, pagina);
    }

    private void registerEventListeners() {
        presenter.subscribeEventStream();
    }

    private void unregisterEventListeners() {
        presenter.unsubscribeEventStream();
    }

    @Override
    public void setProgressBar(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
