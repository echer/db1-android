package br.com.db1.githubwrapper.ui.repositoriodetalhes;

import android.content.Context;

import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.ui.base.IBaseView;

public interface RepositorioDetalhesContract {

    interface View extends IBaseView{
        void exibirRepositorio(Repositorio repositorio);
    }

    interface Presenter{
        void obterRepositorio(Context context, String username, String repo);
    }
}
