package br.com.db1.githubwrapper.data.model;

import com.google.gson.annotations.SerializedName;

public class Repositorio {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String nome;

    @SerializedName("description")
    private String descricao;

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
