package br.cinenoir.controller;

import br.cinenoir.model.Usuario;
import br.cinenoir.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String user = body.get("user");
        String senha = body.get("senha");
        return repository.findByUser(user)
            .filter(u -> u.getSenha().equals(senha))
            .map(u -> ResponseEntity.ok((Object) u))
            .orElse(ResponseEntity.status(401).body("Usuário ou senha incorretos"));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody Usuario u) {
        if (repository.findByUser(u.getUser()).isPresent()) {
            return ResponseEntity.status(409).body("Nome de usuário já existe");
        }
        return ResponseEntity.ok(repository.save(u));
    }
}
