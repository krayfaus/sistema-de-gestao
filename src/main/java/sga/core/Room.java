package sga.core;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
                   List<List<String>> member_ids) {

    public Room(String id, String title, Integer capacity) {
        this(id, title, capacity, new ArrayList<>());
        member_ids.add(new ArrayList<>()); // Stage 1
        member_ids.add(new ArrayList<>()); // Stage 2
    }

    public void addMember(Integer stage, String member_id) {
        List<String> stage_ids = member_ids.get(stage);
        if (stage_ids == null) {
            member_ids.add(stage, new ArrayList<>());
            stage_ids = member_ids.get(stage);
        }
        stage_ids.add(member_id);
    }

    public void removeMember(Integer stage, Member member) {
        List<String> stage_ids = member_ids.get(stage);
        if (stage_ids != null) {
            stage_ids.remove(member.id());
        }
    }

    public Integer memberCount(Integer stage) {
        List<String> stage_ids = member_ids.get(stage);
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