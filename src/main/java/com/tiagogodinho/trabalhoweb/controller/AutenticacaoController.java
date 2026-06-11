package com.tiagogodinho.trabalhoweb.controller;

import com.tiagogodinho.trabalhoweb.config.TokenService;
import com.tiagogodinho.trabalhoweb.dto.AutenticacaoDTO;
import com.tiagogodinho.trabalhoweb.dto.LoginResponseDTO;
import com.tiagogodinho.trabalhoweb.dto.UsuarioDTO;
import com.tiagogodinho.trabalhoweb.model.Usuario;
import com.tiagogodinho.trabalhoweb.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller que criamos para cuidar do login e cadastro. 
// Deixamos essas rotas abertas na configuração de segurança para o pessoal conseguir entrar.
@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    // Rota para fazer o login. Aqui a gente recebe o login e senha enviados pelo front e devolve o JWT.
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO data) {
        // Pede pro Spring Security validar se o usuário e senha estão certos
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Se deu tudo certo e a senha bater, a gente gera o token pra esse usuário
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    // Rota pra cadastrar um usuário novo no nosso banco
    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid UsuarioDTO data) {
        // Primeiro a gente confere se já não tem alguém com esse mesmo login pra não dar erro
        if (this.usuarioRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        // Encriptando a senha antes de salvar, como vimos em sala, por boas práticas de segurança
        String senhaCriptografada = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(data.login(), senhaCriptografada, data.funcao());

        // Salva o usuário lá no Postgres
        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}
