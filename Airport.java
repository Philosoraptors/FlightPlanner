import java.util.*;
import java.io.*;

public class Airport {
  private String name;
  private String city;
  private float latitude;
  private float longitude;
  private int delay;
  ArrayList <Flight> departures;
  
  static ArrayList<Airport> Airports = new ArrayList<Airport>();  
  
  static boolean verbose = true;
  
  public Airport (String code3, float lat, float lon, int layover, String cityName){
    name = code3;
    latitude = lat;
    longitude = lon;
    delay = layover;
    city = cityName;
    departures = new ArrayList<Flight>();
    Airports.add(this);
  }
     
  ////////////////////////////////////// instance methods ((this) is an instance of Airport)
 
   public String name(){ return name; }
   public ArrayList<Flight> departures () { return departures; }

   public String toString(){
     return (name + " " + latitude + " " + longitude + " " + delay + " " + city);
   }
   
   public void showFlights(){
      for(Flight flt: departures){
       System.out.println("\t" + flt);
      }
   }
   
   public void addDeparture(Flight flt){  departures.add(flt); }
   
  public void listAllCheapest() { 
     HashMap<Airport, Itinerary> bests = findAllCheapest();
     System.out.println("\nCheapest costs from " + this);
      for (Airport apt : Airports) {
        System.out.println(apt.name() + ": " + bests.get(apt));
        }
    }

  //// The following findAllCheapest method is purposely corrupt.
  //// It is type-correct, but a fat lot of good that does us: it gives WRONG answers
  ////  and almost all of the snawers are wrong.
  ////  It is supposed to implement the ToDoList pattern, but does not do it properly
  
  public HashMap<Airport, Itinerary> findAllCheapest() { 
      HashMap<Airport, Itinerary> bests = new HashMap<Airport, Itinerary>();    
      
      for (Airport apt : Airports) { bests.put(apt, new Itinerary()); }
      bests.get(this).setCost(0F); 
      ArrayList<Airport> toDo = new ArrayList<Airport>();
      toDo.add(this);
      
        Airport cheapo = toDo.get(0);
        toDo.remove(cheapo);
        Itinerary cheapIt = bests.get(cheapo);
        if (verbose) {System.out.println("Cheap: " + cheapo.name() + " = " + cheapIt);}
        for(Flight flt : cheapo.departures()) {
          Itinerary newIt = cheapIt.addFlight(flt);
          Airport reach = flt.to();
          if (newIt.cost() < bests.get(reach).cost()){
            bests.put(reach, newIt);
            toDo.add(reach);
          }
        }
      
      return(bests);
    }

       
 ///////////////////////////////////////////////////// static (class) methods below   
  
   public static void showAirports(){
     for(Airport apt: Airports){
       System.out.println(apt);
       apt.showFlights();
     }
   }
    
   public static Airport named(String nm3){
     for(Airport apt: Airports){
       if(apt.name().equals(nm3))
         return apt;
     }
   System.out.println("No such airport: " + nm3);
   return null;
   }
   
   public static void loadAirports() {     
     try{
       Scanner scn = new Scanner(new File("airports.csv")).useDelimiter(",\\s*");
       scn.nextLine();
       while (scn.hasNext()) {
         new Airport(scn.next(), scn.nextFloat(), scn.nextFloat(), scn.nextInt(), scn.next());
       }
     } catch (Exception ex){ ex.printStackTrace(); }
   }
   
   public static void testItineraries() {
      Airport.named("SEA").listAllCheapest();
   }
   
   public static void main (String[] args){   
      loadAirports();     
      showAirports();     // No flights yet.
      Flight.loadFlights(); 
      showAirports();     // each shows departing flights
      testItineraries();
   }
  
}

/////////////////// EOF Airportimport java.util.*;
