package br.com.db1.githubwrapper.ui.repositorios;

import android.content.Context;

import java.util.List;

import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.ui.base.IBaseView;

public interface RepositoriosContract {

    interface View extends IBaseView {
        void exibirRepositorios(List<Repositorio> repositorios);

        void limparRepositorios();

        Repositorio obtemRepositorioPelaPosicao(int position);

        void showOfflineMsg(boolean networkAvailable);
    }

    interface Presenter {
        void obterRepositorios(Context context, int pagina);

        void obterRepositorios(Context context, String username);

        void subscribeEventStream();

        void unsubscribeEventStream();

        void abreDetalhesDoRepositorio(int position);
    }
}
