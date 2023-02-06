package console;

import console.helpers.ConsoleMenuHelper;
import entity.User;
import entity.enums.Role;
import services.CargoTransportationService;
import services.CityManageService;
import services.TransportManageService;
import services.UserService;

import java.util.List;

import static console.util.ConsoleReader.readInt;
import static console.util.ConsoleReader.readString;
import static console.util.ConsoleWriter.write;

public class ConsoleApplication {
    public ConsoleSession consoleSession = new ConsoleSession();
    private final UserService userService = new UserService();
    private final CityManageService cityManageService = new CityManageService();
    private final TransportManageService transportManageService = new TransportManageService();
    private final CargoTransportationService cargoTransportationService = new CargoTransportationService();

    public void run() {
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
                        return;
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
}
