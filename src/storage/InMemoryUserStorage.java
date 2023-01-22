package storage;

import entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserStorage implements UserStorage {
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

    @Override
    public List<User> getAll() {
        return new ArrayList<>(usersList);
    }
}
