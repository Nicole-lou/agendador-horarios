package com.projeto.agendador_horarios.config;

import org.springframework.security.core.context.SecurityContextHolder;

public final class UsuarioLogado {

    private UsuarioLogado() {
    }

    // O FirebaseAuthFilter guarda o UID como "nome" do usuário autenticado
    public static String uid() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}