package services;

import entity.Transport;
import storage.InMemoryTransportStorage;
import storage.TransportStorage;

import java.util.List;

public class TransportManageService {
    TransportStorage transportStorage = new InMemoryTransportStorage();

    public void add(Transport transport) {
        transportStorage.save(transport);
    }

    public void removeById(long id) {
        transportStorage.removeById(id);
    }

    public List<Transport> getAll() {
        return transportStorage.getAll();
    }


}
