package br.com.db1.githubwrapper.util;

public interface Constants {

    interface Activity{
        interface Extras{
            String GITHUB_DETALHES_USERNAME = "repositorio_username";
            String GITHUB_DETALHES_REPONAME = "repositorio_nome";
        }
    }

    interface Date {
        String PATTERN_BRASIL = "dd/MM/yyyy";
    }

    interface Database{
        String DATABASE_NAME = "db1.db";
        int VERSION = 1;
    }
}
