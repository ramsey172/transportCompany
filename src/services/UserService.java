package services;

import entity.User;
import storage.InMemoryUserStorage;
import storage.UserStorage;

import java.util.List;
import java.util.Optional;

public class UserService {
    UserStorage userStorage = new InMemoryUserStorage();

    public Optional<User> getUserByCredentials(String nickname, String password) {
        return userStorage
                .getAll()
                .stream()
                .filter(user -> user.getNickname().equals(nickname) && user.getPassword().equals(password))
                .findFirst();
    }



    public void create(User user) {
        userStorage.save(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }


}
