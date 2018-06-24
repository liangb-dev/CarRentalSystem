import java.text.SimpleDateFormat;
import java.util.Date;

public class Kombi extends Bil{

    // Constructor
    Kombi(String boknum, String regnum, String personnum, float mätare) {
        super(boknum, regnum, personnum, "Kombi", mätare,
                new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
    }

    /**
     * 'Kombi' implementation of calculateTotalPrice.
     * @param antalDygn
     * @param antalKm
     * @return
     */
    protected double calculateTotalPrice(int antalDygn, float antalKm) {
        double price = basDygnsHyra * antalDygn * 1.3 + basKmPris * antalKm;
        return price;
    }

}
