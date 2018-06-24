public abstract class Bil {
    // fields
    private String status = ""; // 'återlämnad' or 'utlämnad'
    private String boknum = "";
    private String regnum = "";
    private String personnum = "";
    private String biltyp = "";
    private String utlämnad = ""; // date
    private String återlämnad = ""; // date
    private float mätare = 0;
    private float slutställning = 0;
    protected float basDygnsHyra = 0;
    protected float basKmPris = 0;

    private int antalDygn = 0;
    private float antalKm = 0;
    private double pris = 0;



    Bil(String boknum, String regnum, String personnum, String biltyp, float mätare, String utlämnad) {
        this.status = "utlämnad";
        this.boknum = boknum;
        this.regnum = regnum;
        this.personnum = personnum;
        this.mätare = mätare;
        this.biltyp = biltyp;
        this.utlämnad = utlämnad;

        this.basDygnsHyra = 200;
        this.basKmPris = 2;
    }


    // Methods
    /**
     * Abstract method implemented separately by child classes depending on their
     * own formulas.
     * @param antalDygn
     * @param antalKm
     * @return
     */
    protected abstract double calculateTotalPrice(int antalDygn, float antalKm);

    /**
     * Calculates and modifies the following fields:
     *   - this.status (= 'återlämnad')
     *   - this.slutställning (= given parameter)
     *   - this.återlämnad (= current date)
     *   - this.antalDygn (= return date - rented date)
     *   - this.antalKm (= odometer_now - odometer_before)
     *   - this.pris (calls CalculateTotalPrice)
     * And returns current (post-modifications) object.
     *
     * @param mätarställning
     * @return
     */
    protected Bil återlämnad(float mätarställning) {
        this.status = "återlämnad";
        this.slutställning = mätarställning;
        this.återlämnad = "201807232340";//new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        this.antalKm = this.slutställning - this.mätare;

        String utlånad = this.utlämnad;
        String återlämnad = this.återlämnad;

        // Calulate number of days in rental
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
        this.antalDygn = (hour + min/60)/24 + day + months*30;

        // Accomodating for rentals < 1 day
        if (this.antalDygn > 1) {
            this.antalDygn--;
        }
        // Calculate price
        this.pris = calculateTotalPrice(antalDygn, antalKm);

        return this;
    }

    /**
     * Prints out all available information on Bil object.
     * If the car has been returned, then it will also print out
     * the 'Faktura' (Billing) including so far antalDygn (number
     * of days rented), antalKm (number of Km driven), and pris
     * (total price).
     */
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

    /**
     * Getter for field this.boknum.
     * @return
     */
    public String getBoknum() {
        return this.boknum;
    }


}
