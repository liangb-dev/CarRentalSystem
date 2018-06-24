import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
    Assumptions:
        1. Date
           a. Automatic date insertion. It is assumed that whatever car is rented out
           or returned, will have a time-stamp of that exact moment. Therefore
           there is no functionality to manually add date from console, only from editing
           code.
           b. The date format follows "yyyyMMddHHmm", i.e. 201806242041. No functions
           have been added to make it more reader friendly so far.
           c. Rentals < 1 day are assumed to be 1 day.
        2. Class Bil
           a. Each 'registrering' is a Bil.
           b. All fields are private to prevent outside access (encapsulation).
           c. I have implemented 'Bil' as a parent class which is extended by the different
              cars that may extend it. Each child car class is the same except for the
              formula used to calculate price, and the fields: 'bilkategori', 'dygnHyra',
              and 'kmpris'. It's easy to additional car types.
           d. For each 'Bil', the basDygnsHyra and basKmPris for that category is assumed,
              as no specific requirements were given.
           e. When a car is returned, additional information needs to be provided. The
              method 'återlämnad' requests the information from the user, applies it to
              the Bil class, and returns the edited 'Bil' object.
           f. Each car should have a unique booking number, therefore there principally
              should be no two cars with the same booking number. When "Hitta Bil" is called,
              the program displays all cars that match a booking number. However when a
              car is to be returned, and the system asks for the booking number, the system
              will only modify the first match it finds from ArrayList<Bil> utlämnadeBilar.
              Perhaps it would be useful to check if a collision exists as the user enters
              the new booking number, but then again the system should be issuing the booking
              numbers, and perhaps could store them in a Set to prevent duplicates.

        3. Information
           a. The printing format and ways that information, as well as user input choices,
              are displayed are presumptive. They are crude in that they simply display all
              information at once, there is no option to select what information to see,
              nor implemented for ease to comprehend. They simply print everything.
           b. The available commands to the user through the console is basic. There can
              be more commands available.
           c. The order that user input is entered and checked for error can be improved
              upon. Rather than tell the user that he/she has made an error by the end of
              the form, it would save time to alert the user as soon as he/she enters an
              invalid input.
 */

/**
 * The rental system. It is responsible of data, retrieving and viewing of data, as well
 * as IO console interface.
 */
public class HyrSystem {

    // Fields
    private static ArrayList<Bil> utlämnadeBilar = new ArrayList<>();
    private static ArrayList<Bil> återlämnadeBilar = new ArrayList<>();

