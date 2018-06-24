# CarRentalSystem

This is a car rental system that I recenly implemented as part of a coding exercise for a company interview. I have implemented it as a console IO interaction with the user. There is a parent class called 'Bil' (car in Swedish), that is extended by the child classes 'Småbil' (small car), 'Kombi' (combi), and 'Lastbil' (truck). This makes it easier to add cars (as children classes of 'Bil') in the future.  
</br>
The system keeps track of cars that have been rented out using Arraylist<Bil> utlämnade, and cars that have been returned using ArrayList<Bil> återlämnade.   
</br>
The user interface consists of asking the user which command to run out of "Hitta Bil"["H"], "Utlämning"["U"], "Återlämning"["A"], and "Exit"["X"], where each is followed by an additional set of instructions. By the end of each command, the interface returns to the main menu above.  
</br>
  <em>"Hitta Bil"</em>: Upon providing the "bokningsnummer" (booking number), the system looks for all cars that match the given 'bokningsnummer' and prints them.   
  <em>"Utlämning"</em>: Rental creation which asks the user for information such as type of car, booking number, registration number, person number, and initial odometer reading. After the interaction, the system creates a corresponding Bil-object and stores it in ArrayList<Bil> 'utlämnadeBilar' (rented cars).   
  <em>"Återlämning"</em>: Asks user for information such as the booking number, final odometer reading. These information as well as the date of return are stored into the Bil-object that has a matching booking number. The total rental days and distance travelled are calculated and stored in the Bil-object as well. The Bil-object is then added to ArrayList<Bil> "återlämnadeBilar" (returned cars) and removed from ArrayList<Bil> "utlämnadeBilar".    
  <em>"Visa Bilar"</em>: Asks user if he/she wants to display all "utlämnade" cars or all "återlämnade" cars, then prints all information on every car in "utlämandeBilar" or "återlämnadeBilar".   
  <em>"Exit"</em>: Exits the system.   

The requirements were as follows:  
  There are three types of cars for rental - Småbil, Kombi, and Lastbil. Price for each is calculated differently, as shown below:  
      Småbil Pris = basDygnsHyra * antalDygn   
      Kombi Pris = basDygnsHyra * antalDygn * 1.3 + basKmPris * antalKm  
      Lastbil Pris = basDygnsHyra * antalDygn * 1.5 + basKmPris * antalKm * 1.5  
  
  The system must have two major functionalities:    
      1. Registering a car that's being rented out  
        - Booking number  
        - Registration number  
        - Customer's person number  
        - Car category   
        - Date and time  
        - Odometer reading  
      2. Registering when a car is being returned  
        - Booking number  
        - Date and time  
        - New odometer reading  
        
</br>By the end of the registration, the system will calculate the price for the rental based on number of days rented, distance   travelled, and each car-type's price formula.   
      
      
<h3>There were a few assumptions made within this code:</h3>

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
      d. Another additional option in the future could be the ability to modify
         registrations.
