package entity;

public class CargoTransportation extends AbstractEntity {
    private final City departure;
    private final City destination;
    private final Transport transport;
    private final User user;
    private final double price;
    private final double routeLength;
    private final double routeTime;

    public CargoTransportation(City departure, City destination, Transport transport, User user, double price, double routeLength, double routeTime) {
        this.departure = departure;
        this.destination = destination;
        this.transport = transport;
        this.user = user;
        this.price = price;
        this.routeLength = routeLength;
        this.routeTime = routeTime;
    }

    public City getDeparture() {
        return departure;
    }

    public City getDestination() {
        return destination;
    }

    public Transport getTransport() {
        return transport;
    }

    public User getUser() {
        return user;
    }

    public double getRouteLength() {
        return routeLength;
    }

    public String asString() {
        return toString();
    }

    public double getPrice() {
        return price;
    }

    public double getRouteTime() {
        return routeTime;
    }

    @Override
    public String toString() {
        return "CargoTransportation{" +
                "departure=" + departure +
                ", destination=" + destination +
                ", transport=" + transport +
                ", price=" + price +
                ", routeLength=" + routeLength +
                ", routeTime=" + routeTime +
                ", user=" + user +
                '}';
    }
}
