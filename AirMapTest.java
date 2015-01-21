/**
 * Test the AirMap class for drawing 
 * the airports.
 */


public class AirMapTest {
    public static void main(String[] args) {
       // load airports and flights
       Airport.loadAirports();  
       Flight.loadFlights();

       // create new AirMap and display it
       AirMap.showMap();

       for (Airport a : Airport.findAirSinks()) {
         System.out.println(a.name());
       }
    }
}
