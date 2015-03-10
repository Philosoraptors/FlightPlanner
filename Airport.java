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
    public float lat() { return latitude; }
    public float lng() { return longitude; }
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
     
    // some serious Dijkstra stuff
    static public List<Flight> findCheapestRoute(Airport from, Airport to) {

      // Set up arrays

      Float[] cost = new Float[Airports.size()];
      Flight[] prev = new Flight[Airports.size()];
      ArrayList<Airport> unvisited = new ArrayList<Airport>();

      cost[Airports.indexOf(from)] = 0.0f;   // initial cost is 0
      prev[Airports.indexOf(from)] = null;   // no previous node from source

      for (Airport a : Airports) {
        int aID = Airports.indexOf(a);

        if (a != from) {
          cost[aID] = Float.POSITIVE_INFINITY;   // as close to infinity as we can get
          prev[aID] = null;                      // no previous node yet
        } else {
          cost[aID] = 0f;                        // else initial cost is 0
          prev[aID] = null;
        }

        // add to unvisited
        unvisited.add(a);
      }

      // calculate cheapest routes

      while (!unvisited.isEmpty()) {
        // find airport with the smallest cost so far
        Airport a = unvisited.get(0);
        for (Airport i : unvisited) {
          //System.out.println("Checking " + i.name());
          if (cost[Airports.indexOf(i)] < cost[Airports.indexOf(a)]) a = i;
        }
        int aID = Airports.indexOf(a);
        if (!unvisited.remove(a)) { System.out.println("Unable to remove from visited airports!"); }

        //System.out.println("Airport visiting: " + a.name());  // DEBUG

        // run through airports this connects to and figure out
        // if this route is cheaper than whatever was before
        for (Flight f : a.departures()) {
          int fID = Airports.indexOf(f.to());
          Float altCost = cost[aID] + f.cost();   // calculate cost from this node

          if (altCost < cost[fID]) {              // if the route from this node is cheaper than whatever was stored before
            cost[fID] = altCost;                  // new cheapest route is this one
            prev[fID] = f;                        // new previous node is this one
          }
        }
      }

      // now run route backwards from target to find the cheapest route
      ArrayList<Flight> route = new ArrayList<Flight>();

      int node = Airports.indexOf(to);                  // note that this is an integer index of the airport
      while (prev[node] != null) {                      // until there is no previous node
        route.add(prev[node]);                          // add flight to route
        node = Airports.indexOf(prev[node].from());     // and set node to previous airport
      }

      // flip it
      Collections.reverse(route);

      // done like dinner
      return route;
    }

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
    
    public static ArrayList<Airport> findAirSinks() {
	int[] flightRatio = new int[Airports.size()];
	Arrays.fill(flightRatio, 0);
	
	for (Airport a : Airports) {
	    for (Flight f : a.departures()) {
		flightRatio[Airports.indexOf(f.from())] += 1;
		flightRatio[Airports.indexOf(f.to())] -= 1;
	    }
	}
	
	ArrayList<Airport> airSinks = new ArrayList<Airport>();
	for (Airport a : Airports) {
	    if (flightRatio[Airports.indexOf(a)] > 0) {
		airSinks.add(a);
	    }
	}
	return airSinks;
    }
    
    public static void main(String[] args) {   
      loadAirports();     
      //showAirports();     // No flights yet.
      Flight.loadFlights(); 
      //showAirports();     // each shows departing flights
      //testItineraries();

      List<Flight> route = findCheapestRoute(Airport.named(args[0]), Airport.named(args[1]));
      for (Flight f : route) { System.out.println(f.toString()); }
    }
    
}

/////////////////// EOF Airportimport java.util.*;
