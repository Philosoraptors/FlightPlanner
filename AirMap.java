import java.awt.*;
import javax.swing.*;

public class AirMap extends JPanel{ 

  private static final int WIDTH = 500;
  private static final int HEIGHT = 500;
  private static final int PADDING = 30;

  private static float minLng, maxLng, minLat, maxLat;

  public static void showMap(){
    JFrame frame = new JFrame("flight map");
    AirMap map = new AirMap();
    map.setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
    int yMin, xMin;
    float latRange = maxLat - minLat;
    float lngRange = maxLng - minLng;
    if (latRange > lngRange) {
      scale = (getHeight() - PADDING * 2) / latRange;
      xMin = (int)((getWidth() - lngRange * scale) / 2);
      yMin = PADDING;
    } else {
      scale = (getWidth() - PADDING * 2) / lngRange;
      yMin = (int)((getHeight() - latRange * scale) / 2);
      xMin = PADDING;
    }
    
    for (int i = 0; i < Airport.Airports.size(); i++) {
      Airport airport = Airport.Airports.get(i);
      int y = -(int)((airport.lat() - minLat) * scale) + getHeight() - yMin;
      int x = (int)((airport.lng() - minLng) * scale) + xMin;
      gr.drawString(airport.name(), x, y);
    }
  }
}
