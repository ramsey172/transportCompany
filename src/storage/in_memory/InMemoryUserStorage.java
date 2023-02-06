package storage.in_memory;

import entity.User;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserStorage extends AbstractStorage<User> {
    private long ids = 1;
    private List<User> usersList = new ArrayList<>();


    @Override
    public void save(User user) {
        user.setId(ids++);
        usersList.add(user);
    }

    @Override
    public Optional<User> getById(long id) {
        return usersList.stream().filter(user -> user.getId() == id).findFirst();
    }

    @Override
    public void removeById(long id) {
        usersList = usersList.stream().filter(user -> user.getId() != id).toList();
    }


    public Optional<User> getByCredentials(String nickname, String password) {
        return getAll()
                .stream()
                .filter(user -> user.getNickname().equals(nickname) && user.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(usersList);
    }
}
