package br.cinenoir.controller;

import br.cinenoir.model.Sessao;
import br.cinenoir.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sessoes")
@CrossOrigin(origins = "*")
public class SessaoController {

    @Autowired
    private SessaoRepository repository;

    @GetMapping("/filme/{filmeId}")
    public List<Sessao> porFilme(@PathVariable Long filmeId) {
        return repository.findByFilmeId(filmeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> buscar(@PathVariable Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/assentos")
    public List<Integer> assentos(@PathVariable Long id) {
        return repository.findById(id).map(s -> {
            List<Integer> ocupados = new ArrayList<>();
            String raw = s.getAssentosOcupados();
            if (raw != null && !raw.isBlank()) {
                for (String n : raw.split(",")) {
                    ocupados.add(Integer.parseInt(n.trim()));
                }
            }
            return ocupados;
        }).orElse(new ArrayList<>());
    }


    @PatchMapping("/{id}/ocupar")
    public ResponseEntity<Sessao> ocupar(@PathVariable Long id, @RequestBody java.util.Map<String, Object> body) {
        return repository.findById(id).map(sessao -> {
            Object assentosObj = body.get("assentos");
            if (assentosObj instanceof java.util.List) {
                java.util.List<?> lista = (java.util.List<?>) assentosObj;
                String atual = sessao.getAssentosOcupados();
                java.util.Set<String> ocupados = new java.util.LinkedHashSet<>();
                if (atual != null && !atual.isBlank()) {
                    for (String s : atual.split(",")) ocupados.add(s.trim());
                }
                for (Object a : lista) ocupados.add(a.toString().trim());
                sessao.setAssentosOcupados(String.join(",", ocupados));
                return ResponseEntity.ok(repository.save(sessao));
            }
            return ResponseEntity.badRequest().<Sessao>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sessao criar(@RequestBody Sessao sessao) {
        return repository.save(sessao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
