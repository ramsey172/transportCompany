package storage.JDBC;

import entity.User;
import entity.enums.Role;
import storage.AbstractStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserStorage extends AbstractStorage<User> {
    private static final String SELECT_SQL = "select u.*, r.name as role " +
            "from users u " +
            "left join roles r " +
            "on r.id = u.role_id ";
    private static final String INSERT_SQL = "insert into users values (default, ?, ?, ?)";
    private static final String GET_BY_ID_SQL = "select u.*, r.name as role " +
            "from users u " +
            "left join roles r on r.id = u.role_id" +
            "where id = ? ";
    private static final String DELETE_SQL = "delete from users where id = ?";
    private static final String GET_BY_CREDENTIALS_SQL = "select u.*, r.name as role " +
            "from users u join roles r " +
            "on r.id = u.role_id " +
            "where nickname = ? and password = ?";
    @Override
    public void save(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setLong(1, user.getRole().getId());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> getByCredentials(String nickname, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_CREDENTIALS_SQL);
            preparedStatement.setString(1, nickname);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getUserFromSqlRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getById(long id) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    GET_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getUserFromSqlRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void removeById(long id) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_SQL);
            while (resultSet.next()) {
                getUserFromSqlRow(resultSet).ifPresent(users::add);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private Optional<User> getUserFromSqlRow(ResultSet resultSet) throws SQLException{
        long userId = resultSet.getLong(1);
        String nickname = resultSet.getString(3);
        String password = resultSet.getString(4);
        Role role = Role.valueOf(resultSet.getString(5));
        User user = new User(nickname, password, role);
        user.setId(userId);
        return Optional.of(user);
    }
}
