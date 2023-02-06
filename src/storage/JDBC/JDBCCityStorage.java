package storage.JDBC;

import entity.City;
import storage.AbstractStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCityStorage extends AbstractStorage<City> {
    private static final String SELECT_SQL = "select * from cities";
    private static final String INSERT_SQL = "insert into cities values (default, ?, ?, ?, ?, ?)";
    private static final String GET_BY_ID_SQL = "select * from cities where id=?";
    private static final String DELETE_SQL = "delete from cities where id = ?";


    @Override
    public void save(City city) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1,
                    city.getName());
            preparedStatement.setDouble(2, city.getLatitude());
            preparedStatement.setDouble(3, city.getLongitude());
            preparedStatement.setBoolean(4, city.isHasSeaPort());
            preparedStatement.setBoolean(5, city.isHasSeaPort());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<City> getById(long id) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long city_id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                double lat = resultSet.getDouble(3);
                double lon = resultSet.getDouble(4);
                boolean isHasAirport = resultSet.getBoolean(5);
                boolean isHasSeaPort = resultSet.getBoolean(6);
                City city = new City(name, lat, lon, isHasAirport, isHasSeaPort);
                city.setId(city_id);
                return Optional.of(city);

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
    public List<City> getAll() {
        List<City> cities = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_SQL);
            while (resultSet.next()) {
                long cityId = resultSet.getLong(1);
                String name = resultSet.getString(2);
                double lat = resultSet.getDouble(3);
                double lon = resultSet.getDouble(4);
                boolean isHasAirport = resultSet.getBoolean(5);
                boolean isHasSeaPort = resultSet.getBoolean(6);
                City city = new City(name, lat, lon, isHasAirport, isHasSeaPort);
                city.setId(cityId);
                cities.add(city);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }
}
