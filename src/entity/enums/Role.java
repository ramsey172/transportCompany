package entity.enums;

public enum Role{
    ADMIN(1),
    USER(2);

    private final long id;

    Role(long id){
        this.id = id;
    }

    public long getId(){
        return this.id;
    }
}
