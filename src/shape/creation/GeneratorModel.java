package shape.creation;

import java.awt.*;

/**
 * Represents a model for the generation of a random extended polygon.
 */
public interface GeneratorModel {

  /**
   * represents the x coordinates of the points in a polygon
   *
   * @return x coordinates
   */
  double[] getX();

  /**
   * represents the y coordinates of the points in a polygon
   *
   * @return y coordinates
   */
  double[] getY();

  /**
   * represents the number of nodes (points) in a polygon
   *
   * @return number of nodes
   */
  int getSize();

  /**
   * represents the randomly generated name
   *
   * @return name
   */
  String getName();

  /**
   * represents a randomly generated color
   *
   * @return color
   */
  Color getColor();
}
