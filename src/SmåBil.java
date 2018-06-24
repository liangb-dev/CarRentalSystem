import java.text.SimpleDateFormat;
import java.util.Date;

public class SmåBil extends Bil {

    // Constructor
    SmåBil(String boknum, String regnum, String personnum, float mätare) {
        super(boknum, regnum, personnum, "Småbil", mätare,
                new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
    }

    /**
     * 'Småbil' implementation of calculateTotalPrice.
     * @param antalDygn
     * @param antalKm
     * @return
     */
    protected double calculateTotalPrice(int antalDygn, float antalKm) {
        double price = basDygnsHyra * antalDygn;
        return price;
    }

}
