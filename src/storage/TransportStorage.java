package storage;

import entity.Transport;

import java.util.List;

public interface TransportStorage {
    void save(Transport transport);

    void removeById(long id);

    List<Transport> getAll();
}
