package entity;

public class City extends AbstractEntity{
    private final double latitude;
    private final double longitude;
    private final boolean isHasAirport;
    private final boolean isHasSeaPort;

    public City(String name, double latitude, double longitude, boolean isHasAirport, boolean isHasSeaPort) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isHasAirport = isHasAirport;
        this.isHasSeaPort = isHasSeaPort;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isHasAirport() {
        return isHasAirport;
    }

    public boolean isHasSeaPort() {
        return isHasSeaPort;
    }

    public String asString(){
        return toString();
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + getId() +
                ", name='" + name +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", isHasAirport=" + isHasAirport +
                ", isHasSeaPort=" + isHasSeaPort + '\'' +
                '}';
    }
}
