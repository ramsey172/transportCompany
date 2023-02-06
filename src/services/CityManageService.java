package services;

import entity.City;
import storage.JDBC.JDBCCityStorage;
import storage.AbstractStorage;

import java.util.List;
import java.util.Optional;

public class CityManageService {
    AbstractStorage<City> cityStorage = new JDBCCityStorage();

    public void add(City city) {
        cityStorage.save(city);
    }

    public void removeById(long id) {
        cityStorage.removeById(id);
    }

    public List<City> getAll() {
        return cityStorage.getAll();
    }

    public Optional<City> getById(long id) {
        return cityStorage.getById(id);
    }


}
