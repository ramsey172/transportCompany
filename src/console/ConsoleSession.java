package console;

import entity.User;

import java.util.Optional;

public class ConsoleSession {
    private User currentUser = null;

    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
