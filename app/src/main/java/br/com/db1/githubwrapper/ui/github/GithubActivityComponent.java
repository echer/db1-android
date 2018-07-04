package br.com.db1.githubwrapper.ui.github;

import br.com.db1.githubwrapper.ApplicationComponent;
import br.com.db1.githubwrapper.data.receivers.ReceiversModule;
import br.com.db1.githubwrapper.di.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {GithubModule.class, ReceiversModule.class}
)
public interface GithubActivityComponent {

    GithubContract.Presenter getGithubActivityPresenter();

    void inject(GithubActivity githubActivity);
}
