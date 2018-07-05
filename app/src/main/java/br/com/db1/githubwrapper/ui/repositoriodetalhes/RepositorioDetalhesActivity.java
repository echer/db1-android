package br.com.db1.githubwrapper.ui.repositoriodetalhes;

import android.os.Bundle;

import br.com.db1.githubwrapper.BaseActivity;
import br.com.db1.githubwrapper.R;
import br.com.db1.githubwrapper.data.model.Repositorio;

import static br.com.db1.githubwrapper.util.Constants.Activity.Extras.GITHUB_DETALHES_REPOSITORIO;

public class RepositorioDetalhesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_detalhes);

        Repositorio repositorio = (Repositorio) getIntent().getSerializableExtra(GITHUB_DETALHES_REPOSITORIO);

        if(repositorio == null) {
            finish();
            return;
        }


    }
}
