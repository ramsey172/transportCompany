package storage.in_memory;

import entity.CargoTransportation;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCargoTransportationStorage extends AbstractStorage<CargoTransportation> {
    private long ids = 1;
    private List<CargoTransportation> cargoTransportationsList = new ArrayList<>();


    @Override
    public void save(CargoTransportation cargoTransportation) {
        cargoTransportation.setId(ids++);
        cargoTransportationsList.add(cargoTransportation);
    }

    public Optional<CargoTransportation> getById(long id) {
        return cargoTransportationsList.stream().filter(cargoTransportation -> cargoTransportation.getId() == id).findFirst();
    }

    @Override
    public void removeById(long id) {
        cargoTransportationsList = cargoTransportationsList.stream().filter(cargoTransportation -> cargoTransportation.getId() != id).toList();
    }

    @Override
    public List<CargoTransportation> getAll() {
        return new ArrayList<>(cargoTransportationsList);
    }
}
