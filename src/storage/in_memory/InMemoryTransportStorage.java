package storage.in_memory;

import entity.Transport;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryTransportStorage extends AbstractStorage<Transport> {
    private long ids = 1;
    private List<Transport> transportsList = new ArrayList<>();


    @Override
    public void save(Transport transport) {
        transport.setId(ids++);
        transportsList.add(transport);
    }

    @Override
    public Optional<Transport> getById(long id) {
        return transportsList.stream().filter(transport -> transport.getId() == id).findFirst();
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
