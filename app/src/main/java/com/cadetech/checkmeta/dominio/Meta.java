package com.cadetech.checkmeta.dominio;

/**
 * Created by misael.correia on 23/09/2016.
 */
public class Meta {

    private Long id;
    private Long idUsuario;
    private String titulo;
    private String descricao;
    private String dataDesejada;
    private String status;
    private String dataRealizada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataDesejada() {
        return dataDesejada;
    }

    public void setDataDesejada(String dataDesejada) {
        this.dataDesejada = dataDesejada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(String dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataDesejada='" + dataDesejada + '\'' +
                ", status='" + status + '\'' +
                ", dataRealizada='" + dataRealizada + '\'' +
                '}';
    }

}
