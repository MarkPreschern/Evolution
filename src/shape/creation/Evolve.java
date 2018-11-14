package shape.creation;

import java.util.ArrayList;
import java.util.Random;

import shape.representation.PolygonBuilder;
import shape.representation.Polygon;

/**
 * Evolves the polygons in such a way that the best half of the polygons are unchanged and the worse
 * half are mutated based on the better half.
 */
public class Evolve {

  private ArrayList<Polygon> evolvedPolygons = new ArrayList<>();

  /**
   * default constructor takes in a list of polygons prior to being evolved.
   */
  public Evolve(ArrayList<Polygon> old) {
    evolve(old);
  }

  /**
   * returns the evolved polygons
   */
  public ArrayList<Polygon> getEvolvedPolygons() {
    return evolvedPolygons;
  }

  /**
   * Determines which polygons to evolve.
   */
  private void evolve(ArrayList<Polygon> old) {
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < old.size() / 2; j++) {
        create(old.get(j), i);
      }
    }
  }

  /**
   * Mutates and adds an evolved polygon to the list of evolved polygons
   */
  private void create(Polygon old, int nameNumber) {
    Random rand = new Random();
    int nodes = old.nPoints;

    double[] x = new double[nodes];
    double[] y = new double[nodes];
    for (int i = 0; i < nodes; i++) {
      int tempX;
      int tempY;
      if (nameNumber == 0) {
        tempX = (int) old.xPoints[i];
        tempY = (int) old.yPoints[i];
      } else {
        tempX = (int) old.xPoints[i] + rand.nextInt(3);
        tempY = (int) old.yPoints[i] - 2 + rand.nextInt(5);
      }
      x[i] = tempX;
      y[i] = tempY;
    }

    this.evolvedPolygons.add(new Polygon(new PolygonBuilder()
            .setXPoints(x).setYPoints(y).setNPoints(x.length).setName(old.getName())
            .setColor(old.getColor()).setEvolution(old.getEvolution() + 1).build()));
  }
}
