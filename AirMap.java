import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AirMap extends JPanel implements MouseListener {

  public int width;
  public int height;
  private int padding;
  private int xcord;
  private int ycord;
  private boolean clickBool = false;
  private String portClick = "";
  private int proximity = 10;

  private static float minLng, maxLng, minLat, maxLat;

  // CONSTRUCTOR
  
  public AirMap(int wid, int hei, int pad) {
    super();
    
    width = wid;
    height = hei;
    padding = pad;
    
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
      
      gr.setColor(Color.black);
      gr.drawString(airport.name(), x, y);
      
      //Get the value of the airport selected, and draw the name of the airport
      if ((y >= (ycord - proximity) && y <= (ycord + proximity)) && (x > (xcord - proximity * 3) && (x <= xcord))){        

        //Store the airport clicked on
        portClick = airport.name();

        //Draw the text string the color of the flight lines to be a key
        gr.setColor(Color.red);
        gr.drawString("All departing flights from: " + portClick,(width / 2),(height / 10));
        gr.setColor(Color.blue);
        gr.drawString("All arriving flights from: " + portClick,(width / 2),(height / 10 + 30));
        }
    }

    // cycle through airports onc emore in order to draw lines
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
        
        // draw a line to represent the flight departures
        if (flights.get(h).from().name().equals(portClick)){
          gr.setColor(Color.red);
          gr.drawLine(x, y, x2, y2);
          clickBool = true;  
        }
        if (flights.get(h).to().name().equals(portClick)){
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
      }
    }
  }

  // MouseListener events
  public void mousePressed(MouseEvent e) {
  xcord = e.getX();
  ycord = e.getY(); 
  repaint();
}
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}
  
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
}
