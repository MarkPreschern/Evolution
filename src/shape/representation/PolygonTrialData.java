package shape.representation;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Represents information on a list of all polygons in a trial, for a list of trials in an evolution
 * iteration.
 */
public class PolygonTrialData implements PolygonTrialDataModel {

  private Image leaderBoard;
  private Image graph;

  /**
   * Default constructor initializes the leader board and graph.
   */
  public PolygonTrialData() {
    this.updateLeaderBoardOfTrial();
    this.updateGraphOfTrial();
  }

  //represents relevant information about every polygon in a trial
  private ArrayList<String[][]> data = new ArrayList<>();

  @Override
  public Image getLeaderBoard() {
    return this.leaderBoard;
  }

  @Override
  public Image getGraph() {
    return this.graph;
  }

  @Override
  public void addTrialData(String[][] trial) {
    this.data.add(trial);
  }

  @Override
  public void updateLeaderBoardOfTrial() {
    BufferedImage image = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();

    //The layout of the leader board
    Graphics2D leaderBoard = (Graphics2D) g;
    leaderBoard.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    leaderBoard.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    leaderBoard.setColor(Color.black);
    leaderBoard.drawLine(800, 50, 1280, 50);
    leaderBoard.drawLine(850, 0, 850, 400);
    leaderBoard.drawLine(1050, 0, 1050, 400);

    //The default text of the leader board
    Font header = new Font("Verdana", Font.BOLD, 40);
    leaderBoard.setFont(header);
    int hashHeaderWidth = g.getFontMetrics(header).stringWidth("#");
    int nameHeaderWidth = g.getFontMetrics(header).stringWidth("NAME");
    int massHeaderWidth = g.getFontMetrics(header).stringWidth("DIST");
    leaderBoard.drawString("#", 825 - hashHeaderWidth / 2, 40);
    leaderBoard.drawString("NAME", 950 - nameHeaderWidth / 2, 40);
    leaderBoard.drawString("DIST", 1165 - massHeaderWidth / 2, 40);

    //The data displayed on the leader board
    if (this.data.size() != 0) {
      int length = 10;
      if (this.data.get(0).length < 10) {
        length = this.data.get(0).length;
      }
      for (int i = 0; i < length; i++) {
        Font lists = new Font("Verdana", Font.PLAIN, 30);
        leaderBoard.setFont(lists);
        int numWidth = g.getFontMetrics(lists).stringWidth(Integer.toString(i + 1));
        leaderBoard.drawString(Integer.toString(i + 1), 825 - numWidth / 2, 80 + 35 * i);
        leaderBoard.drawString(this.data.get(this.data.size() - 1)[i][1], 920, 80 + 35 * i);
        leaderBoard.drawString(this.data.get(this.data.size() - 1)[i][0], 1135, 80 + 35 * i);
      }
    }

    this.leaderBoard = image;
  }

