package sga.core;

import java.util.List;
import java.util.Optional;

/**
 * Essa interface contém declaração
 *  dos metódos de persistencia de dados.
 *
 * Possuindo:
 * @param <T>     parâmetro de template para herança.
 **/
public interface Dao<T> {
    Optional<T> read(String id);

    List<T> readAll();

    void create(T t);

    void update(T t);

    void delete(T t);

    long count();
}