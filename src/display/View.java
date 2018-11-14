package display;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;

import shape.creation.Evolve;
import shape.representation.PolygonBuilder;
import shape.representation.Polygon;
import shape.representation.PolygonTrialData;

public class View extends JPanel implements ActionListener {

  private JFrame frame;

  private ArrayList<Polygon> polygons;
  private Path2D.Double ground;
  private PolygonTrialData trialData;
  private Results results;

  private int evolutionDepth;
  private int trials;
  private long start;

  /**
   * Constructor takes in a list of extended polygons and the depth & trials for the simulation.
   */
  public View(int evolutionDepth, int trials) {
    this.polygons = this.createPolygons();
    this.trialData = new PolygonTrialData();
    this.results = new Results();
    this.evolutionDepth = evolutionDepth;
    this.trials = trials;

    //creates the ground
    this.ground = new Path2D.Double();
    this.ground.moveTo(0, 150);
    this.ground.quadTo(100, 800, 300, 500);
    this.ground.lineTo(300, 700);
    this.ground.lineTo(0, 700);
    this.ground.closePath();
  }

  /**
   * Begins the simulation of polygons by initializing a JFrame and starting a counter.
   */
  public void runSimulation() {
    this.frame = new JFrame();
    this.frame.add(this);
    this.frame.setSize(1280, 720);
    this.frame.setVisible(true);
    this.frame.setResizable(false);
    this.frame.setLocationRelativeTo(null);
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.start = System.currentTimeMillis();
    Timer t = new Timer(0, this);
    t.setInitialDelay(1000);
    t.start();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.repaint();

    for (int i = 0; i < 100; i++) { //polygon calculations occur 10 times every repaint
      for (Polygon p : this.polygons) {
        p.move(); //updates position
        p.adjustVelocity(new Point2D.Double(0.00000000001, 0.00001)); //Updates velocity with respect to gravity
        p.rotate();
        p.updateCollision(this.ground); //checks for collisions between a polygon and ground
        p.updateVisibility(700); //updates polygon's visibility
      }
      this.checkEndOfTrial();
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    //the background, leader board, and graph
    this.drawBackground(g);
    g.drawImage(this.trialData.getLeaderBoard(), 0, 0, null);
    g.drawImage(this.trialData.getGraph(), 0, 0, null);

    //the polygons
    Graphics2D main = (Graphics2D) g;
    main.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    for (Polygon polygon : polygons) {
      if (polygon.isVisible()) {
        Path2D.Double path = polygon.getPath();

        main.setColor(polygon.getColor());
        main.fill(path);
        main.setColor(Color.black);
        main.draw(path);
      }
    }
  }

  /**
   * Draws the background images.
   *
   * @param g the graphics component
   */
  private void drawBackground(Graphics g) {
    //The ground
    Graphics2D layout = (Graphics2D) g;
    layout.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    layout.setColor(Color.lightGray);
    layout.fill(ground);
    layout.setColor(Color.black);
    layout.draw(ground);

    //The general layout of the gui
    layout.setColor(Color.lightGray);
    layout.fillRect(getWidth() - 480, 0, getWidth(), getHeight());
    layout.setColor(Color.black);
    layout.drawLine(getWidth() - 480, 0, getWidth() - 480, getHeight());
    layout.drawLine(getWidth() - 479, 0, getWidth() - 479, getHeight());
    layout.fillRect(0, 0, 1280, 2);
    layout.fillRect(0, getHeight() - 2, 1280, 2);
    layout.fillRect(0, 0, 2, getHeight());
    layout.fillRect(1278, 0, 2, getHeight());

    //depth & trial output
    Font header = new Font("Verdana", Font.BOLD, 10);
    layout.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    layout.setFont(header);
    String depth = "Depth: " + this.evolutionDepth;
    String trial = "Trials: " + (this.trials - this.polygons.get(0).getEvolution() + 1);
    int depthWidth = g.getFontMetrics(header).stringWidth(depth);
    layout.drawString(depth, 10, 15);
    layout.drawString(trial, 10 + depthWidth + 10, 15);
  }

  /**
   * Ends the trial when all polygons aren't visible or time has exceeded max time.
   */
  private void checkEndOfTrial() {
    boolean noneVisible = true;
    for (Polygon p : this.polygons) {
      if (p.isVisible()) {
        noneVisible = false;
      }
    }

    if (noneVisible || (System.currentTimeMillis() - this.start) / 1000 >= 10) {
      //sets visible polygon's distance and resets their original points
      for (Polygon p : this.polygons) {
        if (p.isVisible()) {
          p.setDistance(p.xPoints[0]);
        }
        p.setPoints(p.getInitialCoordinates());
      }
      this.newGeneration();
    }
  }

  /**
   * Makes a new generation of polygons's and adds relevant information to be displayed.
   */
  private void newGeneration() {
    //sorts the polygons by their distance in descending order
    this.polygons.sort((p1, p2) -> (int) (p2.getDistance() - p1.getDistance()));

    //adds information to trial data
    String[][] str = new String[polygons.size()][2];
    for (int i = 0; i < this.polygons.size(); i++) {
      str[i][0] = Integer.toString((int) this.polygons.get(i).getDistance());
      str[i][1] = this.polygons.get(i).getName();
    }
    this.trialData.addTrialData(str);
    this.trialData.updateLeaderBoardOfTrial();
    this.trialData.updateGraphOfTrial();

    //when an evolution iteration is over
    if ((this.trials - this.polygons.get(0).getEvolution()) == 0) {
      this.evolutionDepth--;
      this.results.addPolygonData(this.polygons.get(0), this.trialData.getMean(this.trials - 1));
      this.polygons = this.createPolygons();
      this.trialData = new PolygonTrialData();

      //if the simulation is over
      if (this.evolutionDepth == 0) {
        this.frame.remove(this);
        this.frame.add(this.results);
        this.frame.validate();
        this.frame.repaint();
      }
    } else {
      Evolve e = new Evolve(this.polygons);
      this.polygons = e.getEvolvedPolygons();
    }
    start = System.currentTimeMillis();
  }

  //creates 20 random polygons
  private ArrayList<Polygon> createPolygons() {
    ArrayList<Polygon> polygons = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      polygons.add(new Polygon(new PolygonBuilder().build()));
    }
    return polygons;
  }
}
