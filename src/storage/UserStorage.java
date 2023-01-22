package storage;

import entity.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    void save(User user);

    Optional<User> getById(long id);

    void removeById(long id);

    List<User> getAll();
}
