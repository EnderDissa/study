import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped
public class DotsBean implements Serializable {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

    private String owner = session.getId();
    //private String owner = "owner";
    //номер текущей страницы
    private int str = 0;

    private Dot newDot = new Dot();
    private final DatabaseManager databaseManager = new DatabaseManager();
    private List<Dot> dots = new LinkedList<>();
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public List<Dot> getDots(){
        return dots;
    }

    //список номеров страниц, отображаемых в пагинации
    private List<Integer> pagBut = new ArrayList<>();
    public List<Integer> getPagBut(){
        return pagBut;
    }

    //собирает новый список страниц для пагинации
    public void newPagBut(){
        pagBut.clear();
        int count = databaseManager.strCount(owner);
        if(count <= 5) {
            for (int i = 0; i < count; i++) {
                pagBut.add(i);
            }
        } else {
            pagBut.add(0);

            if(str < 4) {
                for(int i = 1; i <= str; i++){
                    pagBut.add(i);
                }
            } else {
                pagBut.add(-1);
                for(int i = str -2; i <= str; i++){
                    pagBut.add(i);
                }
            }

            if(str + 3 < count){
                for(int i = str; i <= str + 2; i++){
                    pagBut.add(i);
                }
                pagBut.add(-1);
            } else {
                for(int i = str; i < count - 1; i++){
                    pagBut.add(i);
                }
            }
            pagBut.add(count - 1);
            pagBut = new HashSet<>(pagBut).stream().sorted().collect(Collectors.toList());
        }
    }

    //возвращает стайо класс для кнопок пагинации, чтобы скрыть пропуски и выделить текущую страницу

    public String getPageClass(int page){
        if(page == -1) return "nonePag";
        if(page == str) return "currentPag";
        return "pagination";
    }

    //достает все точки автора из бд
    // @PostConstruct
    public void loadFromDatabase(){
        str = 0;
        this.dots = databaseManager.getNext20DotsByOwner(owner, str);
        newPagBut();

    }



    //достает первые 20 точек из бд - первую страницу
    @PostConstruct
    public void loadStartStr(){
        str = 0;
        newPagBut();
        this.dots = databaseManager.get20DotsByOwner(owner);
    }



    // достает нужную стр из бд по номеру, меняет флаги пагинации и текущую стр
    public void loadStr(int str){
        this.str = str;
        this.dots = databaseManager.getNext20DotsByOwner(owner, str);
        newPagBut();
    }



    //достает вообще все точки из бд
    // @PostConstruct
    public void loadAllDotsFromDatabase(){
        this.dots = databaseManager.getAllDots();
        dots = dots.stream().sorted(Comparator.comparing(Dot::getTime).reversed()).collect(Collectors.toList());

        System.out.println("added dots: " + dots.size() );
    }


    //пришли новые данные, создаем новую точку и вызываем добавление ее в бд
    public void add(){
        newDot.setResult(isInArea(newDot));
        Date d = new Date();
        newDot.setTime(formatter.format(d));
        newDot.setOwner(owner);
        this.addDot(newDot);
        newDot = new Dot(newDot.getX(), newDot.getY(), newDot.getR(), newDot.getOwner());
    }

    //добавляет точку в бд, загружаем первуб страницу
    public void addDot(Dot dot){
        if(databaseManager.addDot(dot)) System.out.println("dot was added to database");
        else System.out.println("dot was NOT added to database");
        loadStartStr();
    }


    //пришла новая точка с канваса, создаем и вызываем ее добавление в бд
    public void addFromCanvas(){
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        // params.values().forEach(System.out::println);
        try{
            double x = Double.parseDouble(params.get("x"));
            double y = Double.parseDouble(params.get("y"));
            double r = Double.parseDouble(params.get("r"));

            Dot dot = new Dot(x, y, r, owner);
            Date d = new Date();
            dot.setTime(formatter.format(d));
            dot.setResult(isInArea(dot));
            this.addDot(dot);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    public void setNewDot(Dot newDot){
        this.newDot = newDot;
        this.newDot.setOwner(owner);
        this.newDot.setResult(isInArea(this.newDot));

    }

    public void setDots(List<Dot> dots){
        this.dots = dots;
    }
    public Dot getNewDot(){
        return newDot;
    }

    public void updateR(double r){
        System.out.println("RR= "+r);
        newDot.setR(r);
    }

    //проверяет попадание точки в область
    private boolean isInArea(Dot dot){
        Double x = dot.getX();
        Double y = dot.getY();
        Double r = dot.getR();

        return ((x * x + y * y <= r * r) && -r <= y && y <= 0 && x<=0) ||     // л н круг
                (x >= -r && y <= r/2 && x <= 0 && y >= 0) ||     // Прямоугольник л в
                (y <= - (2*x) + (r)  && x >= 0 && y >= 0); // треугл

    }
}