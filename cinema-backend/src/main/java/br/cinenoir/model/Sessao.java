package br.cinenoir.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sessoes")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String horario;
    private String tipoSala;
    private boolean encerrada;
    private boolean emCartaz;
    private String assentosOcupados;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private Filme filme;

    public Sessao() {}

    public Long getId() { return id; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public String getTipoSala() { return tipoSala; }
    public void setTipoSala(String tipoSala) { this.tipoSala = tipoSala; }
    public boolean isEncerrada() { return encerrada; }
    public void setEncerrada(boolean encerrada) { this.encerrada = encerrada; }
    public boolean isEmCartaz() { return emCartaz; }
    public void setEmCartaz(boolean emCartaz) { this.emCartaz = emCartaz; }
    public String getAssentosOcupados() { return assentosOcupados; }
    public void setAssentosOcupados(String assentosOcupados) { this.assentosOcupados = assentosOcupados; }
    public Filme getFilme() { return filme; }
    public void setFilme(Filme filme) { this.filme = filme; }
}
