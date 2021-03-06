import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.List; // because both java.awt.List and java.util.List exist

public class AirMap extends JPanel implements MouseListener {
  public int width;
  public int height;
  private int padding;
  private boolean clickBool = false;
  private int proximity = 10;
  public static boolean drawAll = false;

  private Airport selected;

  private static float minLng, maxLng, minLat, maxLat;

  private List<Flight> route;

  // CONSTRUCTOR

  public AirMap(int wid, int hei, int pad) {
	  super();

	  width = wid;
	  height = hei;
	  padding = pad;

    setPreferredSize(new Dimension(width, height));

    selected = null;
    route = null;

	  // calculate minimum and maximum latitude/longitude
	  minLng = maxLng = Airport.Airports.get(0).lng();  // need to initialize variables
	  minLat = maxLat = Airport.Airports.get(0).lat();  // in order to compare them!

	  // cycle through airports
	  for (int i = 0; i < Airport.Airports.size(); i++) {
	    Airport airport = Airport.Airports.get(i);

	    // compare airport to previously stored values
	    if (airport.lat() > maxLat) maxLat = airport.lat();
	    if (airport.lat() < minLat) minLat = airport.lat();
	    if (airport.lng() > maxLng) maxLng = airport.lng();
	    if (airport.lng() < minLng) minLng = airport.lng();
	  }
  }


  // STATIC METHODS

  public void showMap(){
	  JFrame frame = new JFrame("flight map");
	  setPreferredSize(new Dimension(500, 500));
	  addMouseListener(this);
	  frame.getContentPane().add(this);
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.pack();
	  frame.setVisible(true);
  }

  // PUBLIC METHODS

  public Airport getSelected() {
    return selected;
  }

  // called when window needs to be drawn
  public void paintComponent(Graphics gr){
	  super.paintComponent(gr);

    // update width and height
	  width = getWidth();
	  height = getHeight();

    // cycle through airports first to place airport names and
    // look to see what airport has been selected (clicked).
	  for (Airport airport : Airport.Airports) {

	    // get coordinates
	    int y = getY(airport.lat());
	    int x = getX(airport.lng());

      if (airport == selected) {
        gr.setColor(Color.red);
      } else {
        gr.setColor(Color.black);
      }
	    gr.drawString(airport.name(), x, y);

      // draw route
      if (route != null) {
        drawRoute(gr);
      }
      if (drawAll == true) {
        drawAirportLines(gr);
      }

	    //Get the value of the airport selected, and draw the name of the airport
	    /*if ((y >= (ycord - proximity) && y <= (ycord + proximity)) && (x > (xcord - proximity * 3) && (x <= xcord))) {

		    //Store the airport clicked on
		    portClick = airport.name();

		    //Draw the text string the color of the flight lines to be a key
		    gr.setColor(Color.red);
		    gr.drawString("All departing flights from: " + portClick,(width / 2),(height / 10));
		    gr.setColor(Color.blue);
		    gr.drawString("All arriving flights from: " + portClick,(width / 2),(height / 10 + 30));
	   }*/
    }

	  // cycle through airports onc emore in order to draw lines
    //drawAirportLines(gr);

  }

  // MouseListener events
  public void mousePressed(MouseEvent e) {
    int mouseX = e.getX();
    int mouseY = e.getY();

    int minDistSq = Integer.MAX_VALUE;

    for (Airport a : Airport.Airports) {
      int portX = getX(a.lng());
      int portY = getY(a.lat());
      int distSq = (mouseX - portX) * (mouseX - portX) + (mouseY - portY) * (mouseY - portY);

      if (distSq < minDistSq) {
        selected = a;
        minDistSq = distSq;
      }
    }

	  repaint();
  }

  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}

  public void setRoute(List<Flight> r) {
    this.route = r;
  }

  // PRIVATE FUNCTIONS

  // calculates an x coordinate from longitude
  private int getX(float lng) {
	  float scale;
	  int xMin;
	  float latRange = maxLat - minLat;
	  float lngRange = maxLng - minLng;

    if (latRange > lngRange) {
	    scale = (height - padding * 2) / latRange;
	    xMin = (int)((width - lngRange * scale) / 2);
	  } else {
      scale = (width - padding * 2) / lngRange;
      xMin = padding;
    }

    int x = (int)((lng - minLng) * scale) + xMin;
    return x;
  }

  // calculates a y coordinate from a latitude
  private int getY(float lat) {
	  float scale;
	  int yMin;
	  float latRange = maxLat - minLat;
	  float lngRange = maxLng - minLng;

	  if (latRange > lngRange) {
	    scale = (height - padding * 2) / latRange;
	    yMin = padding;
	  } else {
	    scale = (width - padding * 2) / lngRange;
	    yMin = (int)((height - latRange * scale) / 2);
	  }

	  int y = -(int)((lat - minLat) * scale) + height - yMin;
	  return y;
  }

  //Draws lines
  private void drawAirportLines(Graphics gr) {

	  for (Airport airport : Airport.Airports) {

	    // get coordinates
	    int y = getY(airport.lat());
	    int x = getX(airport.lng());

	    // get flights
	    ArrayList<Flight> flights = airport.departures();

	    // cycle through flights from this airport
	    for (int h = 0; h < flights.size(); h++) {
		    Airport dest = flights.get(h).to();

        // compute destination coordinates
		    int x2 = getX(dest.lng());
		    int y2 = getY(dest.lat());

        if (clickBool == false){
			      gr.setColor(Color.green);
			      gr.drawLine(x, y, x2, y2);
		    }

    //This section is commented out until we want to reimplement the 'click on airport and show flights to/from that airport
/*
		    // draw a line to represent the flight departures
		    if (flights.get(h).from() == selected){
		      gr.setColor(Color.red);
		      gr.drawLine(x, y, x2, y2);
		      clickBool = true;
		    }
		    if (flights.get(h).to() == selected){
		      gr.setColor(Color.blue);
		      gr.drawLine(x, y, x2, y2);
		      clickBool = true;
		    }
		    else {
		      if (clickBool == false){
			      gr.setColor(Color.green);
			      gr.drawLine(x, y, x2, y2);
		      }
		    }
*/
	    }
	  }
  }

  private void drawRoute(Graphics gr) {
    for (Flight f : route) {
      drawLine(gr, f.from(), f.to());
    }
  }

  private void drawLine(Graphics gr, Airport from, Airport to) {
    int x1 = getX(from.lng());
    int y1 = getY(from.lat());

    int x2 = getX(to.lng());
    int y2 = getY(to.lat());

    gr.drawLine(x1, y1, x2, y2);
  }

  public static void toggleDrawAll() {
    if (drawAll == false) {
      drawAll = true;
    }
    else {
      drawAll = false;
    }
  }

}
