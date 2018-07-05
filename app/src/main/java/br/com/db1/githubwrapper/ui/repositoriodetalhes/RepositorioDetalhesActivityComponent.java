package br.com.db1.githubwrapper.ui.repositoriodetalhes;

import br.com.db1.githubwrapper.ApplicationComponent;
import br.com.db1.githubwrapper.di.ActivityScope;
import br.com.db1.githubwrapper.ui.repositorios.RepositoriosModule;
import dagger.Component;

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {RepositoriosModule.class}
)
public interface RepositorioDetalhesActivityComponent {

    void inject(RepositorioDetalhesActivity repositorioDetalhesActivity);
}
