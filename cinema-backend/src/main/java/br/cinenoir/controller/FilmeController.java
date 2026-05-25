package br.cinenoir.controller;

import br.cinenoir.model.Filme;
import br.cinenoir.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/filmes")
@CrossOrigin(origins = "*")
public class FilmeController {

    @Autowired
    private FilmeRepository repository;

    @GetMapping
    public List<Filme> listar() {
        return repository.findAll();
    }

    @GetMapping("/cartaz")
    public List<Filme> emCartaz() {
        return repository.findByEmCartazTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscar(@PathVariable Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Filme criar(@RequestBody Filme filme) {
        return repository.save(filme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizar(@PathVariable Long id, @RequestBody Filme dados) {
        return repository.findById(id).map(f -> {
            f.setNome(dados.getNome());
            f.setDuracao(dados.getDuracao());
            f.setSinopse(dados.getSinopse());
            f.setValor(dados.getValor());
            f.setGenero(dados.getGenero());
            f.setPosterUrl(dados.getPosterUrl());
            f.setEmCartaz(dados.isEmCartaz());
            f.setBackdropUrl(dados.getBackdropUrl());
            f.setDiretor(dados.getDiretor());
            f.setElenco(dados.getElenco());
            f.setClassificacao(dados.getClassificacao());
            return ResponseEntity.ok(repository.save(f));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
