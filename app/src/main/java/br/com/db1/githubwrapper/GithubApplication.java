package br.com.db1.githubwrapper;

public class GithubApplication extends BaseApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void initApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}