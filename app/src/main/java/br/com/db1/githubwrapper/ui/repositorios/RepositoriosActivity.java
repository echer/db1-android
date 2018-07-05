package br.com.db1.githubwrapper.ui.repositorios;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.db1.githubwrapper.BaseActivity;
import br.com.db1.githubwrapper.R;
import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.util.EndlessRecyclerViewScrollListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriosActivity extends BaseActivity implements RepositoriosContract.View {

    @Inject
    RepositoriosContract.Presenter presenter;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerGithubRepos)
    RecyclerView recyclerGithubRepos;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    SearchView searchView;

    private int pagina = 1;
    private boolean isCriacao;
    private boolean isFirstClickBusca = true;
    private boolean isBuscaUsuario = false;

    private RepositoriosActivityComponent repositoriosActivityComponent;
    private RepositoriosRecyclerAdapter recyclerAdapter;
    private EndlessRecyclerViewScrollListener endlessScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        ButterKnife.bind(this);
        initDependencyInjection();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_github_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem searchViewMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchViewMenuItem.getActionView();
        ImageView v = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        v.setImageResource(R.drawable.ic_search_black_24dp);

        searchViewMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                isBuscaUsuario = false;
                swipeRefreshLayout.setEnabled(true);
                limparRepositorios();
                presenter.obterRepositorios(getContext(), 1);
                return true;
            }
        });

        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                isBuscaUsuario = true;
                swipeRefreshLayout.setEnabled(false);
                presenter.obterRepositorios(getContext(), query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (isFirstClickBusca) {
                    isFirstClickBusca = false;
                    return false;
                }

                if (newText == null || newText.isEmpty()) {
                    isBuscaUsuario = false;
                    swipeRefreshLayout.setEnabled(true);
                    limparRepositorios();
                    presenter.obterRepositorios(getContext(), 1);
                }
                return false;
            }
        });
        return true;
    }

    private void initViews() {
        recyclerAdapter = new RepositoriosRecyclerAdapter(presenter, new ArrayList<>());
        recyclerGithubRepos.setAdapter(recyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerGithubRepos.setLayoutManager(linearLayoutManager);

        endlessScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager, pagina) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!isBuscaUsuario)
                    presenter.obterRepositorios(getContext().getApplicationContext(), page);
            }
        };

        recyclerGithubRepos.addOnScrollListener(endlessScrollListener);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (!isBuscaUsuario)
                presenter.obterRepositorios(getContext().getApplicationContext(), 1);
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary);
    }

    protected void initDependencyInjection() {
        super.initDependencyInjection();
        if (repositoriosActivityComponent == null) {
            repositoriosActivityComponent = DaggerRepositoriosActivityComponent.builder()
                    .applicationComponent(applicationComponent)
                    .repositoriosModule(new RepositoriosModule(this))
                    .build();

            repositoriosActivityComponent.inject(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        initDependencyInjection();
        registerEventListeners();

        if (!isCriacao) {
            isCriacao = true;
            presenter.obterRepositorios(getContext().getApplicationContext(), 1);
        }
    }

    @Override
    protected void onPause() {
        unregisterEventListeners();
        releaseDependencyInjection();
        super.onPause();
    }

    private void releaseDependencyInjection() {
        repositoriosActivityComponent = null;
    }

    @Override
    public void exibirRepositorios(List<Repositorio> repositorios) {
        recyclerAdapter.addAll(repositorios);
    }

    @Override
    public void limparRepositorios() {
        recyclerAdapter.clear();
    }

    @Override
    public Repositorio obtemRepositorioPelaPosicao(int position) {
        return recyclerAdapter.getRepositorios().get(position);
    }

    @Override
    public void showOfflineMsg(boolean networkAvailable) {
        if (!networkAvailable)
            Toast.makeText(this, getString(R.string.offline_message), Toast.LENGTH_SHORT).show();
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
