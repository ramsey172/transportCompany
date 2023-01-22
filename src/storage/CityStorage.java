package storage;

import entity.City;

import java.util.List;
import java.util.Optional;

public interface CityStorage {
    void save(City city);

    Optional<City> getById(long id);

    void removeById(long id);

    List<City> getAll();
}
