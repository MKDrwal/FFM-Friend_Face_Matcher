package dev.mateuszkowalczyk.ffm.data.database;

import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(long id);

    void save(T t);
    void update(T t);
    void delete (T t);

}
