package ivanov.dao;

import ivanov.model.Car;
import ivanov.model.Accident;
import ivanov.reflection.Component;
import ivanov.reflection.DependencyInjection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class AccidentDao implements DAO<Long, Accident> {

    private Connection connection;

    @DependencyInjection
    private CarDao carDao;


    public void setConnection(Connection connection) {
        this.connection = connection;
        carDao.setConnection(connection);
    }

    @Override
    public void put(Accident object) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO \"accident\" DEFAULT VALUES RETURNING id");

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long id = resultSet.getLong("id");


            for (Car car : object.getCars()) {
                carDao.update(car.getIdentity(), id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Long id, Long road) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE \"accident\" SET road_id = ? WHERE id = ?");

            statement.setInt(1, road.intValue());
            statement.setInt(2, id.intValue());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Accident> get(Long key) {
        Accident accident = new Accident();
        accident.setIdentity(key);
        List<Optional<Car>> cars = carDao.getByAccident(key);

        for (Optional<Car> car : cars) {
            accident.getCars().add(car.get());
        }

        return Optional.of(accident);

    }

    public List<Optional<Accident>> getByAccident(Long key) {
        List<Optional<Accident>> accidents = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"accident\" WHERE accident_id = ?");
            statement.setInt(1, key.intValue());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Accident accident = get(id).get();

                accidents.add(Optional.of(accident));
            }


            return accidents;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
