package shape.creation;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Generates information required for the extended polygon
 */
public class PolygonGenerator implements GeneratorModel {

  private String name;
  private Color color;
  private double[] xPoints;
  private double[] yPoints;

  /**
   * default constructor
   */
  public PolygonGenerator() {
    this.name = this.getRandomName();
    this.color = this.getRandomColor();
    this.generateShape();
  }

  @Override
  public double[] getX() {
    return this.xPoints;
  }

  @Override
  public double[] getY() {
    return this.yPoints;
  }

  @Override
  public int getSize() {
    return this.xPoints.length;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Color getColor() {
    return this.color;
  }


  /**
   * Generates a random string of length 4.
   *
   * @return a random string of length 4.
   */
  private String getRandomName() {
    Random rand = new Random();
    String Upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String Lower = "abcdefghijklmnopqrstuvwxyz";
    return Character.toString(Upper.charAt(rand.nextInt(Upper.length())))
            + Character.toString(Lower.charAt(rand.nextInt(Lower.length())))
            + Character.toString(Lower.charAt(rand.nextInt(Lower.length())))
            + Integer.toString(rand.nextInt(10));
  }

  /**
   * Generates a random rgb color.
   *
   * @return a random color.
   */
  private Color getRandomColor() {
    Random rand = new Random();
    return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
  }

  /**
   * Generates a polygon with no intersections and a random number of nodes.
   *
   * @return A geometric representation of a polygon.
   */
  private void generateShape() {
    Random rand = new Random();
    int nodes = rand.nextInt(18) + 3;

    //creates sorted random angles
    ArrayList<Double> angle = new ArrayList<>();
    for (int i = 0; i < nodes; i++) {
      angle.add(Math.random() * 2 * Math.PI);
    }

    angle.sort((d1, d2) -> {
      if (d1 < d2) return -1;
      if (d1 > d2) return 1;
      return 0;
    });

    double[] x = new double[nodes];
    double[] y = new double[nodes];
    for (int i = 0; i < nodes; i++) {
      double length = 5 + Math.random() * 20;
      x[i] = Math.cos(angle.get(i)) * length + 25;
      y[i] = Math.sin(angle.get(i)) * length + 125;
    }

    this.xPoints = x;
    this.yPoints = y;
  }
}
