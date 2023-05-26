package ivanov.dao;

import ivanov.model.Car;
import ivanov.model.Accident;
import ivanov.reflection.DependencyInjection;
import org.jetbrains.annotations.NotNull;
import ivanov.reflection.Component;
import ivanov.utils.Utils;

import java.io.IOException;
import java.util.*;


@Component
public class AccidentDaoCSV extends AbstractCSVFileDAO<Long, Accident> {

    @DependencyInjection
    private CarDaoCSV ac;

    public AccidentDaoCSV() throws IOException {
        super("AccidentTable");
    }

    @Override
    public void put(@NotNull Accident object) {
        var key = object.getIdentity();
        var fields = new Object[] {
                Utils.objectsToList(object.getCars())
        };

        try {
            this.putInCSVFile(key, fields);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Accident> get(Long key) {
        Optional<Object[]> fieldsInternal;
        try {
            fieldsInternal = this.getFromCSV(key);
        } catch (IOException e) {
            throw  new IllegalStateException(e);
        }
        
        if (fieldsInternal.isEmpty()) {
            return  Optional.empty();
        }


        var fields = fieldsInternal.get();
        List<Long> idCars = Utils.strToList((String) fields[1]);
        List<Car> cars = new ArrayList<>();
        for (Long id: idCars) {
            cars.add(ac.get(id).get());
        }
        Accident lane = new Accident();
        lane.setCars(cars);
        lane.setIdentity(key);
        return Optional.of(lane);
    }

}
