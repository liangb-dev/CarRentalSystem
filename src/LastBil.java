import java.text.SimpleDateFormat;
import java.util.Date;

public class LastBil extends Bil {

    // Constructor
    LastBil(String boknum, String regnum, String personnum, float mätare) {
        super(boknum, regnum, personnum, "Lastbil", mätare,
                new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
    }

    /**
     * 'Lastbil' implementation of calculateTotalPrice.
     * @param antalDygn
     * @param antalKm
     * @return
     */
    protected double calculateTotalPrice(int antalDygn, float antalKm) {
        double price = basDygnsHyra * antalDygn * 1.5 + basKmPris * antalKm * 1.5;
        return price;
    }

}
