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
	AirMap funMap = new AirMap(500,500,30);
	funMap.showMap();
	
	System.out.println("The Airport Sinks: Where incoming flights are greater than outgoing flights:");
	for (Airport a : Airport.findAirSinks()) {
	    System.out.println(a.name());
	}
    }
}
