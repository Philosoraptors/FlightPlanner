/**
 * AirMapInterface
 *
 * Contains buttons/text boxes to interact with AirMap.  The idea
 * is to have two panels within our frame, this being one of them,
 * the AirMap being the other.  -Noah
 * 
 * Made the buttons panel and added the AirMap to show next to it. --Michelle
 **/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AirMapInterface extends JPanel implements ActionListener {
  
  private Airport to, from;

  public static final int TO = 1;
  public static final int FROM = 2;
  public int mode;
  
  public JPanel buttonPanel = new JPanel(new GridBagLayout()); //make the panel for the buttons
      
      public JButton fromButton = new JButton("Departure");
      public JButton toButton = new JButton("Destination");
      public JButton quitButton = new JButton("Quit");
      
      
  
  public AirMapInterface() { 
     setPreferredSize(new Dimension(600, 600));
     this.add(fromButton);
     this.add(toButton);
     this.add(quitButton);     
     fromButton.addActionListener(this);
     toButton.addActionListener(this);
     quitButton.addActionListener(this);
      
      
     
  }
  
   public void actionPerformed(ActionEvent e){
     if (e.getSource() == quitButton)
       System.exit(0);
    
     //funMap.getSelected();
     //repaint();
   
  }
     
  public void TravelMap(){
     
      JFrame frame = new JFrame("Travel Map");
      frame.setVisible(true);
      frame.setSize(1000, 600);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      GridBagConstraints layout = new GridBagConstraints();
      
      layout.gridx = 0;
      layout.gridy = 1;
      buttonPanel.add(fromButton, layout);
     
      layout.gridx = 0;
      layout.gridy = 2;
      buttonPanel.add(toButton, layout);
      
      layout.gridx = 0;
      layout.gridy = 3;
      buttonPanel.add(quitButton, layout);
      
      
      // create new AirMap and display it
      AirMap funMap = new AirMap(700,500,30);
      funMap.setPreferredSize(new Dimension(800, 600));

      frame.addMouseListener(funMap);
      frame.add(buttonPanel, BorderLayout.EAST); // put the button panel on the right
      frame.add(funMap, BorderLayout.WEST); // put the map panel on the left
    
     
  }
      
     public void paintComponent(Graphics g) {
      super.paintComponent(g);
 

}
     
     public static void main (String[] args){
      
// load airports and flights
      Airport.loadAirports();
      Flight.loadFlights();
      AirMapInterface funInterface = new AirMapInterface();
      
      funInterface.TravelMap();
       
     }
}
