import java.util.*;
import java.io.*;

public class Flight {
    public int num;
    public Airport origin;
    public Airport dest;
    public int depart;
    public int arrive;
    public int seats;
    public float cost;
    public String airline;
    
    public Flight (int fltnum, String fltorigin, String fltdest, int takeoff, int land, float price, int seating, String company){
	num = fltnum;
	origin = Airport.named(fltorigin);
	dest = Airport.named(fltdest);
	depart = takeoff;
	arrive = land;
	cost =  price;
	seats = seating;
	airline = company;
	origin.addDeparture(this);
	//   System.out.println("loaded " + this);
    }
    
  public String toString(){
      return (num + " " + origin.name() + " " + dest.name() + " " + depart + " " + arrive + " " + cost + " " + seats + " " + airline);
  }
    
    public float cost() { return (cost); }
    
    public Airport from() { return (origin); }
    
    public Airport to() { return (dest); }
    
    ///////////////// static below ////////////////////////
    public static void loadFlights() {   
	try{
	    Scanner scn = new Scanner(new File("flights.csv")).useDelimiter(",\\s*");
	    scn.nextLine();
	    while (scn.hasNext()) {
		new Flight(scn.nextInt(), scn.next(), scn.next(), scn.nextInt(), scn.nextInt(), scn.nextFloat(), scn.nextInt(), scn.next());
	    }
	} catch (Exception ex){  ex.printStackTrace(); }
    }
    
}

/////////////// EOF Flight
