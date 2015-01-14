
/**
 * Who wrote this anyway and what does it do?
 */

import java.awt.*;
import javax.swing.*;

public class AirMap extends JPanel{ 

  public static void showMap(){
    JFrame frame = new JFrame("flight map");
    AirMap map = new AirMap();
    map.setPreferredSize(new Dimension(500,500));
    frame.getContentPane().add(map);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    frame.pack();
    frame.setVisible(true);
  }
  
  public void paintComponent(Graphics gr){
    super.paintComponent(gr);
    gr.drawString("hi", 50, 100);
    gr.drawString("low", 100, 200);
    gr.drawLine(50,100, 100, 200);
  }
}
