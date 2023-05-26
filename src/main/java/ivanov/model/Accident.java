package ivanov.model;

import ivanov.dao.IdentityInterface;

import java.util.ArrayList;
import java.util.List;

public class Accident implements IdentityInterface<Long> {
    private List<Car> cars = new ArrayList<>();
    private Long identity;

    public List<Car> getCars() {
        return cars;
    }

    public Accident() {
        cars = new ArrayList<>();
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Accident{" +
                "cars=" + cars +
                ", identity=" + identity +
                '}';
    }

    @Override
    public Long getIdentity() {
        return identity;
    }

    @Override
    public void setIdentity(Long identity) {
        this.identity = identity;
    }

}
