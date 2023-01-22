package console.helpers;

import entity.CargoTransportation;
import entity.City;
import entity.Transport;
import entity.User;
import entity.enums.TransportType;
import services.CargoTransportationService;
import services.CityManageService;
import services.TransportManageService;

import java.util.List;
import java.util.Optional;

import static console.util.ConsoleReader.*;
import static console.util.ConsoleWriter.*;


public class ConsoleMenuHelper {
    public static void citiesMenu(CityManageService cityManageService, User currentUser) {
        write("1. View");
        if (currentUser.isAdmin()) {
            write("2. Edit");
        }
        write(String.valueOf(currentUser.isAdmin()));
        int i = readInt();
        switch (i) {
            case 1 -> {
                List<City> citiesList = cityManageService.getAll();
                citiesList.forEach(city -> write(city.asString()));
            }
            case 2 -> {
                if (currentUser.isAdmin()) {
                    write("1. Add, 2. Remove by id");
                    int i2 = readInt();
                    switch (i2) {
                        case 1 -> {
                            write("Write name");
                            String name = readString();
                            write("Enter latitude");
                            double lat = readDouble();
                            write("Enter longitude");
                            double lon = readDouble();
                            write("Has airport? (y/n)");
                            String airportAnswer = readString();
                            write("Has seaport? (y/n)");
                            String seaportAnswer = readString();
                            City city = new City(name, lat, lon, airportAnswer.equals("y"), seaportAnswer.equals("y"));
                            cityManageService.add(city);
                        }
                        case 2 -> {
                            write("Enter city id");
                            int cityId = readInt();
                            cityManageService.removeById(cityId);
                        }
                    }
                }
            }
        }
    }

    public static void transportsMenu(TransportManageService transportManageService, User currentUser) {
        write("1. View");
        if (currentUser.isAdmin()) {
            write("2. Edit");
        }
        int i = readInt();
        switch (i) {
            case 1 -> {
                List<Transport> transportList = transportManageService.getAll();
                transportList.forEach(transport -> write(transport.asString()));
            }
            case 2 -> {
                if (currentUser.isAdmin()) {
                    write("1. Add, 2. Remove by id");
                    int i2 = readInt();
                    switch (i2) {
                        case 1 -> {
                            write("Write name");
                            String name = readString();
                            write("Enter speed");
                            int speed = readInt();
                            write("Enter people capacity");
                            int peopleCapacity = readInt();
                            write("Enter cargo capacity");
                            double cargoCapacity = readDouble();
                            write("Enter transport type (1. Ground, 2. Air, 3. Sea)");
                            int transportTypeChoice = readInt();
                            TransportType transportType;
                            switch (transportTypeChoice) {
                                case 1 -> transportType = TransportType.GROUND;
                                case 2 -> transportType = TransportType.AIR;
                                case 3 -> transportType = TransportType.SEA;
                                default -> transportType = TransportType.GROUND;
                            }
                            write("Enter price per km");
                            double pricePerKm = readDouble();
                            Transport transport = new Transport(name, speed, peopleCapacity, cargoCapacity, transportType, pricePerKm);
                            transportManageService.add(transport);
                        }
                        case 2 -> {
                            write("Enter transport id");
                            int transportId = readInt();
                            transportManageService.removeById(transportId);
                        }
                    }
                }
            }
        }
    }

    public static void cargoTransportationMenu(CargoTransportationService cargoTransportationService, CityManageService cityManageService, TransportManageService transportManageService, User currentUser) {
        write("1. Get cheapest and fastest types of transportations");
        if (currentUser.isAdmin()) {
            write("2. View report");
        }
        int i = readInt();
        switch (i) {
            case 1 -> {
                if (currentUser.isAdmin()) {
                    write("Enter city id of departure");
                    int cityDepartureId = readInt();
                    Optional<City> optionalCityDeparture = cityManageService.getById(cityDepartureId);
                    if (optionalCityDeparture.isEmpty()) {
                        write("City of departure not found");
                        break;
                    }
                    write("Enter city id of destination");
                    int cityDestinationId = readInt();
                    Optional<City> optionalCityDestination = cityManageService.getById(cityDestinationId);
                    if (optionalCityDestination.isEmpty()) {
                        write("City of destination not found");
                        break;
                    }
                    write("Enter people number");
                    int peopleNum = readInt();
                    write("Enter cargo weight");
                    double cargoWeight = readDouble();
                    cargoTransportationService.setSuitableCargoTransportationsByParams(transportManageService.getAll(), optionalCityDeparture.get(), optionalCityDestination.get(), peopleNum, cargoWeight, currentUser);
                    if (cargoTransportationService.getCheapest().isPresent()) {
                        write("The cheapest cargo transportation - " + cargoTransportationService.getCheapest().get().asString());
                    }
                    if (cargoTransportationService.getFastest().isPresent()) {
                        write("The fastest cargo transportation - " + cargoTransportationService.getFastest().get().asString());
                    }
                }
            }
            case 2 -> {
                if (currentUser.isAdmin()) {
                    List<CargoTransportation> cargoTransportations = cargoTransportationService.getAll();
                    cargoTransportations.forEach(transport -> write(transport.asString()));
                }
            }
        }
    }


}
