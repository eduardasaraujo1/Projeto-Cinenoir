package br.cinenoir.model;

import jakarta.persistence.*;

@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int duracao;
    private String sinopse;
    private double valor;
    private double nota;
    private int quantidadeCriticos;
    private String genero;
    private String posterUrl;
    private boolean emCartaz;

    public Filme() {}

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
    public int getQuantidadeCriticos() { return quantidadeCriticos; }
    public void setQuantidadeCriticos(int quantidadeCriticos) { this.quantidadeCriticos = quantidadeCriticos; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public boolean isEmCartaz() { return emCartaz; }
    public void setEmCartaz(boolean emCartaz) { this.emCartaz = emCartaz; }
}
