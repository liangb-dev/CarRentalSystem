import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.AlgorithmConstraints;
import java.util.ArrayList;

/*
    Problems to fix:
    *. Control of duplicate booking numbers
    *. Control of invalid inputs
    *. See all cars rented & returned
    4. Comments and documentation
    5. Clarifications (choice of time presentation, Bil-objects, number of days in month)
 */


public class HyrSystem {

    // Fields
    static ArrayList<Bil> utlämnadeBilar = new ArrayList<>();
    static ArrayList<Bil> återlämnadeBilar = new ArrayList<>();

    HyrSystem() {

        BufferedReader br = null;
        String choice = "";

        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Enter command Hitta-Bokning('H'), Utlämning('U'), Återlämning('A'), Visa Bilar('V'), Exit('X'): ");
                choice = br.readLine();

                switch (choice.toUpperCase()) {
                    case "X":
                        System.out.println("Exiting Hyrsystem...");
                        System.exit(0);
                        break;
                    case "U":
                        if (utlämnning()) {
                            System.out.println("[Bil Utlämnad]");
                            utlämnadeBilar.get(utlämnadeBilar.size()-1).printSpecs();
                            System.out.println("");
                        }
                        break;
                    case "A":
                        if (återlämning()) {
                            System.out.println("[Bil Återlämnad]");
                            återlämnadeBilar.get(återlämnadeBilar.size() - 1).printSpecs();
                            System.out.println("");
                        }
                        break;
                    case "H":
                        hittaBil();
                        break;
                    case "V":
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


    private void visaBilar() {
        ArrayList<String> que = new ArrayList<>();
        que.add("Visa Utlämnade('U') eller Återlämnade('A'): " );

        ArrayList<String> ans = userIO(que);
        if (ans.size() < 1) {
            return;
        }
        ArrayList<Bil> result = new ArrayList<>();
        if (ans.get(0).toUpperCase().equals("U")) {
            result = utlämnadeBilar;
        } else if (ans.get(0).toUpperCase().equals("A")) {
            result = återlämnadeBilar;
        } else {
            System.out.println("Ogiltig val. Försök igen. ");
            return;
        }

        int count = 1;
        for (Bil b : result) {
            System.out.println(count + ". ");
            b.printSpecs();
            System.out.println("");
            count++;
        }
    }

    private boolean hittaBil() {
        ArrayList<String> que = new ArrayList<>();
        que.add("Ange Boknignsnummer: ");

        ArrayList<String> ans = userIO(que);
        if (ans.size() < 1) {
            return false;
        }

        int count = 0;
        for (int i : findCar(ans.get(0), utlämnadeBilar)) {
            count++;
            System.out.println(count + ". ");
            utlämnadeBilar.get(i).printSpecs();
        }
        for (int i : findCar(ans.get(0), återlämnadeBilar)) {
            count++;
            System.out.println(count + ". ");
            återlämnadeBilar.get(i).printSpecs();
        }

        if (count == 0) {
            System.out.println("Booking does not exist. ");
            return false;
        }
        return true;
    }

    private boolean återlämning() {
        ArrayList<String> que = new ArrayList<>();


        que.add("Ange Boknignsnummer: ");
        que.add("Ange mätarställning: ");

        ArrayList<String> ans = userIO(que);
        if (ans.size() < 1) {
            return false;
        }

        ArrayList<Integer> result = findCar(ans.get(0), utlämnadeBilar);

        if (result.size() < 1) {
            System.out.println("Booking does not exist. ");
            return false;
        }

        for (int i : result) {
            återlämnadeBilar.add(utlämnadeBilar.get(i).återlämnad(Integer.parseInt(ans.get(1))));
            utlämnadeBilar.remove(i);
        }
        return true;
    }

    private boolean utlämnning() {
        ArrayList<String> que = new ArrayList<>();

        que.add("Ange bilkategori (småbil['S'], kombi['K'], lastbil['L']): ");
        que.add("Ange Bokningsnummer: ");
        que.add("Ange Registreringsnummer: ");
        que.add("Ange Personnummer: ");
        que.add("Ange mätarställning: ");

        float mätare;

        ArrayList<String> ans = userIO(que);
        if (ans.size() < 1) {
            return false;
        }
        try {
            mätare = Float.valueOf(ans.get(4));
        } catch (NumberFormatException e) {
            System.out.println("Ogiltig Mätarställning. Försök igen. ");
            return false;
        }


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
                default:
                    System.out.println("Ogiltig bil kategori. Försök igen. ");
                    return false;
            }

        return true;

    }


    private ArrayList<String> userIO(ArrayList<String> que) {
        BufferedReader br = null;
        ArrayList<String> ans = new ArrayList<>();

        if (que.size() == 0) {
            return ans;
        }

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


    private ArrayList<Integer> findCar(String match , ArrayList<Bil> register) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < register.size(); i++) {
            Bil current = register.get(i);
            if (current.boknum.equals(match)) {
                result.add(i);
            }
        }
        return result;
    }

}
