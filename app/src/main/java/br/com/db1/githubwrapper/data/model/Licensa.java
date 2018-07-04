package br.com.db1.githubwrapper.data.model;

import com.google.gson.annotations.SerializedName;

public class Licensa {

    @SerializedName("name")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
