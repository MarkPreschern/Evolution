package main;

import java.util.ArrayList;

import display.View;
import shape.representation.Polygon;
import shape.representation.PolygonBuilder;

/**
 * Creates a new evolution simulation of polygons.
 */
public class CreateSimulation {

  /**
   * The main method allows the user to set depth and trials and creates a new simulation.
   *
   * @param args for evolution depth and trials per depth
   */
  public static void main(String[] args) {
    int depth = 10;
    int trials = 10;

    boolean first = true;
    for (String s : args) {
      try {
        int val = Integer.parseInt(s);
        if (val > 0) {
          if (first) {
            depth = val;
            first = false;
          } else {
            trials = val;
          }
        }
      } catch (NumberFormatException ignore) {}
    }

    View view = new View(depth, trials);
    view.runSimulation();
  }
}
