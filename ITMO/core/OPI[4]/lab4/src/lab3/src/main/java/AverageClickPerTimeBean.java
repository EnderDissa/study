import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.management.*;
import java.lang.management.ManagementFactory;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@ManagedBean
@SessionScoped
public class AverageClickPerTimeBean{
    private List<LocalTime> clicksTimeList = new ArrayList<>();
    private Double averageValue = 0.0;
//    public AverageClickPerTimeBean() {
//try {
//        ObjectName objectName = new
//        ObjectName("space.arlet.mbeans:name=timeBean");
//        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
//        server.registerMBean(this, objectName);
//        } catch (MalformedObjectNameException |
//        NotCompliantMBeanException | InstanceAlreadyExistsException |
//        MBeanRegistrationException e) {
//        throw new RuntimeException(e);
//        }
//        }
public void update() {
        clicksTimeList.add(LocalTime.now());
        long sum = 0;
        for (int i = 1; i < clicksTimeList.size(); i++) {
        long timeClick = clicksTimeList.get(i).toSecondOfDay();
        long previousTimeClick = clicksTimeList.get(i - 1).toSecondOfDay();
        sum += timeClick - previousTimeClick;
        }
        averageValue = sum / (clicksTimeList.size() * 1.0 - 1);
        }
public List<LocalTime> getClicksTimeList() {
        return clicksTimeList;
        }
public Double getAverageValue() {
        this.update();
        return averageValue;
        }
        }

//@ManagedBean
//@SessionScoped
//public class ClickIntervalMBean {
//    private long previousClickTime;
//    private long currentClickTime;
//    private int clickCount;
//    private double averageClickInterval;
//
//    public void registerClick() {
//        currentClickTime = System.currentTimeMillis();
//        if (clickCount > 0) {
//            averageClickInterval = ((averageClickInterval * (clickCount - 1)) + (currentClickTime - previousClickTime)) / clickCount;
//        }
//        previousClickTime = currentClickTime;
//        clickCount++;
//    }
//
//    public double getAverageClickInterval() {
//        return averageClickInterval;
//    }
//}
