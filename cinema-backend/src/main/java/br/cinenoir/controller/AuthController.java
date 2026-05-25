package br.cinenoir.controller;
import br.cinenoir.model.Usuario;
import br.cinenoir.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    @GetMapping("/usuario/{user}")
    public ResponseEntity<?> buscarPorUser(@PathVariable String user) {
        return repository.findByUser(user)
            .map(u -> ResponseEntity.ok((Object) u))
            .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/alterar-senha")
    public ResponseEntity<?> alterarSenha(@RequestBody Map<String, String> body) {
        String user = body.get("user");
        String senhaAtual = body.get("senhaAtual");
        String novaSenha = body.get("novaSenha");
        return repository.findByUser(user)
            .map(u -> {
                if (!u.getSenha().equals(senhaAtual))
                    return ResponseEntity.status(401).body((Object) "Senha atual incorreta");
                u.setSenha(novaSenha);
                return ResponseEntity.ok((Object) repository.save(u));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciSenha(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        return repository.findByEmail(email)
            .map(u -> {
                String token = String.valueOf((int)(Math.random() * 900000) + 100000);
                u.setResetToken(token);
                u.setResetExpiry(String.valueOf(System.currentTimeMillis() + 3600000));
                repository.save(u);
                return ResponseEntity.ok((Object) Map.of("token", token, "user", u.getUser()));
            })
            .orElse(ResponseEntity.status(404).body("E-mail não encontrado"));
    }
    @PostMapping("/reset-senha")
    public ResponseEntity<?> resetSenha(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String novaSenha = body.get("novaSenha");
        return repository.findByResetToken(token)
            .map(u -> {
                long expiry = Long.parseLong(u.getResetExpiry() != null ? u.getResetExpiry() : "0");
                if (System.currentTimeMillis() > expiry)
                    return ResponseEntity.status(400).body((Object) "Token expirado");
                u.setSenha(novaSenha);
                u.setResetToken(null);
                u.setResetExpiry(null);
                return ResponseEntity.ok((Object) repository.save(u));
            })
            .orElse(ResponseEntity.status(404).body("Token inválido"));
    }
}
