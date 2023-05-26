package ivanov;
import ivanov.controller.AccidentController;
import ivanov.controller.CarController;
import ivanov.reflection.ApplicationContext;
import ivanov.reflection.DependencyInjection;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainServer {


    @DependencyInjection
    private static AccidentController accidentController;

    @DependencyInjection
    private static CarController carController;


    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";

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

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/api");

        servletContextHandler.addServlet(new ServletHolder(accidentController), "/accident");
        servletContextHandler.addServlet(new ServletHolder(carController), "/car");

        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
