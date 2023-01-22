package entity;

public abstract class AbstractEntity {
    private long id;
    protected String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id){
        this.id = id;
    }
}
