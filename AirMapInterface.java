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

import java.util.List;

public class AirMapInterface extends JPanel implements ActionListener {

  private Airport to, from = null;
  private AirMap funMap;

  public JPanel buttonPanel = new JPanel(); //make the panel for the buttons

  public JButton fromButton = new JButton("Departure");
  public JButton toButton = new JButton("Destination");
  public JButton routeButton = new JButton("Route");
  public JButton quitButton = new JButton("Quit");

  private JTextField fromLabel = new JTextField(3);
  private JTextField toLabel = new JTextField(3);
  private JLabel outputLabel = new JLabel("");

  public AirMapInterface() {
      super();

      funMap = new AirMap(800, 500, 30);
      funMap.setAlignmentX(Component.LEFT_ALIGNMENT);
      funMap.setBorder(BorderFactory.createTitledBorder("Airports"));
      this.add(funMap);

      Box gui = new Box(BoxLayout.PAGE_AXIS);
      gui.setAlignmentX(Component.RIGHT_ALIGNMENT);
      gui.setPreferredSize(new Dimension(300, 500));

      Box buttons = new Box(BoxLayout.PAGE_AXIS);
      buttons.setMaximumSize(new Dimension(300, 400));

      Box fromBox = new Box(BoxLayout.LINE_AXIS);
      fromBox.add(fromButton);
      fromBox.add(fromLabel);
      fromLabel.setEditable(false);
      buttons.add(fromBox);

      Box toBox = new Box(BoxLayout.LINE_AXIS);
      toBox.add(toButton);
      toBox.add(toLabel);
      toLabel.setEditable(false);
      buttons.add(toBox);

      Box buttonBox = new Box(BoxLayout.LINE_AXIS);
      buttonBox.add(routeButton);
      buttonBox.add(quitButton);

      buttons.add(buttonBox);

      buttons.setBorder(BorderFactory.createTitledBorder("Find Route"));
      buttons.setAlignmentY(Component.TOP_ALIGNMENT);

      gui.add(buttons);

      JPanel outputBox = new JPanel();
      outputBox.setPreferredSize(new Dimension(300, 200));
      outputBox.setBorder(BorderFactory.createTitledBorder("Cheapest Route"));
      outputBox.add(outputLabel);
      outputBox.setAlignmentY(Component.BOTTOM_ALIGNMENT);

      gui.add(Box.createVerticalGlue());
      gui.add(outputBox);

      this.add(gui);

      fromButton.addActionListener(this);
      toButton.addActionListener(this);
      quitButton.addActionListener(this);
      routeButton.addActionListener(this);
  }

   public void actionPerformed(ActionEvent e){
     if (e.getSource() == quitButton) {
       System.exit(0);
     } else if (e.getSource() == fromButton) {
       from = funMap.getSelected();
       fromLabel.setText(from.name());
     } else if (e.getSource() == toButton) {
       to = funMap.getSelected();
       toLabel.setText(to.name());
     } else if (e.getSource() == routeButton) {
       if (from != null && to != null) {
         List<Flight> route = Airport.findCheapestRoute(from, to);
         funMap.setRoute(route);
         funMap.repaint();

         from = null;
         to = null;
         fromLabel.setText("");
         toLabel.setText("");

         String output = "<html>";
         float cost = 0;

         for (Flight f : route) {
          output += f.toStringFriendly() + "<br>";
          cost += f.cost();
         }
         output += "<b>Total: $" + cost + "</b>";
         output += "</html>";
         outputLabel.setText(output);
       }
     }

     repaint();
  }
     
  public void TravelMap(){
      JFrame frame = new JFrame("Route Planner");
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // create new AirMap and display it
      frame.addMouseListener(funMap);
      frame.getContentPane().add(this);

      frame.pack();
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
