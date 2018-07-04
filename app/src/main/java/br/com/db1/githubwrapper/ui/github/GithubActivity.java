package br.com.db1.githubwrapper.ui.github;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import br.com.db1.githubwrapper.R;
import br.com.db1.githubwrapper.data.model.Repositorio;

public class GithubActivity extends AppCompatActivity implements GithubContract.View{

    @Inject
    private GithubContract.Presenter presenter;

    SwipeRefreshLayout swipeRefreshLayout;

    TextView connectionPlaceHolder;

    private int pagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //initDependencyInjection();
        registerEventListeners();
    }

    @Override
    protected void onPause() {
        unregisterEventListeners();
        //releaseDependencyInjection();
        super.onPause();
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
