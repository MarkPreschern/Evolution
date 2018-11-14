package shape.representation;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * A similar version to the java polygon implementation, with fields relevant to the evolution
 * simulation. I choose not to extend Polygon, as points must be represented as a double for the
 * purpose of this simulation.
 */
public class Polygon implements PolygonModel {

  public double[] xPoints;
  public double[] yPoints;
  public int nPoints;
  private Point2D.Double velocity;

  private double rotationalVelocity;

  private final String name;
  private final Color color;

  private double distanceTraveled;
  private int evolution;
  private boolean visible;

  private final ArrayList<Point2D.Double> initialCoordinates;

  /**
   * constructs a polygon using the polygon builder
   *
   * @param b the polygon builder
   */
  public Polygon(PolygonBuilder b) {
    this.xPoints = b.xPoints;
    this.yPoints = b.yPoints;
    this.nPoints = b.nPoints;
    this.velocity = b.velocity;

    this.rotationalVelocity = b.rotationalVelocity;

    this.name = b.name;
    this.color = b.color;

    this.distanceTraveled = b.distanceTraveled;
    this.evolution = b.evolution;
    this.visible = b.visible;

    this.initialCoordinates = this.setInitialCoordinates();
  }

  /**
   * Initializes the fields of this polygon to that of the given polygon.
   *
   * @param p the given polygon
   */
  public Polygon(Polygon p) {
    this.xPoints = p.xPoints;
    this.yPoints = p.yPoints;
    this.nPoints = p.nPoints;
    this.velocity = p.velocity;

    this.rotationalVelocity = p.rotationalVelocity;

    this.name = p.name;
    this.color = p.color;

    this.distanceTraveled = p.distanceTraveled;
    this.evolution = p.evolution;
    this.visible = p.visible;

    this.initialCoordinates = this.setInitialCoordinates();
  }

  /**
   * Initializes the initial coordinates to the x and y values in point form
   *
   * @return a list of points representing the initial coordinates of this shape
   */
  private ArrayList<Point2D.Double> setInitialCoordinates() {
    ArrayList<Point2D.Double> points = new ArrayList<>();
    for (int i = 0; i < this.xPoints.length; i++) {
      points.add(new Point2D.Double(this.xPoints[i], this.yPoints[i]));
    }
    return points;
  }

  /**
   * Calculates the area of the polygon.
   *
   * @return the area
   */
  private double calculateArea() {
    double area = 0.0;

    int j = this.nPoints - 1;
    for (int i = 0; i < this.nPoints; i++) {
      area += (this.xPoints[j] + this.xPoints[i]) * (this.yPoints[j] - this.yPoints[i]);
      j = i;
    }
    return Math.abs(area / 2.0);
  }

