package storage.JDBC;

import entity.CargoTransportation;
import entity.City;
import entity.Transport;
import entity.User;
import entity.enums.Role;
import entity.enums.TransportType;
import storage.AbstractStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCargoTransportationStorage extends AbstractStorage<CargoTransportation> {
    private static final String SELECT_SQL = "select c.*, departure.id  as departure_id, departure.name as departure_name, departure.lat as departure_lat, departure.long as departure_long, departure.is_has_airport as departure_is_has_airport, departure.is_has_sea_port as departure_is_has_sea_port, destination.id  as destination_id, destination.name as destination_name, destination.lat as destination_lat, destination.long as destination_long, destination.is_has_airport as destination_is_has_airport, destination.is_has_sea_port as destination_is_has_sea_port," +
            "            transport.id  as transport_id, transport.name as transport_name, transport.speed as transport_speed, transport.people_capacity as transport_people_capacity, transport.cargo_capacity as transport_cargo_capacity, transport_type.name as transport_type_name, transport.price_per_km as transport_price_per_km," +
            "            u.id  as user_id, u.nickname as user_nickname, u.password as user_password, role.name as user_role_name" +
            "            from cargo_transportations c" +
            "            left join cities departure on departure.id = c.departure_id" +
            "            left join cities destination on destination.id = c.destination_id" +
            "            left join transports transport on transport.id = c.transport_id" +
            "            left join transport_types transport_type on transport.transport_type_id = transport_type.id" +
            "            left join users u on u.id = c.user_id" +
            "            left join roles role on u.role_id = role.id";
    private static final String INSERT_SQL = "insert into cargo_transportations values (default, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_ID_SQL = "select c.*, departure.id  as departure_id, departure.name as departure_name, departure.lat as departure_lat, departure.long as departure_long, departure.is_has_airport as departure_is_has_airport, departure.is_has_sea_port as departure_is_has_sea_port, destination.id  as destination_id, destination.name as destination_name, destination.lat as destination_lat, destination.long as destination_long, destination.is_has_airport as destination_is_has_airport, destination.is_has_sea_port as destination_is_has_sea_port," +
            "            transport.id  as transport_id, transport.name as transport_name, transport.speed as transport_speed, transport.people_capacity as transport_people_capacity, transport.cargo_capacity as transport_cargo_capacity, transport_type.name as transport_type_name, transport.price_per_km as transport_price_per_km," +
            "            u.id  as user_id, u.nickname as user_nickname, u.password as user_password, role.name as user_role_name" +
            "            from cargo_transportations c" +
            "            left join cities departure on departure.id = c.departure_id" +
            "            left join cities destination on destination.id = c.destination_id" +
            "            left join transports transport on transport.id = c.transport_id" +
            "            left join transport_types transport_type on transport.transport_type_id = transport_type.id" +
            "            left join users u on u.id = c.user_id" +
            "            left join roles role on u.role_id = role.id"+
            "            where id = ? ";
    private static final String DELETE_SQL = "delete from cargo_transportations where id = ?";


    @Override
    public void save(CargoTransportation cargoTransportation) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setLong(1, cargoTransportation.getDeparture().getId());
            preparedStatement.setLong(2, cargoTransportation.getDestination().getId());
            preparedStatement.setLong(3, cargoTransportation.getTransport().getId());
            preparedStatement.setDouble(4, cargoTransportation.getPrice());
            preparedStatement.setDouble(5, cargoTransportation.getRouteLength());
            preparedStatement.setDouble(6, cargoTransportation.getRouteTime());
            preparedStatement.setLong(7, cargoTransportation.getUser().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CargoTransportation> getById(long id) {
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    GET_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getCargoTransportationFromSqlRow(resultSet);
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
    public List<CargoTransportation> getAll() {
        List<CargoTransportation> cargoTransportations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL,
                DB_USER, DB_USER_PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_SQL);
            while (resultSet.next()) {
                getCargoTransportationFromSqlRow(resultSet).ifPresent(cargoTransportations::add);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cargoTransportations;
    }

    private Optional<CargoTransportation> getCargoTransportationFromSqlRow(ResultSet resultSet) throws SQLException{
        long cargoTransportationId = resultSet.getLong(1);
        double price = resultSet.getDouble(5);
        double routeLength = resultSet.getDouble(6);
        double routeTime = resultSet.getDouble(7);
        //departure
        long departureId = resultSet.getLong(9);
        String departureName = resultSet.getString(10);
        double departureLat = resultSet.getDouble(11);
        double departureLon = resultSet.getDouble(12);
        boolean departureIsHasAirport = resultSet.getBoolean(13);
        boolean departureIsHasSeaPort = resultSet.getBoolean(14);
        City departure = new City(departureName, departureLat, departureLon, departureIsHasAirport, departureIsHasSeaPort);
        departure.setId(departureId);
        //destination
        long destinationId = resultSet.getLong(15);
        String destinationName = resultSet.getString(16);
        double destinationLat = resultSet.getDouble(17);
        double destinationLon = resultSet.getDouble(18);
        boolean destinationIsHasAirport = resultSet.getBoolean(19);
        boolean destinationIsHasSeaPort = resultSet.getBoolean(20);
        City destination = new City(destinationName, destinationLat, destinationLon, destinationIsHasAirport, destinationIsHasSeaPort);
        destination.setId(destinationId);
        //transport
        long transportId = resultSet.getLong(21);
        String transportName = resultSet.getString(22);
        int transportSpeed = resultSet.getInt(23);
        int transportPeopleCapacity = resultSet.getInt(24);
        double transportCargoCapacity = resultSet.getDouble(25);
        String transportTypeName = resultSet.getString(26);
        double transportPricePerKm = resultSet.getDouble(27);
        TransportType transportType =  TransportType.valueOf(transportTypeName);
        Transport transport = new Transport(transportName, transportSpeed, transportPeopleCapacity, transportCargoCapacity, transportType, transportPricePerKm);
        transport.setId(transportId);
        //user
        long userId = resultSet.getLong(28);
        String userNickname = resultSet.getString(29);
        String userPassword = resultSet.getString(30);
        Role role = Role.valueOf(resultSet.getString(31));
        User user = new User(userNickname, userPassword, role);
        user.setId(userId);

        CargoTransportation cargoTransportation = new CargoTransportation(departure, destination, transport, user, price, routeLength, routeTime);
        cargoTransportation.setId(cargoTransportationId);
        return Optional.of(cargoTransportation);
    }
}
