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
  public JButton showallButton = new JButton("Show all");

  private JTextField fromLabel = new JTextField(3);
  private JTextField toLabel = new JTextField(3);
  private JLabel outputLabel = new JLabel("");

  public AirMapInterface() {
      super();

      // this essentially sets up the whole view,
      // by adding components to "this" - a JPanel.
      // The JFrame is set up later.

      // Add an airmap
      funMap = new AirMap(800, 500, 30);                                // create map with width 800, height 500, padding 30
      funMap.setAlignmentX(Component.LEFT_ALIGNMENT);                   // align to left
      funMap.setBorder(BorderFactory.createTitledBorder("Airports"));   // give it a named border
      this.add(funMap);

      Box gui = new Box(BoxLayout.PAGE_AXIS);                           // create a container (a "Box"), to right side of the page
                                                                        // BoxLayout.PAGE_AXIS makes it a vertical layout
      gui.setAlignmentX(Component.RIGHT_ALIGNMENT);                     // align to right
      gui.setPreferredSize(new Dimension(300, 500));                    // set prefferred size

      Box buttons = new Box(BoxLayout.PAGE_AXIS);                       // create a container for the buttons
      buttons.setBorder(BorderFactory.createTitledBorder("Find Route"));  // give it a named border
      buttons.setAlignmentY(Component.TOP_ALIGNMENT);                   // align to top

      Box fromBox = new Box(BoxLayout.LINE_AXIS);                       // LINE_AXIS creates a horizontal container
      fromBox.add(fromButton);                                          // add from button
      fromBox.add(fromLabel);                                           // add the from text field
      fromLabel.setEditable(false);                                     // turn off user editing on the text field
      buttons.add(fromBox);                                             // add to buttons

      Box toBox = new Box(BoxLayout.LINE_AXIS);                         // do the same for the to button
      toBox.add(toButton);                                              // add to button
      toBox.add(toLabel);                                               // add text field
      toLabel.setEditable(false);                                       // turn off editing
      buttons.add(toBox);                                               // add to buttons

      Box buttonBox = new Box(BoxLayout.LINE_AXIS);                     // create another horizontal container for the misc buttons
      buttonBox.add(routeButton);                                       // add route button
      buttonBox.add(showallButton);                                     // add show all button
      buttonBox.add(quitButton);                                        // add quit button

      buttons.add(buttonBox);                                           // add horizontal container to buttons


      gui.add(buttons);                                                 // add buttons to the gui

      JPanel outputBox = new JPanel();                                  // create a JPanel for outputBox, because JPanel works better for sizing for some reason
      outputBox.setPreferredSize(new Dimension(300, 500));              // expand to fill available space
      outputBox.setBorder(BorderFactory.createEtchedBorder());          // add border
      outputBox.add(outputLabel);                                       // add output label
      outputBox.setAlignmentY(Component.BOTTOM_ALIGNMENT);              // align to bottom

      gui.add(outputBox);                                               // add outputBox to gui

      this.add(gui);                                                    // add gui to this

      // add action listeners
      fromButton.addActionListener(this);
      toButton.addActionListener(this);
      quitButton.addActionListener(this);
      routeButton.addActionListener(this);
      showallButton.addActionListener(this);
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
     } else if (e.getSource() == showallButton) {
        AirMap.toggleDrawAll();
     } else if (e.getSource() == routeButton) {
       if (from != null && to != null) {
         // calculate route
         List<Flight> route = Airport.findCheapestRoute(from, to);
         funMap.setRoute(route);
         funMap.repaint();

         // string together output (note that output uses HTML for line breaks and formatting)
         String output = "<html>";
         float cost = 0;

         for (Flight f : route) {
          output += f.toStringFriendly() + "<br>";
          cost += f.cost();
         }
         output += "<b>Total: $" + cost + "</b>";
         output += "</html>";
         outputLabel.setText(output);

         // reset to and from
         from = null;
         to = null;
         fromLabel.setText("");
         toLabel.setText("");
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
