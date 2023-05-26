package ivanov;

import ivanov.controller.AccidentController;
import ivanov.controller.CarController;
import ivanov.dao.AccidentDao;
import ivanov.dao.CarDao;
import ivanov.model.Accident;
import ivanov.model.Car;
import ivanov.reflection.ApplicationContext;
import ivanov.reflection.DependencyInjection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainJDBC {


    @DependencyInjection
    private static AccidentController accidentController;

    @DependencyInjection
    private static CarController carController;


    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";

    @DependencyInjection
    private static CarDao carDao;

    @DependencyInjection
    private static AccidentDao accidentDao;

    static {
        try {
            ApplicationContext.injectDependencies();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        accidentController.getDao().setConnection(connection);
        carController.getDao().setConnection(connection);
    }

    public static void main(String[] args) {
        Car car1 = new Car();
        car1.setName("car1");
        car1.setSpeed(500);
        Car car2 = new Car();
        car2.setName("car2");
        car2.setSpeed(500);
        carDao.put(car1);
        car1 = carDao.get(7L).get();
        carDao.put(car2);
        car2 = carDao.get(8L).get();
        Accident accident = new Accident();
        accident.getCars().add(car1);
        accident.getCars().add(car2);
        accidentDao.put(accident);
    }
}
