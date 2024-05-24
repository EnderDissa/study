import org.junit.Test;
import org.junit.jupiter.api.Assertions;
public class HitTest {
    @Test
    public void controllerNotHit() {
        Dot point = new Dot();
        point.setX(1000.0);
        point.setY(0.0);
        point.setR(2.0);
        point.controllerHit();
        System.out.println("Not Hit Test: " + point.isHit());
        Assertions.assertFalse(point.isHit());
    }
    @Test
    public void controllerHit() {
        Dot point = new Dot();
        point.setX(0.5);
        point.setY(-0.5);
        point.setR(1.0);
        point.controllerHit();
        System.out.println("Hit Test: " + point.isHit());
        Assertions.assertTrue(point.isHit());
    }

}