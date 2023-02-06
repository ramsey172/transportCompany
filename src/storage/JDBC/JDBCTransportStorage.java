package storage.JDBC;

import entity.Transport;
import entity.enums.TransportType;
import storage.AbstractStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCTransportStorage extends AbstractStorage<Transport> {
    private static final String SELECT_SQL = "select * from transports";
    private static final String INSERT_SQL = "insert into transports values (default, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_ID_SQL = "select * from transports where id = ?";
    private static final String DELETE_SQL = "delete from transports where id = ?";


    @Override
    public void save(Transport transport) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1,
                    transport.getName());
            preparedStatement.setInt(2, transport.getSpeed());
            preparedStatement.setInt(3, transport.getPeopleCapacity());
            preparedStatement.setDouble(4, transport.getCargoCapacity());
            preparedStatement.setLong(5, transport.getTransportType().getId());
            preparedStatement.setDouble(6, transport.getPricePerKm());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Transport> getById(long id) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getTransportFromSqlRow(resultSet);
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
    public List<Transport> getAll() {
        List<Transport> transports = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_SQL);
            while (resultSet.next()) {
                getTransportFromSqlRow(resultSet).ifPresent(transports::add);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transports;
    }

    private Optional<Transport> getTransportFromSqlRow(ResultSet resultSet) throws SQLException{
        long transportId = resultSet.getLong(1);
        String name = resultSet.getString(2);
        int speed = resultSet.getInt(3);
        int peopleCapacity = resultSet.getInt(4);
        double cargoCapacity = resultSet.getDouble(5);
        int transportTypeId = resultSet.getInt(6);
        double pricePerKm = resultSet.getDouble(7);
        TransportType transportType =  TransportType.values()[--transportTypeId];
        Transport transport = new Transport(name, speed, peopleCapacity, cargoCapacity, transportType, pricePerKm);
        transport.setId(transportId);
        return Optional.of(transport);
    }
}
