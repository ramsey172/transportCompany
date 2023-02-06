package storage;
import java.util.List;
import java.util.Optional;

public abstract class AbstractStorage<T> {

    protected static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    protected static final String DB_USER = "postgres";
    protected static final String DB_USER_PASSWORD = "1460";


    public abstract void save(T item);

    public abstract void removeById(long id);

    public abstract Optional<T> getById(long id);

    public abstract List<T> getAll();
}
