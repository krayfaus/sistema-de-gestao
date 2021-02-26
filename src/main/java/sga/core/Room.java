package sga.core;

import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Esse record representa uma sala (de curso/café) cadastrada no sistema.
 * As salas ficam armazenadas em coleções diferentes do banco de dados.
 *
 * Possuindo:
 * @param id          código de identificação.
 * @param name        nome da sala.
 * @param capacity    capacidade máxima de participantes.
 * @param member_ids  códigos dos participantes cadastrados por etapa.
 **/
public record Room(String id,
                   String name,
                   Integer capacity,
                   ArrayList<ArrayList<String>> member_ids) {

    public Room(String id, String title, Integer capacity) {
        this(id, title, capacity, new ArrayList<>());
    }

    public void addMember(Integer stage, Member member) {
        ArrayList<String> stage_ids = member_ids.get(stage);
        if (stage_ids == null) {
            member_ids.add(stage, new ArrayList<>());
            stage_ids = member_ids.get(stage);
        }
        stage_ids.add(member.id());
    }

    public void removeMember(Integer stage, Member member) {
        ArrayList<String> stage_ids = member_ids.get(stage);
        if (stage_ids != null) {
            stage_ids.remove(member.id());
        }
    }

    public Integer memberCount(Integer stage) {
        ArrayList<String> stage_ids = member_ids.get(stage);
        if (stage_ids == null) {
            return 0;
        }
        return member_ids.size();
    }

    public String toJson() {
        final Gson gson = new Gson();
        return gson.toJson(this, Room.class);
    }
}