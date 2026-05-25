package br.cinenoir.controller;
import br.cinenoir.model.Critica;
import br.cinenoir.model.Filme;
import br.cinenoir.repository.CriticaRepository;
import br.cinenoir.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/criticas")
@CrossOrigin(origins = "*")
public class CriticaController {
    @Autowired
    private CriticaRepository criticaRepository;
    @Autowired
    private FilmeRepository filmeRepository;
    @GetMapping("/filme/{filmeId}")
    public List<Critica> listar(@PathVariable Long filmeId) {
        return criticaRepository.findByFilmeId(filmeId);
    }
    @PostMapping("/filme/{filmeId}")
    public ResponseEntity<?> criar(@PathVariable Long filmeId, @RequestBody Map<String, Object> body) {
        return filmeRepository.findById(filmeId).map(filme -> {
            Critica c = new Critica();
            c.setNome((String) body.get("nome"));
            c.setMensagem((String) body.get("mensagem"));
            c.setNota(((Number) body.get("nota")).doubleValue());
            c.setOrigem((String) body.getOrDefault("origem", "Usuário"));
            c.setFilme(filme);
            Critica salva = criticaRepository.save(c);
            // Atualiza nota média do filme
            List<Critica> todas = criticaRepository.findByFilmeId(filmeId);
            double media = todas.stream().mapToDouble(Critica::getNota).average().orElse(0);
            filme.setNota(Math.round(media * 10.0) / 10.0);
            filme.setQuantidadeCriticos(todas.size());
            filmeRepository.save(filme);
            return ResponseEntity.ok((Object) salva);
        }).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/usuario/{nome}")
    public List<Critica> listarPorUsuario(@PathVariable String nome) {
        return criticaRepository.findByNome(nome);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        criticaRepository.findById(id).ifPresent(c -> {
            Filme filme = c.getFilme();
            criticaRepository.deleteById(id);
            List<Critica> restantes = criticaRepository.findByFilmeId(filme.getId());
            double media = restantes.stream().mapToDouble(Critica::getNota).average().orElse(0);
            filme.setNota(Math.round(media * 10.0) / 10.0);
            filme.setQuantidadeCriticos(restantes.size());
            filmeRepository.save(filme);
        });
        return ResponseEntity.noContent().build();
    }
}
