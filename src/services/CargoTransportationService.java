package services;

import entity.CargoTransportation;
import entity.City;
import entity.Transport;
import entity.User;
import entity.enums.TransportType;
import storage.CargoTransportationStorage;
import storage.InMemoryCargoTransportationStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CargoTransportationService {

    private static final double EARTH_RADIUS = 6372.795;

    private List<CargoTransportation> suitableCargoTransportations = new ArrayList<>();
    private final CargoTransportationStorage cargoTransportationStorage = new InMemoryCargoTransportationStorage();

    public void setSuitableCargoTransportationsByParams(List<Transport> transportList, City city1, City city2, int peopleCapacity, double cargoWeight, User user) {
        List<TransportType> transportTypes = new ArrayList<>();
        transportTypes.add(TransportType.GROUND);
        if (city1.isHasAirport() && city2.isHasAirport()) {
            transportTypes.add(TransportType.AIR);
        }
        if (city1.isHasSeaPort() && city2.isHasSeaPort()) {
            transportTypes.add(TransportType.SEA);
        }
        transportList = transportList.stream()
                .filter(transport -> transportTypes.contains(transport.getTransportType()) && transport.getPeopleCapacity() >= peopleCapacity && transport.getCargoCapacity() >= cargoWeight)
                .toList();
        if (!transportList.isEmpty()) {
            List<CargoTransportation> suitableCargoTransportations = new ArrayList<>();
            for (Transport transport : transportList) {
                double routeLength = calculateRouteLength(city1, city2);
                double routePrice = calculateRoutePrice(transport, routeLength);
                double routeTime = calculateRouteTime(transport, routeLength);
                CargoTransportation cargoTransportation = new CargoTransportation(city1, city2, transport, user, routePrice, routeLength, routeTime);
                suitableCargoTransportations.add(cargoTransportation);
                cargoTransportationStorage.save(cargoTransportation);
            }
            this.suitableCargoTransportations = suitableCargoTransportations;
        }
    }

    private double calculateRouteLength(City city1, City city2) {
        double lat1 = city1.getLatitude() * Math.PI / 180;
        double lon1 = city1.getLongitude() * Math.PI / 180;
        double lat2 = city2.getLatitude() * Math.PI / 180;
        double lon2 = city2.getLongitude() * Math.PI / 180;
        double cl1 = Math.cos(lat1);
        double cl2 = Math.cos(lat2);
        double sl1 = Math.sin(lat1);
        double sl2 = Math.sin(lat2);
        double delta = lon2 - lon1;
        double cdelta = Math.cos(delta);
        double sdelta = Math.sin(delta);
        double y = Math.sqrt(Math.pow(cl2 * sdelta, 2) + Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
        double x = sl1 * sl2 + cl1 * cl2 * cdelta;
        double ad = Math.atan2(y, x);
        return ad * EARTH_RADIUS;
    }

    private double calculateRoutePrice(Transport transport, double routeRange) {
        return transport.getPricePerKm() * routeRange;
    }

    private double calculateRouteTime(Transport transport, double routeLength) {
        return routeLength / transport.getSpeed();
    }

    public Optional<CargoTransportation> getFastest() {
        if (!suitableCargoTransportations.isEmpty()) {
            return suitableCargoTransportations.stream().min(Comparator.comparingDouble(CargoTransportation::getRouteTime));
        }
        return Optional.empty();
    }

    public Optional<CargoTransportation> getCheapest() {
        if (!suitableCargoTransportations.isEmpty()) {
            return suitableCargoTransportations.stream().min(Comparator.comparingDouble(CargoTransportation::getPrice));
        }
        return Optional.empty();
    }

    public List<CargoTransportation> getAll() {
        return cargoTransportationStorage.getAll();
    }
}
