package br.cinenoir.model;
import jakarta.persistence.*;
@Entity
@Table(name = "critica")
public class Critica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String mensagem;
    private double nota;
    private String origem;
    @ManyToOne
    @JoinColumn(name = "filme_id")
    private Filme filme;
    public Critica() {}
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
    public Filme getFilme() { return filme; }
    public void setFilme(Filme filme) { this.filme = filme; }
}
