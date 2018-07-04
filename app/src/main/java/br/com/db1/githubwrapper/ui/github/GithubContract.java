package br.com.db1.githubwrapper.ui.github;

import android.content.Context;

import java.util.List;

import br.com.db1.githubwrapper.data.model.Repositorio;
import br.com.db1.githubwrapper.ui.base.IBaseView;

public interface GithubContract {

    interface View extends IBaseView {
        void exibirRepositorios(List<Repositorio> repositorios);

        void showOfflineMsg(boolean networkAvailable);
    }

    interface Presenter {
        void obterRepositorios(Context context, int pagina);

        void subscribeEventStream();

        void unsubscribeEventStream();
    }
}
