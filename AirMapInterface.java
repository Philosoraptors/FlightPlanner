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
  
     
  JButton fromButton = new JButton("Departure");
  JButton toButton = new JButton("Destination");
  //JTextField text = new JTextField(5);
 // JLabel label = new JLabel("name"); 
  
  public AirMapInterface() { 
     setPreferredSize(new Dimension(550, 500));
     this.add(fromButton);
     fromButton.addActionListener(this);
     this.add(toButton);
     toButton.addActionListener(this);
  }
  
 public void actionPerformed(ActionEvent e){
   //if (e.getSource() == fromButton)
   //repaint();
   
  }
 
     
  public static void TravelMap(){
      JFrame frame = new JFrame("Travel Map");
      frame.setVisible(true);
      Container cnt = frame.getContentPane();
      AirMapInterface pnl = new AirMapInterface();
      cnt.add(pnl);
      frame.pack();
     
  }
      
     public void paintComponent(Graphics g) {
      super.paintComponent(g);

}
     
     public static void main (String[] args){
       
      TravelMap();
       
     }
}

