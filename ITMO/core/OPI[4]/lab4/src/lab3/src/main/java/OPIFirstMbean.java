import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.SQLException;

@ManagedBean
@SessionScoped
public class OPIFirstMbean implements Serializable {
    private int totalDotsCounter = 0;
    private int hitDotsCounter = 0;
    private double avrgClickInterval = 0;

FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    private final DatabaseManager databaseManager = new DatabaseManager();
    private String owner = session.getId();

    public String getMessage(){
        String message = "";
        totalDotsCounter = databaseManager.totalDotsCount(owner);
        hitDotsCounter = databaseManager.hitDotsCount(owner);
        if(totalDotsCounter % 5 ==0 && totalDotsCounter != 0){
            message = "You have reached another 5 dots\n";
        }
        return message;
    }
    public int getTotalDotsCounter(){
        totalDotsCounter = databaseManager.totalDotsCount(owner);
        return totalDotsCounter;
    }

    public int getHitDotsCounter(){
        hitDotsCounter = databaseManager.hitDotsCount(owner);
        if (hitDotsCounter % 5 == 0) {
            // Отобразить сообщение пользователю
            FacesMessage message = new FacesMessage("Вы добавили 5 точек!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return hitDotsCounter;
    }

    public double getAvrgClickInterval(){
        return avrgClickInterval;
    }


//    public void loadStartStr(){
//        str = 0;
//        newPagBut();
//        this.dots = databaseManager.get20DotsByOwner(owner);

    @PostConstruct
    public void loadTotalCount() {
//        this.totalDotsCounter++;
//        this.hitDotsCounter++;
        hitDotsCounter = databaseManager.hitDotsCount(owner);
        totalDotsCounter = databaseManager.totalDotsCount(owner);
    }

//    public void loadHitCount(){
//
//    }

//    @PostConstruct
//    public void loadHitCount() {
//        this.hitDotsCounter = databaseManager.hitDotsCount(owner);
//    }
}