  @Override
  public void updateGraphOfTrial() {
    BufferedImage image = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graph = image.createGraphics();

    int evolution = this.data.size();

    //The layout of the graph
    graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graph.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    graph.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graph.setColor(Color.black);
    graph.drawLine(850, 630, 1200, 630);
    graph.drawLine(850, 450, 850, 630);

    //The default text of the graph
    Font gen = new Font("Verdana", Font.BOLD, 30);
    graph.setFont(gen);
    String gens = "Generation: " + (evolution + 1);
    int gensWidth = graph.getFontMetrics(gen).stringWidth(gens);
    graph.drawString(gens, 1040 - gensWidth / 2, 680);

    Font num = new Font("Verdana", Font.PLAIN, 10);
    graph.setFont(num);
    int sizeWidth = graph.getFontMetrics(num).stringWidth("Size");
    int genWidth = graph.getFontMetrics(num).stringWidth("Generation");
    graph.rotate(Math.toRadians(-90), 845, 540 + sizeWidth / 2);
    graph.drawString("Size", 845, 540 + sizeWidth / 2);
    graph.rotate(Math.toRadians(90), 845, 540 + sizeWidth / 2);
    graph.drawString("Generation", 1025 - genWidth / 2, 645);

    //The data displayed on the graph
    if (this.data.size() != 0) {
      int[] highestHeights = this.allMaxes();
      int[] meanHeights = this.allMeans();
      int low = this.localMin(meanHeights);
      int high = this.localMax(highestHeights);

      String smallest = Integer.toString(low);
      String largest = Integer.toString(high);
      int lowWidth = graph.getFontMetrics(num).stringWidth(smallest);
      int highWidth = graph.getFontMetrics(num).stringWidth(largest);

      if (this.data.size() > 1 && low != high) {
        graph.drawString(smallest, 830 - lowWidth / 2, 625);
      }
      graph.drawString(largest, 830 - highWidth / 2, 460);

      if (this.data.size() > 1)
        graph.drawString("1", 855, 645);
      String lg = Integer.toString((evolution));
      int lgw = graph.getFontMetrics(num).stringWidth(lg);
      graph.drawString(lg, 1200 - lgw / 2, 645);
      graph.setColor(Color.red);
      if (this.data.size() == 1) {
        graph.fill(new Ellipse2D.Double(1200, 460, 2, 2));
      } else if (this.data.size() > 1) {
        double[][] cords = new double[evolution][2];
        double[][] cords2 = new double[evolution][2];
        for (int i = 0; i < evolution; i++) {
          double width = 855 + (345 / (evolution - 1)) * i;
          double height;
          double height2;
          if (low == high) {
            height = 625;
            height2 = 625;
          } else {
            height = 625 - (165 * ((double) (highestHeights[i] - low) / (double) (high - low)));
            height2 = 625 - (165 * ((double) (meanHeights[i] - low) / (double) (high - low)));
          }
          cords[i][0] = width;
          cords[i][1] = height;
          cords2[i][0] = width;
          cords2[i][1] = height2;
        }

        for (int i = 0; i < evolution - 1; i++) {
          graph.setColor(Color.black);
          graph.drawLine((int) cords[i][0] + 1, (int) cords[i][1] + 1, (int) cords[i + 1][0] + 1, (int) cords[i + 1][1] + 1);
          graph.drawLine((int) cords2[i][0] + 1, (int) cords2[i][1] + 1, (int) cords2[i + 1][0] + 1, (int) cords2[i + 1][1] + 1);
          graph.setColor(Color.red);
          graph.fill(new Ellipse2D.Double((int) cords[i][0], cords[i][1], 2, 2));
          graph.fill(new Ellipse2D.Double((int) cords2[i][0], cords2[i][1], 2, 2));
          if (i == evolution - 2) {
            graph.fill(new Ellipse2D.Double((int) cords[i + 1][0], cords[i + 1][1], 2, 2));
            graph.fill(new Ellipse2D.Double((int) cords2[i + 1][0], cords2[i + 1][1], 2, 2));

            graph.drawString("Max Height", (int) cords[i + 1][0] + 5, (int) cords[i + 1][1]);
            graph.drawString("Mean Height", (int) cords2[i + 1][0] + 5, (int) cords2[i + 1][1]);
          }
        }
      }
    }

    this.graph = image;
  }

  /**
   * Gets a list of the the local max distances of every trial.
   *
   * @return a list of max's
   */
  private int[] allMaxes() {
    int[] high = new int[this.data.size()];
    for (int i = 0; i < this.data.size(); i++) {
      high[i] = getMax(i);
    }
    return high;
  }

  /**
   * Gets a list of the the local mean distances of every trial.
   *
   * @return a list of means
   */
  private int[] allMeans() {
    int[] mean = new int[this.data.size()];
    for (int i = 0; i < this.data.size(); i++) {
      mean[i] = getMean(i);
    }
    return mean;
  }

  /**
   * Gets the maximum element at the index.
   *
   * @param loc the index
   * @return the maximum element
   */
  private int getMax(int loc)
  //gets the highest distance in the database
  {
    int highest = 0;
    for (int i = 0; i < this.data.get(loc).length; i++) {
      if ((int) Double.parseDouble(this.data.get(loc)[i][0]) > highest)
        highest = (int) Double.parseDouble(this.data.get(loc)[i][0]);
    }
    return highest;
  }

  @Override
  public int getMean(int loc) throws IllegalArgumentException {
    int sum = 0;
    for (int i = 0; i < this.data.get(loc).length / 2; i++)
      sum += (int) Double.parseDouble(this.data.get(loc)[i][0]);
    if (sum != 0)
      return sum / (this.data.get(loc).length / 2);
    return 0;
  }

  /**
   * Gets the maximum distance of a list
   *
   * @param high the list of highs
   * @return the local max
   */
  private int localMax(int[] high) {
    int highest = 0;
    for (int aHigh : high) {
      if (aHigh > highest)
        highest = aHigh;
    }
    return highest;
  }

  /**
   * Gets the minimum element in the list.
   *
   * @return the minimum element
   */
  private int localMin(int[] low) {
    int lowest = 99999999;
    for (int aLow : low) {
      if (aLow < lowest)
        lowest = aLow;
    }
    return lowest;
  }
}
