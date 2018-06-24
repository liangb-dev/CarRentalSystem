import org.junit.Test;
import org.junit.Assert;

public class BilTest {

    Bil sb = new SmåBil("a","b", "c", 0);
    Bil k = new Kombi("ab", "cd", "ef", 0);
    Bil lb = new LastBil("ag", "ff", "oo", 0);

    @Test
    public void calculateTotalPrice() {
        // Småbil
        Assert.assertEquals(2000, sb.calculateTotalPrice(10,500), 0.0001);
        Assert.assertEquals(2000, sb.calculateTotalPrice(10,500), 0.0001);
        Assert.assertEquals(0, sb.calculateTotalPrice(0,500), 0.0001);
        // Kombi
        Assert.assertEquals(3600, k.calculateTotalPrice(10,500), 0.0001);
        Assert.assertEquals(1000, k.calculateTotalPrice(0,500), 0.0001);
        Assert.assertEquals(2600, k.calculateTotalPrice(10,0), 0.0001);
        // Lastbil
        Assert.assertEquals(3000, lb.calculateTotalPrice(10,0), 0.0001);
        Assert.assertEquals(1500, lb.calculateTotalPrice(0,500), 0.0001);
        Assert.assertEquals(4500, lb.calculateTotalPrice(10,500), 0.0001);
    }

}