  /**
   * Calculates the center point of the polygon.
   *
   * @return the center
   */
  private Point2D.Double calculateCenter() {
    double x = 0.0;
    double y = 0.0;
    for (int i = 0; i < this.nPoints; i++) {
      x += this.xPoints[i];
      y += this.yPoints[i];
    }

    x = x / this.nPoints;
    y = y / this.nPoints;

    return new Point2D.Double(x, y);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public int getEvolution() {
    return this.evolution;
  }

  @Override
  public Point2D.Double getVelocity() {
    return this.velocity;
  }

  @Override
  public ArrayList<Point2D.Double> getInitialCoordinates() {
    return this.initialCoordinates;
  }

  @Override
  public double getDistance() {
    return this.distanceTraveled;
  }

  @Override
  public boolean isVisible() {
    return this.visible;
  }

  @Override
  public void move() {
    for (int i = 0; i < this.nPoints; i++) {
      this.xPoints[i] += this.velocity.x;
      this.yPoints[i] += this.velocity.y;
    }
  }

  @Override
  public void rotate() {
    Point2D.Double center = this.calculateCenter();

    for (int i = 0; i < this.nPoints; i++) {
      double x1 = this.xPoints[i] - center.x;
      double y1 = this.yPoints[i] - center.y;

      //applies rotation via a rotation matrix
      x1 = x1 * Math.cos(this.rotationalVelocity) - y1 * Math.sin(this.rotationalVelocity);
      y1 = x1 * Math.sin(this.rotationalVelocity) + y1 * Math.cos(this.rotationalVelocity);

      this.xPoints[i] = x1 + center.x;
      this.yPoints[i] = y1 + center.y;
    }
  }

  @Override
  public void adjustVelocity(Point2D.Double change) {
    this.velocity.x += change.getX();
    this.velocity.y += change.getY();
  }

  @Override
  public void setPoints(double[] x, double[] y) {
    this.xPoints = x;
    this.yPoints = y;
  }

  @Override
  public void setPoints(ArrayList<Point2D.Double> points) {
    double[] x = new double[points.size()];
    double[] y = new double[points.size()];
    for (int i = 0; i < points.size(); i++) {
      x[i] = points.get(i).x;
      y[i] = points.get(i).y;
    }
    this.xPoints = x;
    this.yPoints = y;
  }

  @Override
  public void setVelocity(Point2D.Double vel) {
    this.velocity = new Point2D.Double(vel.getX(), vel.getY());
  }

  @Override
  public void setDistance(double distance) {
    this.distanceTraveled = distance;
  }

  @Override
  public void updateCollision(Path2D.Double path) {
    for (int i = 0; i < this.nPoints; i++) {
      if (path.contains(this.xPoints[i], this.yPoints[i])) {
        //updates linear collisions
        this.updateLinearCollisionVelocity(this.getSlope(this.xPoints[i]));
      }
    }
  }

  /**
   * Updates the velocity based on a collision at a point of a given slope. Takes into effect a 10%
   * decrease in magnitude of both the x and y vectors of the velocity due to an assumed friction
   * with the ground.
   *
   * @param slope slope at a point
   */
  private void updateLinearCollisionVelocity(double slope) {
    double vTot = Math.sqrt((this.velocity.x * this.velocity.x)
            + (this.velocity.y * this.velocity.y));
    double angleObj;
    try {
      angleObj = Math.toDegrees(Math.atan(this.velocity.y / this.velocity.x));
    } catch (ArithmeticException e) {
      angleObj = 0;
    }
    double angleSlope = Math.toDegrees(Math.atan(slope));
    double angleOfIncidence = angleSlope - angleObj;
    double angleOfReflection = 180 - angleOfIncidence;
    double vX = vTot * Math.cos(Math.toRadians(angleOfReflection));
    double vY = -Math.abs(vTot * Math.sin(Math.toRadians(angleOfReflection)));

    this.rotationalVelocity = 0.00002 * this.calculateArea() * (this.rotationalVelocity
            + (vX - this.velocity.x));

    this.setVelocity(new Point2D.Double(vX, vY));
  }

  /**
   * Represents the slope of the ground's uppermost surface at an x point
   *
   * @param x point
   * @return the slope of the ground at the point
   */
  private double getSlope(double x)
  //returns the instantaneous angle of ground at x
  {
    x -= 182;
    if (x >= 0)
      return .014 * x;
    else
      return (0.01508 * x) - (0.00009 * x * x);
  }

  @Override
  public void updateVisibility(double height) {
    if (this.isVisible()) {
      for (int i = 0; i < this.nPoints; i++) {
        if (this.yPoints[i] >= height) {
          this.setDistance(this.xPoints[i]);
          this.visible = false;
        }
      }
    }
  }

  @Override
  public Path2D.Double getPath() {
    Path2D.Double path = new Path2D.Double();
    path.moveTo(this.xPoints[0], this.yPoints[0]);
    for (int j = 1; j < this.nPoints; j++) {
      path.lineTo(this.xPoints[j], this.yPoints[j]);
    }
    path.closePath();

    return path;
  }
}