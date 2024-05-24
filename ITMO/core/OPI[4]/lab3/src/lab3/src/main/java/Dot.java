import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.ValueChangeEvent;

public class Dot {

    private Double x = 0.0;
    private Double y;
    private Double r;
    private boolean result;
    private String owner;
    private String time;
    private int id;
//    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public Dot(Double x, Double y, Double r, String owner) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.owner = owner;
        //this.hit = isInArea();
//        Date d = new Date();
//        this.time = formatter.format(d);
    }

    public Dot() {
//        Date d = new Date();
//        this.time = formatter.format(d);
    }


    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getR() {
        return r;
    }

    public String getOwner() {
        return owner;
    }

    public String getTime() {
        return time;
    }

    public boolean getResult() {
        return result;
    }

    public int getId() {
        return id;
    }

    public String getResultString() {
        if (result) return "Точка попала";
        else return "Точка не попала";
    }

    public String getResultClass() {
        if (result) return "success";
        else return "fail";
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setResult(boolean isInArea) {
        this.result = isInArea;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void updateX(ValueChangeEvent e) {
        String id = ((HtmlSelectBooleanCheckbox) e.getSource()).getId();
        boolean isChecked = (boolean) e.getNewValue();
        if (isChecked) {
            setX((double) Float.parseFloat(id.substring(5, id.length()).replace("x", ".")));
        }
    }

    public void controllerHit() {
        Double x = this.getX();
        Double y = this.getY();
        Double r = this.getR();

        setResult( ((x * x + y * y <= r * r) && -r <= y && y <= 0 && x>=0) ||     // л н круг
                (x <= r/2 && y <= r && x >= 0 && y >= 0) ||     // Прямоугольник л в
                (y <= (x) + (r)  && x <= 0 && y >= 0)); // треугл
    }

    public boolean isHit() {
        return this.result;
    }
}