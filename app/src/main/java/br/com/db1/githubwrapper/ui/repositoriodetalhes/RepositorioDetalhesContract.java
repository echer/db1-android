package br.com.db1.githubwrapper.ui.repositoriodetalhes;

import android.content.Context;

import br.com.db1.githubwrapper.data.model.RepositorioDetalhes;
import br.com.db1.githubwrapper.ui.base.IBaseView;

public interface RepositorioDetalhesContract {

    interface View extends IBaseView {
        void exibirRepositorio(RepositorioDetalhes repositorio);
    }

    interface Presenter {
        void obterRepositorio(Context context, String username, String repo);
    }
}
