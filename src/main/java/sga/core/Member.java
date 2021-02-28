package sga.core;

import com.google.gson.Gson;
import sga.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Esse record representa uma pessoa cadastrada no sistema.
 *
 * Possuindo:
 * @param id       código de identificação.
 * @param name     prenome.
 * @param surname  sobrenome.
 * @param stages   [{ código de turma, código de café }].
 **/
public record Member(String id,
                     String name,
                     String surname,
                     List<Pair<String, String>> stages) {

    public Member(String id, String name, String surname) {
        this(id, name, surname, new ArrayList<>());
    }

    // Retorna o nome completo da pessoa.
    public String fullName() { return name + " " + surname; }

    // Converte o objeto a uma String JSON.
    public String toJson() {
        final Gson gson = new Gson();
        return gson.toJson(this, Member.class);
    }
}