    HyrSystem() {
        BufferedReader br = null;
        String choice = "";


        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Enter command Hitta-Bokning('H'), Utlämning('U'), Återlämning('A'), Visa Bilar('V'), Exit('X'): ");
                choice = br.readLine();

                switch (choice.toUpperCase()) {
                    case "X": // Exits program
                        System.out.println("Exiting Hyrsystem...");
                        System.exit(0);
                        break;
                    case "U": // Rent out car
                        if (utlämnning()) {
                            System.out.println("[Bil Utlämnad]");
                            utlämnadeBilar.get(utlämnadeBilar.size()-1).printSpecs();
                            System.out.println("");
                        }
                        break;
                    case "A": // Return car
                        if (återlämning()) {
                            System.out.println("[Bil Återlämnad]");
                            återlämnadeBilar.get(återlämnadeBilar.size() - 1).printSpecs();
                            System.out.println("");
                        }
                        break;
                    case "H": // Find car
                        hittaBil();
                        break;
                    case "V": // Display cars
                        visaBilar();
                        break;
                    default:
                        System.out.println("Invalid command...");
                        System.out.println("Try again. ");
                        break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Display all the rented out cars ('U') or all the already returned
     * cars ('A') according to user input.
     */
    private void visaBilar() {
        // Store questions in ArrayList 'que'
        ArrayList<String> que = new ArrayList<>();
        que.add("Visa Utlämnade('U') eller Återlämnade('A'): " );
        // Acquire user answers to questions and store in ArrayList 'ans'
        ArrayList<String> ans = userIO(que);

        ArrayList<Bil> result;
        if (ans.get(0).toUpperCase().equals("U")) { // Display cars in rental
            result = utlämnadeBilar;
            if (result.size() < 1) {
                System.out.println("Det finns ingen utlämnad bil just nu. ");
            } else {
                System.out.println("De utlämnade bilarna finns nedan: ");
            }
        } else if (ans.get(0).toUpperCase().equals("A")) { // Display returned cars
            result = återlämnadeBilar;
            if (result.size() < 1) {
                System.out.println("Inga bilar har återlämnats än. ");
            } else {
                System.out.println("De återlämnade bilarna finns nedan:");
            }
        } else { // bad input
            System.out.println("Ogiltig val. Försök igen. ");
            return;
        }

        int count = 1;

        // For-loop to print all found cars
        for (Bil b : result) {
            System.out.println(count + ". ");
            b.printSpecs(); // Print car specs
            System.out.println("");
            count++;
        }
    }

    /**
     * Given a booking number by user input, returns all matching cars,
     * regardless of their rented/returned status.
     * @return
     */
    private boolean hittaBil() {
        // Store questions in ArrayList 'que'
        ArrayList<String> que = new ArrayList<>();
        que.add("Ange Boknignsnummer: ");
        // Acquire user answers to questions and store in ArrayList 'ans'
        ArrayList<String> ans = userIO(que);


        int count = 0;
        // Return all matches from 'utlämnadeBilar'
        for (int i : findCar(ans.get(0), utlämnadeBilar)) {
            count++;
            System.out.println(count + ". ");
            utlämnadeBilar.get(i).printSpecs();
        }
        // Return all matches from 'återlämnadeBilar'
        for (int i : findCar(ans.get(0), återlämnadeBilar)) {
            count++;
            System.out.println(count + ". ");
            återlämnadeBilar.get(i).printSpecs();
        }
        // If no matches were found
        if (count == 0) {
            System.out.println("Booking does not exist. ");
            return false;
        }
        return true;
    }

    /**
     * Given user input for new odometer value upon car return, adds
     * that information as well as return date and 'återlämnad' status
     * to matching Bil object.
     * Edited Bil object is added to ArrayList återlämnadeBilar (returned
     * cars) and removed from ArrayList utlämnadeBilar (cars in rental).
     * @return
     */
    private boolean återlämning() {
        // Store questions in ArrayList 'que'
        ArrayList<String> que = new ArrayList<>();
        que.add("Ange Boknignsnummer: ");
        que.add("Ange mätarställning: ");
        // Acquire user answers to questions and store in ArrayList 'ans'
        ArrayList<String> ans = userIO(que);

        // Find indexes of cars that match given booking number
        ArrayList<Integer> result = findCar(ans.get(0), utlämnadeBilar);

        // If empty array is returned then booking does not exist and can't be returned
        if (result.size() < 1) {
            System.out.println("Booking does not exist. ");
            return false;
        }

        // Although multiple cars can be found, only modify the first one found
        // There shouldn't be duplicate booking numbers, but in the event there
        // are, we also don't want to modify all of them.
        int i = 0;
        //for (int i : result) {
            återlämnadeBilar.add(utlämnadeBilar.get(i).återlämnad(Integer.parseInt(ans.get(1))));
            utlämnadeBilar.remove(i);
        //}
        return true;
    }

    /**
     * Based on user input, creates a 'Småbil', 'Kombi', or 'Lastbil' -object, and stores
     * it in ArrayList 'utlämnadeBilar'.
     * @return
     */
    private boolean utlämnning() {
        // Store questions in ArrayList 'que'
        ArrayList<String> que = new ArrayList<>();
        que.add("Ange bilkategori (småbil['S'], kombi['K'], lastbil['L']): ");
        que.add("Ange Bokningsnummer: ");
        que.add("Ange Registreringsnummer: ");
        que.add("Ange Personnummer: ");
        que.add("Ange mätarställning: ");
        // Acquire user answers to questions and store in ArrayList 'ans'
        ArrayList<String> ans = userIO(que);

        // Catches if an input other than float was entered for the odometer reading
        float mätare;
        try {
            mätare = Float.valueOf(ans.get(4));
        } catch (NumberFormatException e) {
            System.out.println("Ogiltig Mätarställning. Försök igen. ");
            return false;
        }

        // Based on user chosen type, return matching child class of 'Bil'
        switch (ans.get(0).toLowerCase()) {
            case "s":
                utlämnadeBilar.add(new SmåBil(ans.get(1), ans.get(2), ans.get(3), mätare));
                break;
            case "k":
                utlämnadeBilar.add(new Kombi(ans.get(1), ans.get(2), ans.get(3),mätare));
                break;
            case "l":
                utlämnadeBilar.add(new LastBil(ans.get(1), ans.get(2), ans.get(3), mätare));
                break;
            default: // Bad input
                System.out.println("Ogiltig bil kategori. Försök igen. ");
                return false;
        }

        return true;
    }


    /**
     * Takes in an ArrayList<String> containing questions, which are answered
     * by the user in an IO console interaction. The answers are stored in another
     * ArrayList<String> and returned by this method.
     * @param que
     * @return
     */
    private ArrayList<String> userIO(ArrayList<String> que) {
        BufferedReader br;
        ArrayList<String> ans = new ArrayList<>();

        // Catches if no questions were provided
        if (que.size() == 0) {
            return ans;
        }

        // Display each question one by one and ask for user input after
        // each. Store answers in ArrayList 'ans'.
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            String input = "";
            int i = 0;
            while (i < que.size()) {
                System.out.println(que.get(i));
                input = br.readLine();
                if (input.toUpperCase().contains("Q!")) {
                    System.out.println("Returning to main menu...");
                    ans.clear();
                    return ans;
                }
                ans.add(input);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ans;
    }

    /**
     * Looks for all Bil-objects that match a given booking number in the given
     * ArrayList<Bil> register. This gives flexibility in which register to search
     * in. Returns an ArrayList<Integer> of the indexes of all Bil-objects that
     * matched in the given 'register'.
     * @param match
     * @param register
     * @return
     */
    private ArrayList<Integer> findCar(String match , ArrayList<Bil> register) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < register.size(); i++) {
            Bil current = register.get(i);
            if (current.getBoknum().equals(match)) {
                result.add(i);
            }
        }
        return result;
    }

}
