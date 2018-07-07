package br.com.db1.githubwrapper.data.local;

import com.raizlabs.android.dbflow.annotation.Database;

import static br.com.db1.githubwrapper.util.Constants.Database.DATABASE_NAME;
import static br.com.db1.githubwrapper.util.Constants.Database.VERSION;

@Database(name = DATABASE_NAME, version = VERSION)
public class LocalDatabase {
}