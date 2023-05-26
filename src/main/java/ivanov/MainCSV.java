package ivanov;

import ivanov.dao.CarDaoCSV;
import ivanov.dao.AccidentDaoCSV;
import ivanov.model.Car;
import ivanov.model.Accident;
import ivanov.reflection.ApplicationContext;
import ivanov.reflection.DependencyInjection;

public class MainCSV {

    @DependencyInjection
    private static AccidentDaoCSV ac;

    @DependencyInjection
    private static CarDaoCSV cr;

    static {
        try {
            ApplicationContext.injectDependencies();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        car1.setIdentity(1L);
        car2.setIdentity(2L);
        car3.setIdentity(3L);
        Accident accident = new Accident();
        accident.setIdentity(1L);
        car1.setName("car1");
        car2.setName("car2");
        car3.setName("car3");
        car1.setSpeed(100);
        car2.setSpeed(50);
        car3.setSpeed(10);
        accident.getCars().add(car1);
        accident.getCars().add(car2);
        accident.getCars().add(car3);
        cr.put(car1);
        cr.put(car2);
        cr.put(car3);
        ac.put(accident);
        System.out.println(cr.get(1L));
        System.out.println(cr.get(2L));
        System.out.println(cr.get(1L));
        System.out.println(ac.get(1L));
    }
}
