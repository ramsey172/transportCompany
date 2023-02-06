package entity.enums;

public enum TransportType{
    GROUND(1),
    AIR(2),
    SEA(3);

    private final long id;

    TransportType(long id){
        this.id = id;
    }

    public long getId(){
        return this.id;
    }
}
