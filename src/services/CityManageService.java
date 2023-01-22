package services;

import entity.City;
import storage.CityStorage;
import storage.InMemoryCityStorage;

import java.util.List;
import java.util.Optional;

public class CityManageService {
    CityStorage cityStorage = new InMemoryCityStorage();

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
