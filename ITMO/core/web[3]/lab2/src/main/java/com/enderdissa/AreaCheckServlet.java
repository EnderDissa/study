package com.enderdissa;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.enderdissa.table.Table;
import com.enderdissa.table.TableRow;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

@WebServlet(name="AreaCheckServlet", urlPatterns="/AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getDispatcherType().name().equals("FORWARD")) {
            resp.sendError(403, "у вас нет доступа к этому ресурсу");
            return;
        }
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkPoint(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkPoint(request, response);
    }

    private void checkPoint(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        long currentTime = System.nanoTime();

        try {
            float x = Float.parseFloat(request.getParameter("x"));
            float y = Float.parseFloat(request.getParameter("y"));
            float r = Float.parseFloat(request.getParameter("r"));
            long offset = -Long.parseLong(request.getParameter("offset"));
            
            Instant clientTime = Instant.now().truncatedTo(ChronoUnit.MILLIS).plus(offset, ChronoUnit.MINUTES);
            boolean result = checkArea(x,y,r);

            HttpSession session = request.getSession();
            double scriptWorkingTime = System.nanoTime() - currentTime;
            TableRow newRow = new TableRow(x, y, r, result, clientTime.toString(), scriptWorkingTime);

            if (session.getAttribute("table") == null){
                session.setAttribute("table", new Table());
            }

            Table sessionTable = (Table) request.getSession().getAttribute("table");
            sessionTable.getTableRows().add(newRow);

            PrintWriter out = response.getWriter();
            out.print(new JSONObject(newRow));
            out.close();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            response.sendError(400, "значения не валидны!");
        } catch (IOException e) {
            response.sendError(418, "неизвестная ошибка");
        }
    }

    private boolean checkArea(float x, float y, float r){
        if (x>=0 && y<=0){
            if (x<=r && y>=-r) return true;
        }
        else if (x<=0 && y>=0){
            if (y <= (x*2 + r)) return true;
        }
        else if (x<=0 && y<=0){
            if ((x*x + y*y) < r*r) return true;
        }
        return false;
    }
}
