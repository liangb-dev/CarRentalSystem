import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Bil {
    // fields
    String status = "";
    String boknum = "";
    String regnum = "";
    String personnum = "";
    String biltyp = "";
    String utlämnad = "";
    String återlämnad = "";
    float mätare = 0;
    float slutställning = 0;
    float basDygnsHyra = 0;
    float basKmPris = 0;

    int antalDygn = 0;
    float antalKm = 0;
    double pris = 0;



    Bil(String boknum, String regnum, String personnum, String biltyp, float mätare, String utlämnad, float dygnHyra, float kmpris) {
        this.status = "utlämnad";
        this.boknum = boknum;
        this.regnum = regnum;
        this.personnum = personnum;
        this.mätare = mätare;
        this.biltyp = biltyp;
        this.utlämnad = utlämnad;

        this.basDygnsHyra = dygnHyra;
        this.basKmPris = kmpris;
    }


    // Methods
    protected abstract double calculateTotalPrice(int antalDygn, float antalKm);

    protected Bil återlämnad(float mätarställning) {
        this.status = "återlämnad";
        this.slutställning = mätarställning;
        this.återlämnad = "201807232340";//new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        this.antalDygn = antalDygn;
        this.antalKm = antalKm;
        this.pris = pris;

        String utlånad = this.utlämnad;
        String återlämnad = this.återlämnad;

        int umin = Integer.parseInt("" + utlånad.charAt(10) + utlånad.charAt(11));
        int uhour = Integer.parseInt("" + utlånad.charAt(8) + utlånad.charAt(9));
        int uday = Integer.parseInt("" + utlånad.charAt(6) + utlånad.charAt(7));
        int umonths = Integer.parseInt("" + utlånad.charAt(4) + utlånad.charAt(5));

        int åmin = Integer.parseInt("" + återlämnad.charAt(10) + återlämnad.charAt(11));
        int åhour = Integer.parseInt("" + återlämnad.charAt(8) + återlämnad.charAt(9));
        int åday = Integer.parseInt("" + återlämnad.charAt(6) + återlämnad.charAt(7));
        int åmonths = Integer.parseInt("" + återlämnad.charAt(4) + återlämnad.charAt(5));

        int min = (60 - umin) + åmin;
        int hour = (24 - uhour) + åhour;
        int day = (30 - uday) + åday;
        int months = åmonths - umonths - 1;

        antalDygn = (hour + min/60)/24 + day + months*30;
        if (antalDygn > 1) {
            antalDygn--;
        }
        antalKm = this.slutställning - this.mätare;

        this.pris = calculateTotalPrice(antalDygn, antalKm);


        return this;
    }


    protected void printSpecs() {
        System.out.println("Bil Status: " + status);
        System.out.println("Bokningsnummer: " + boknum);
        System.out.println("Registreringsnummer: " + regnum);
        System.out.println("Personnummer: " + personnum);
        System.out.println("Bilkategori: " + biltyp);
        System.out.println("Utlämnad: " + utlämnad);
        System.out.println("Återlämnad: " + återlämnad);
        System.out.println("Mätare: " + mätare);
        System.out.println("Slutställning: " + slutställning);
        System.out.println("basDygnsHyra: " + basDygnsHyra);
        System.out.println("basKmPris: " + basKmPris);

        if (status.equals("återlämnad")) {
            System.out.println("===== Faktura ===== ");
            System.out.println("antalDygn: " + antalDygn);
            System.out.println("antalKm: " + antalKm);
            System.out.println("Total Pris: " + pris);
        }

    }


}
