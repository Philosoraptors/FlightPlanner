/**
 * AirMapInterface
 *
 * Contains buttons/text boxes to interact with AirMap.  The idea
 * is to have two panels within our frame, this being one of them,
 * the AirMap being the other.  -Noah
 **/

import java.awt.*;
import javax.swing.*;

public class AirMapInterface extends JPanel {
  private JButton toButton, fromButton;
  private Airport to, from;

  public static final int TO = 1;
  public static final int FROM = 2;
  public int mode;

  public AirMapInterface() {
    toButton = new JButton("To");
    fromButton = new JButton("From");


}
