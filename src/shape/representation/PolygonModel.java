package shape.representation;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Represents a model for the an extended polygon's functionality.
 */
public interface PolygonModel {

  /**
   * @return polygon's name.
   */
  String getName();

  /**
   * @return polygon's color.
   */
  Color getColor();

  /**
   * @return the evolution iteration.
   */
  int getEvolution();

  /**
   * @return the velocity represented as a point.
   */
  Point2D.Double getVelocity();

  /**
   * @return the initial coordinates.
   */
  ArrayList<Point2D.Double> getInitialCoordinates();

  /**
   * @return the distance this polygon has traveled in a trial.
   */
  double getDistance();

  /**
   * @return the visibility of this polygon.
   */
  boolean isVisible();

  /**
   * Moves a polygon based on it's velocity.
   */
  void move();

  /**
   * Rotates a polygon based on it's rotational velocity.
   */
  void rotate();

  /**
   * Changes the velocity by a value.
   *
   * @param change the value to change velocity by
   */
  void adjustVelocity(Point2D.Double change);

  /**
   * Sets the x and y points of this polygon to x an y.
   *
   * @param x coordinates
   * @param y coordinates
   */
  void setPoints(double[] x, double[] y);

  /**
   * Sets the x and y points of this polygon to the points given.
   *
   * @param points new points
   */
  void setPoints(ArrayList<Point2D.Double> points);

  /**
   * Sets the velocity to the given velocity
   *
   * @param vel new velocity
   */
  void setVelocity(Point2D.Double vel);

  /**
   * Sets distance
   *
   * @param distance new distance
   */
  void setDistance(double distance);

  /**
   * Updates the velocity if a collision with some path2D occurs.
   *
   * @param path the path
   */
  void updateCollision(Path2D.Double path);

  /**
   * Updates the visibility by some height and sets the distance to the x value below the height
   * only if the visibility of the polygon is true.
   *
   * @param height height
   */
  void updateVisibility(double height);

  /**
   * represents a path outlining the points of the polygon.
   *
   * @return a path
   */
  Path2D.Double getPath();
}
