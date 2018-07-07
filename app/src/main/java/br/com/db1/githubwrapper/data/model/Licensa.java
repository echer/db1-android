package br.com.db1.githubwrapper.data.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import br.com.db1.githubwrapper.data.local.LocalDatabase;

@Table(database = LocalDatabase.class, allFields = true)
public class Licensa extends BaseModel {

    @PrimaryKey
    @SerializedName("name")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
