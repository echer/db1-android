package br.com.db1.githubwrapper.data.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import br.com.db1.githubwrapper.data.local.LocalDatabase;

@Table(database = LocalDatabase.class, allFields = true)
public class Repositorio extends BaseModel {

    @PrimaryKey
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String nome;

    @SerializedName("description")
    private String descricao;

    @ForeignKey(stubbedRelationship = true)
    @SerializedName("owner")
    private Dono dono;

    public Dono getDono() {
        return dono;
    }

    public void setDono(Dono dono) {
        this.dono = dono;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
