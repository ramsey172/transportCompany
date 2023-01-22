package entity;

import entity.enums.TransportType;

public class Transport extends AbstractEntity {
    private final int speed;
    private final int peopleCapacity;
    private final double cargoCapacity;
    private final TransportType transportType;
    private final double pricePerKm;

    public Transport(String name, int speed, int peopleCapacity, double cargoCapacity, TransportType transportType, double pricePerKm) {
        this.name = name;
        this.speed = speed;
        this.peopleCapacity = peopleCapacity;
        this.cargoCapacity = cargoCapacity;
        this.transportType = transportType;
        this.pricePerKm = pricePerKm;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPeopleCapacity() {
        return peopleCapacity;
    }

    public double getCargoCapacity() {
        return cargoCapacity;
    }

    public String asString() {
        return toString();
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id='" + getId() +
                ", name='" + name +
                ", speed=" + speed +
                ", peopleCapacity=" + peopleCapacity +
                ", cargoCapacity=" + cargoCapacity +
                ", transportType=" + transportType +
                ", pricePerKm=" + pricePerKm + '\'' +
                '}';
    }
}
