package com.tiagogodinho.trabalhoweb.dto;

import com.tiagogodinho.trabalhoweb.model.UsuarioFuncao;

public record UsuarioDTO(String login, String senha, UsuarioFuncao funcao) {
}
