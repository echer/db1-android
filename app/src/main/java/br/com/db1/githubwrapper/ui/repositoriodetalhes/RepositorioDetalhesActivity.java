package br.com.db1.githubwrapper.ui.repositoriodetalhes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import br.com.db1.githubwrapper.BaseActivity;
import br.com.db1.githubwrapper.R;
import br.com.db1.githubwrapper.data.model.RepositorioDetalhes;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.db1.githubwrapper.util.Constants.Activity.Extras.GITHUB_DETALHES_REPONAME;
import static br.com.db1.githubwrapper.util.Constants.Activity.Extras.GITHUB_DETALHES_USERNAME;
import static br.com.db1.githubwrapper.util.Constants.Date.PATTERN_BRASIL;

public class RepositorioDetalhesActivity extends BaseActivity implements RepositorioDetalhesContract.View {

    private RepositorioDetalhesActivityComponent repositorioDetalhesActivityComponent;

    @BindView(R.id.repositorioIDNome)
    TextView repositorioNome;

    @BindView(R.id.repositorioDescricao)
    TextView repositorioDescricao;

    @BindView(R.id.repositorioUltimaAtualizacao)
    TextView repositorioUltimaAtualizacao;

    @BindView(R.id.repositorioCountEstrelas)
    TextView repositorioCountEstrelas;

    @BindView(R.id.repositorioCountFork)
    TextView repositorioCountFork;

    @BindView(R.id.repositorioCountInscritos)
    TextView repositorioCountInscritos;

    @BindView(R.id.repositorioCountIncidentes)
    TextView repositorioCountIncidentes;

    @BindView(R.id.repositorioLicensa)
    TextView repositorioLicensa;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    private ProgressDialog progress;

    private boolean isCriacao;
    private String username;
    private String reponame;

    @Inject
    RepositorioDetalhesContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_detalhes);

        username = getIntent().getStringExtra(GITHUB_DETALHES_USERNAME);
        reponame = getIntent().getStringExtra(GITHUB_DETALHES_REPONAME);

        if (username == null || reponame == null) {
            finish();
            return;
        }

        ButterKnife.bind(this);
        initDependencyInjection();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initDependencyInjection();

        if (!isCriacao) {
            isCriacao = true;
            presenter.obterRepositorio(getContext(), username, reponame);
        }
    }

    @Override
    protected void onPause() {
        releaseDependencyInjection();
        super.onPause();
    }

    private void releaseDependencyInjection() {
        repositorioDetalhesActivityComponent = null;
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getResources().getString(R.string.activity_detalhes));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    protected void initDependencyInjection() {
        super.initDependencyInjection();
        if (repositorioDetalhesActivityComponent == null) {
            repositorioDetalhesActivityComponent = DaggerRepositorioDetalhesActivityComponent.builder()
                    .applicationComponent(applicationComponent)
                    .repositorioDetalhesModule(new RepositorioDetalhesModule(this))
                    .build();

            repositorioDetalhesActivityComponent.inject(this);
        }
    }

    @Override
    public void exibirRepositorio(RepositorioDetalhes repositorio) {
        if(repositorio == null) {
            Toast.makeText(this, getString(R.string.repositorio_detalhes_repositorio_nao_encontrado), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        repositorioNome.setText(String.format("%s - %s", String.valueOf(repositorio.getId()), repositorio.getNome()));
        repositorioDescricao.setText(repositorio.getDescricao() != null ? repositorio.getDescricao() : "");
        repositorioUltimaAtualizacao.setText(repositorio.getDataUltimaAtualizacao() != null ? new SimpleDateFormat(PATTERN_BRASIL).format(repositorio.getDataUltimaAtualizacao()) : "");
        repositorioCountEstrelas.setText(repositorio.getCountEstrelas() != null ? String.valueOf(repositorio.getCountEstrelas()) : "");
        repositorioCountFork.setText(repositorio.getCountFork() != null ? String.valueOf(repositorio.getCountFork()) : "");
        repositorioCountInscritos.setText(repositorio.getCountSubscritos() != null ? String.valueOf(repositorio.getCountSubscritos()) : "");
        repositorioCountIncidentes.setText(repositorio.getCountIncidentesAbertos() != null ? String.valueOf(repositorio.getCountIncidentesAbertos()) : "");
        repositorioLicensa.setText(repositorio.getLicensa() != null && repositorio.getLicensa().getNome() != null ? repositorio.getLicensa().getNome() : "");
        Picasso.get()
                .load(repositorio.getDono().getAvatarUrl())
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(image);
    }

    @Override
    public void setProgressBar(boolean show) {
        if (show) {
            progress = new ProgressDialog(this);
            progress.setMessage(getString(R.string.repositorio_detalhes_carregando));
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            if (!this.isFinishing())
                progress.show();
        } else {
            if (progress.isShowing() && !this.isFinishing())
                progress.hide();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
