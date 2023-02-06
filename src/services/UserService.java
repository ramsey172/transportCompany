package services;

import entity.User;
import storage.JDBC.JDBCUserStorage;

import java.util.List;
import java.util.Optional;

public class UserService {
    JDBCUserStorage userStorage = new JDBCUserStorage();

    public Optional<User> getUserByCredentials(String nickname, String password) {
        return userStorage.getByCredentials(nickname, password);
    }

    public void create(User user) {
        userStorage.save(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }


}
