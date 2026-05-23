package br.cinenoir.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bilhetes")
public class Bilhete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int assento;
    private double valor;
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    public Bilhete() {}

    public Long getId() { return id; }
    public int getAssento() { return assento; }
    public void setAssento(int assento) { this.assento = assento; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Sessao getSessao() { return sessao; }
    public void setSessao(Sessao sessao) { this.sessao = sessao; }
}
