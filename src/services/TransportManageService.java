package services;

import entity.Transport;
import storage.AbstractStorage;
import storage.JDBC.JDBCTransportStorage;

import java.util.List;

public class TransportManageService {
    AbstractStorage<Transport> transportStorage = new JDBCTransportStorage();

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
