package br.com.db1.githubwrapper.data.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

import br.com.db1.githubwrapper.data.local.LocalDatabase;

@Table(database = LocalDatabase.class, allFields = true)
public class RepositorioDetalhes extends BaseModel {

    @PrimaryKey
    @SerializedName("id")
    private long id;

    @SerializedName("owner")
    @ForeignKey(stubbedRelationship = true)
    private Dono dono;

    @SerializedName("name")
    private String nome;

    @SerializedName("description")
    private String descricao;

    @SerializedName("updated_at")
    private Date dataUltimaAtualizacao;

    @SerializedName("stargazers_count")
    private Long countEstrelas;

    @SerializedName("forks_count")
    private Long countFork;

    @SerializedName("watchers_count")
    private Long countSubscritos;

    @SerializedName("open_issues_count")
    private Long countIncidentesAbertos;

    @SerializedName("license")
    @ForeignKey(stubbedRelationship = true)
    private Licensa licensa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dono getDono() {
        return dono;
    }

    public void setDono(Dono dono) {
        this.dono = dono;
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

    public Date getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public Long getCountEstrelas() {
        return countEstrelas;
    }

    public void setCountEstrelas(Long countEstrelas) {
        this.countEstrelas = countEstrelas;
    }

    public Long getCountFork() {
        return countFork;
    }

    public void setCountFork(Long countFork) {
        this.countFork = countFork;
    }

    public Long getCountSubscritos() {
        return countSubscritos;
    }

    public void setCountSubscritos(Long countSubscritos) {
        this.countSubscritos = countSubscritos;
    }

    public Long getCountIncidentesAbertos() {
        return countIncidentesAbertos;
    }

    public void setCountIncidentesAbertos(Long countIncidentesAbertos) {
        this.countIncidentesAbertos = countIncidentesAbertos;
    }

    public Licensa getLicensa() {
        return licensa;
    }

    public void setLicensa(Licensa licensa) {
        this.licensa = licensa;
    }
}
