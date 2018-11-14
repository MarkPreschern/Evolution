package display;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import shape.representation.Polygon;

/**
 * Represents information relevant to an extended polygon's results
 */
public class Results extends JPanel {

  private ArrayList<Polygon> polygons = new ArrayList<>();
  private ArrayList<Integer> means = new ArrayList<>();

  /**
   * Takes in a polygon and the mean of the last trial of it's evolution iteration.
   */
  public void addPolygonData(Polygon polygon, int mean) {
    this.polygons.add(new Polygon(polygon));
    this.means.add(mean);
  }

  /**
   * Displays the graphics.
   *
   * @param g graphics object
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D main = (Graphics2D) g;
    main.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    for (int i = 0; i < this.polygons.size(); i++) {

      int[] xCords = new int[this.polygons.get(i).nPoints];
      int[] yCords = new int[this.polygons.get(i).nPoints];
      for (int j = 0; j < this.polygons.get(i).nPoints; j++) {
        xCords[j] = (int) this.polygons.get(i).xPoints[j] + (i % 10) * 120 + 20;
        yCords[j] = (int) this.polygons.get(i).yPoints[j] + (i / 10) * 120 + 50 - 100;
      }
      java.awt.Polygon poly = new java.awt.Polygon(xCords, yCords, this.polygons.get(i).nPoints);

      main.setColor(this.polygons.get(i).getColor());
      main.fill(poly);
      main.setColor(Color.black);
      main.draw(poly);

      String str1 = this.polygons.get(i).getName();
      String str2 = Integer.toString((int) this.polygons.get(i).getDistance());
      String str3 = Integer.toString(this.means.get(i));
      main.setColor(Color.black);
      Font font = new Font("Verdana", Font.BOLD, 15);
      main.setFont(font);
      main.drawString(str1, (i % 10) * 120 + 50, ((int) i / 10) * 120 + 40);
      main.drawString(str2, (i % 10) * 120 + 50, ((int) i / 10) * 120 + 120);
      main.drawString(str3, (i % 10) * 120 + 50, ((int) i / 10) * 120 + 135);
    }
  }
}
