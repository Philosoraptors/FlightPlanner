import java.util.*;
import java.io.*;
import java.lang.*;

public class Itinerary {
    Boolean placeHolder;
    float cost = 9999999F;
    int duration = 9999999;
    ArrayList<Flight> stops = new ArrayList<Flight>();
  
    //////////////////// 3 different constructors for Itinerary
    
    public Itinerary (Flight flt) {
	placeHolder = false;
	stops.add(flt);
	cost = flt.cost();
    }
    
    public Itinerary () {
	placeHolder = true;
	cost = 999999F;
	duration = 9999999;
    }
    
    public Itinerary (Itinerary sofar, Flight flt) {
	
	if (sofar.placeHolder()) {
	    cost = flt.cost();
	    stops = new ArrayList<Flight>();
	} else {
	    cost = sofar.cost() + flt.cost();
	    stops = new ArrayList<Flight>(sofar.stops());
	}
	stops.add(flt);
	placeHolder = false;
    }
    
    //// return a new Itinerary with this flight tacked onto the end.  Do NOT change the original.
    public Itinerary addFlight(Flight flight) {
	return ( new Itinerary(this, flight));
    }
    
    
    public void setCost(Float cst) { cost = cst;}
    private Boolean placeHolder() { return(placeHolder); }
    public float cost() { return(cost);}
    public ArrayList<Flight> stops(){ return(stops);}
    
    public Flight last() { return(stops.get(stops.size()-1));}
    
    public Airport to() { return(this.last().to());}
    
    // public int arrivalTime() { return (this.last().arrivalTime());}
    
    public void printItemized() {  
	if (placeHolder) { return; }
	for (Flight flt : stops) {
	    System.out.println("->" + flt);   
	}
    }
    
  public String toString() {
      if (placeHolder) { return ("*****"); }
      String rslt = "$" + cost + " \t" + duration + "min \t" + stops.get(0).from().name();
      for (Flight flt : stops) {
	  rslt = rslt + "->" + flt.to().name(); }   
      return (rslt);
  }
    
}


////////////////////////  EOF Itinerary
