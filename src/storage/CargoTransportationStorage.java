package storage;

import entity.CargoTransportation;

import java.util.List;

public interface CargoTransportationStorage {
    void save(CargoTransportation cargoTransportation);

    void removeById(long id);

    List<CargoTransportation> getAll();
}
