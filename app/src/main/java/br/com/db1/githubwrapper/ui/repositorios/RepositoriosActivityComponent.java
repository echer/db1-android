package br.com.db1.githubwrapper.ui.repositorios;

import br.com.db1.githubwrapper.ApplicationComponent;
import br.com.db1.githubwrapper.data.receivers.ReceiversModule;
import br.com.db1.githubwrapper.di.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {RepositoriosModule.class, ReceiversModule.class}
)
public interface RepositoriosActivityComponent {

    void inject(RepositoriosActivity repositoriosActivity);
}
