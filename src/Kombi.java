import java.text.SimpleDateFormat;
import java.util.Date;

public class Kombi extends Bil{

    // Constructor
    Kombi(String boknum, String regnum, String personnum, float mätare) {
        super(boknum, regnum, personnum, "Kombi", mätare,
                new SimpleDateFormat("yyyyMMddHHmm").format(new Date()),20,5);
    }

    protected double calculateTotalPrice(int antalDygn, float antalKm) {
        double price = basDygnsHyra * antalDygn * 1.3 + basKmPris * antalKm;
        return price;
    }

}
