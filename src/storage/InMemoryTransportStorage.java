package storage;

import entity.Transport;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransportStorage implements TransportStorage {
    private long ids = 1;
    private List<Transport> transportsList = new ArrayList<>();


    @Override
    public void save(Transport transport) {
        transport.setId(ids++);
        transportsList.add(transport);
    }

    @Override
    public void removeById(long id) {
        transportsList = transportsList.stream().filter(transport -> transport.getId() != id).toList();
    }

    @Override
    public List<Transport> getAll() {
        return new ArrayList<>(transportsList);
    }
}
