/**
 * AirMapInterface
 *
 * Contains buttons/text boxes to interact with AirMap.  The idea
 * is to have two panels within our frame, this being one of them,
 * the AirMap being the other.  -Noah
 * 
 * Made the buttons to click --Michelle
 **/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AirMapInterface extends JPanel implements ActionListener {
  
  private Airport to, from;

  public static final int TO = 1;
  public static final int FROM = 2;
  public int mode;
  
  public AirMapInterface() { 
      setPreferredSize(new Dimension(600, 600));
    //TravelMap();
     
  }
  
 public void actionPerformed(ActionEvent e){
   //if (e.getSource() == from)
   repaint();
   
  }
     
  public static void TravelMap(){
     
      JFrame frame = new JFrame("Travel Map");
      frame.setVisible(true);
      frame.setSize(600, 600);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      JPanel buttonPanel = new JPanel(new GridBagLayout()); //make the panel for the buttons
      JPanel mapPanel = new JPanel(); // make the map panel
      
      mapPanel.setPreferredSize(new Dimension(500, 500));
      
      JButton fromButton = new JButton("Departure");
      JButton toButton = new JButton("Destination");
      
      GridBagConstraints layout = new GridBagConstraints();
      
      layout.gridx = 0;
      layout.gridy = 1;
      buttonPanel.add(fromButton, layout);
      //fromButton.addActionListener(this);
      layout.gridx = 0;
      layout.gridy = 2;
      buttonPanel.add(toButton, layout);
     
      //toButton.addActionListener(this);

      // create new AirMap and display it
      AirMap funMap = new AirMap(500,500,30);
      
      mapPanel.add(funMap);
      
      frame.add(buttonPanel, BorderLayout.EAST); // put the button panel on the right
      frame.add(mapPanel, BorderLayout.WEST); // put the map panel on the left
      
      
     
  }
      
     public void paintComponent(Graphics g) {
      super.paintComponent(g);
 

}
     
     public static void main (String[] args){
      
// load airports and flights
      Airport.loadAirports();
      Flight.loadFlights();
      
      TravelMap();
       
     }
}

