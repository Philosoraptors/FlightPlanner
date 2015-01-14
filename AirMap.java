import java.awt.*;
import javax.swing.*;

public class AirMap extends JPanel{ 

  private static final int WIDTH = 1000;
  private static final int HEIGHT = 1000;
  private static final int PADDING = 20;

  private static float minLng, maxLng, minLat, maxLat;

  public static void showMap(){
    JFrame frame = new JFrame("flight map");
    AirMap map = new AirMap();
    map.setPreferredSize(new Dimension(WIDTH,HEIGHT));
    frame.getContentPane().add(map);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    frame.pack();
    frame.setVisible(true);
    
    minLng = maxLng = Airport.Airports.get(0).lng();
    minLat = maxLat = Airport.Airports.get(0).lat();
    
    for (int i = 0; i < Airport.Airports.size(); i++) {
      Airport airport = Airport.Airports.get(i);
      if (airport.lat() > maxLat) maxLat = airport.lat();
      if (airport.lat() < minLat) minLat = airport.lat();
      if (airport.lng() > maxLng) maxLng = airport.lng();
      if (airport.lng() < minLng) minLng = airport.lng();
    }
  }
  
  public void paintComponent(Graphics gr){
    super.paintComponent(gr);
    
    float scale;
    float latRange = maxLat - minLat;
    float lngRange = maxLng - minLng;
    if (latRange > lngRange) {
      scale = (WIDTH - PADDING * 2) / latRange;
    } else {
      scale = (HEIGHT - PADDING * 2) / lngRange;
    }
    
    for (int i = 0; i < Airport.Airports.size(); i++) {
      Airport airport = Airport.Airports.get(i);
      int y = HEIGHT - PADDING - (int)((airport.lat() - minLat) * scale);
      int x = (int)((airport.lng() - minLng) * scale) + PADDING;
      gr.drawString(airport.name(), x, y);
      
      System.out.println(airport.name());
      System.out.println("X: " + x + ", Y: " + y);
      System.out.println("LAT: " + airport.lat() + ", LNG: " + airport.lng());
    }
  }
}
