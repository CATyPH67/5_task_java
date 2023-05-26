package ivanov.model;

import java.util.List;

public class Road {
    private List<Accident> accidents;
    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Accident> getAccidents() {
        return accidents;
    }

    public void setAccidents(List<Accident> accidents) {
        this.accidents = accidents;
    }

    @Override
    public String toString() {
        return "Road{" +
                "accidents=" + accidents +
                ", cars=" + cars +
                '}';
    }

}
