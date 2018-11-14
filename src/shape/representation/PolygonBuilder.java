package shape.representation;

import java.awt.*;
import java.awt.geom.Point2D;

import shape.creation.PolygonGenerator;

/**
 * Represents a build pattern for PolygonExtended
 */
public class PolygonBuilder {

  double[] xPoints;
  double[] yPoints;
  int nPoints;
  Point2D.Double velocity;
  double rotationalVelocity;

  String name;
  Color color;

  double distanceTraveled;
  int evolution;
  boolean visible;

  /**
   * default constructor initializes values to default settings.
   */
  public PolygonBuilder() {
    PolygonGenerator pg = new PolygonGenerator();

    this.xPoints = pg.getX();
    this.yPoints = pg.getY();
    this.nPoints = pg.getSize();
    this.velocity = new Point2D.Double(0, 0);
    this.rotationalVelocity = 0;

    this.name = pg.getName();
    this.color = pg.getColor();

    this.distanceTraveled = 0;
    this.evolution = 1;
    this.visible = true;
  }

  public PolygonBuilder build() {
    return this;
  }

  public PolygonBuilder setXPoints(double[] xPoints) {
    this.xPoints = xPoints;
    return this;
  }

  public PolygonBuilder setYPoints(double[] yPoints) {
    this.yPoints = yPoints;
    return this;
  }

  public PolygonBuilder setNPoints(int nPoints) {
    this.nPoints = nPoints;
    return this;
  }

  public PolygonBuilder setVelocity(Point2D.Double velocity) {
    this.velocity = velocity;
    return this;
  }

  public PolygonBuilder setRotationalVelocity(double rotationalVelocity) {
    this.rotationalVelocity = rotationalVelocity;
    return this;
  }

  public PolygonBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public PolygonBuilder setColor(Color color) {
    this.color = color;
    return this;
  }

  public PolygonBuilder setDistanceTraveled(double distanceTraveled) {
    this.distanceTraveled = distanceTraveled;
    return this;
  }

  public PolygonBuilder setEvolution(int evolution) {
    this.evolution = evolution;
    return this;
  }

  public PolygonBuilder setVisible(boolean visible) {
    this.visible = visible;
    return this;
  }
}
