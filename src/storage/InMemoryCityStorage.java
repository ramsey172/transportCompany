package storage;

import entity.City;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCityStorage implements CityStorage{
    private long ids = 1;
    private List<City> citiesList = new ArrayList<>();


    @Override
    public void save(City city) {
        city.setId(ids++);
        citiesList.add(city);
    }

    @Override
    public Optional<City> getById(long id) {
        return citiesList.stream().filter(city -> city.getId() == id).findFirst();
    }

    @Override
    public void removeById(long id) {
        citiesList = citiesList.stream().filter(city -> city.getId() != id).toList();
    }

    @Override
    public List<City> getAll() {
        return new ArrayList<>(citiesList);
    }
}
