package ivanov.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ivanov.dao.AccidentDao;
import ivanov.model.Car;
import ivanov.model.Accident;
import ivanov.reflection.Component;
import ivanov.reflection.DependencyInjection;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component
@WebServlet("/accident")
public class AccidentController extends HttpServlet {

    @DependencyInjection
    private AccidentDao dao;

    public AccidentDao getDao() {
        return dao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Accident musicRecording = dao.get(id).get();
        PrintWriter out = response.getWriter();
        String json = new JSONObject(musicRecording).toString();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuffer buffer = new StringBuffer();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            buffer.append(inputLine);
        }
        JSONObject jsonObject = new JSONObject(buffer.toString());
        Map<String, Object> map = jsonObject.toMap();

        List<Car> cars = new ArrayList<>() {};
        for (Object id: (List<Object>) map.get("cars")) {
            Car car = new Car();
            car.setIdentity(Long.parseLong((String) id));
            cars.add(car);
        }

        Accident accident = new Accident();
        accident.setCars(cars);
        dao.put(accident);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new JSONObject(accident).toString());

    }
}
