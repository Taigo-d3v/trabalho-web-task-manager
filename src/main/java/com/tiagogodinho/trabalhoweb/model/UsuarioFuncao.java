package com.tiagogodinho.trabalhoweb.model;

public enum UsuarioFuncao {
    ADMIN("admin"),
    USUARIO("usuario");

    private String funcao;

    UsuarioFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }
}
