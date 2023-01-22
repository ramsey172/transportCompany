package console;

import console.helpers.ConsoleMenuHelper;
import entity.City;
import entity.Transport;
import entity.enums.Role;
import entity.User;
import entity.enums.TransportType;
import services.CargoTransportationService;
import services.CityManageService;
import services.TransportManageService;
import services.UserService;

import java.util.List;

import static console.util.ConsoleReader.*;
import static console.util.ConsoleWriter.*;

public class ConsoleApplication {
    public ConsoleSession consoleSession = new ConsoleSession();
    private final UserService userService = new UserService();
    private final CityManageService cityManageService = new CityManageService();
    private final TransportManageService transportManageService = new TransportManageService();
    private final CargoTransportationService cargoTransportationService = new CargoTransportationService();

    public void run() {
        setDefaults();
        while (true) {
            if (consoleSession.getCurrentUser().isEmpty()) {
                write("Hello guest!");
                write("1. Registration, 2. Authorization, 3. Exit");
                int i = readInt();
                switch (i) {
                    case 1 -> registration();
                    case 2 -> {
                        if (!isSuccessAuthorize()) {
                            write("Wrong nickname or password");
                        }
                    }
                    case 3 -> {
                        System.exit(0);
                    }
                }
            } else {
                write("Hello " + consoleSession.getCurrentUser().get().getNickname() + "!");
                write("1. View users, 2. Cities, 3. Transports, 4. Cargo transportation, 5. Logout, 6. Exit");
                int i = readInt();
                switch (i) {
                    case 1:
                        List<User> usersList = userService.getAll();
                        usersList.forEach(user -> write(user.asString()));
                        break;
                    case 2:
                        ConsoleMenuHelper.citiesMenu(cityManageService, consoleSession.getCurrentUser().get());
                        break;
                    case 3:
                        ConsoleMenuHelper.transportsMenu(transportManageService, consoleSession.getCurrentUser().get());
                        break;
                    case 4:
                        ConsoleMenuHelper.cargoTransportationMenu(cargoTransportationService, cityManageService, transportManageService, consoleSession.getCurrentUser().get());
                        break;
                    case 5:
                        consoleSession.setCurrentUser(null);
                    case 6:
                        System.exit(0);
                }

            }
        }
    }

    private void registration() {
        write("Enter nickname");
        String nickname = readString();
        write("Enter password");
        String password = readString();
        User user = new User(nickname, password);
        write("Enter your role: 1 - Admin, 2 - User");
        int i = readInt();
        switch (i) {
            case 1 -> user.setRole(Role.ADMIN);
            case 2 -> user.setRole(Role.USER);
        }
        userService.create(user);
        consoleSession.setCurrentUser(user);
    }

    private boolean isSuccessAuthorize() {
        write("Enter nickname");
        String nickname = readString();
        write("Enter password");
        String password = readString();
        if (userService.getUserByCredentials(nickname, password).isPresent()) {
            consoleSession.setCurrentUser(userService.getUserByCredentials(nickname, password).get());
            return true;
        }
        return false;
    }
    private void setDefaultCities() {
        City city1 = new City("Prague", 50.09, 14.42, true, false);
        City city2 = new City("Vein", 48.21, 16.37, true, false);
        City city3 = new City("Hamburg", 53.57, 10.01, true, true);
        City city4 = new City("Chausy", 53.48, 30.58, false, false);
        City city5 = new City("Tallinn", 59.26, 24.45, true, true);
        cityManageService.add(city1);
        cityManageService.add(city2);
        cityManageService.add(city3);
        cityManageService.add(city4);
        cityManageService.add(city5);
    }

    private void setDefaultTransports() {
        Transport transport1 = new Transport("Donkey", 5, 1, 50, TransportType.GROUND, 3);
        Transport transport2 = new Transport("Mondeo 2002", 90, 4, 300, TransportType.GROUND, 6);
        Transport transport3 = new Transport("Ship", 30, 400, 500000, TransportType.SEA, 10);
        Transport transport4 = new Transport("Aircraft", 700, 100, 10000, TransportType.AIR, 100);
        transportManageService.add(transport1);
        transportManageService.add(transport2);
        transportManageService.add(transport3);
        transportManageService.add(transport4);
    }

    private void setDefaults() {
        setDefaultCities();
        setDefaultTransports();
    }
}
