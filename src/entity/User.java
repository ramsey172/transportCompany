package entity;

import entity.enums.Role;

public class User extends AbstractEntity {
    private Role role;

    private final String nickname;
    private final String password;

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String asString() {
        return toString();
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", role=" + role +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
