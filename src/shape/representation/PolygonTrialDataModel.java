package shape.representation;

import java.awt.*;

/**
 * Represents a model for the a list of polygon data's functionality.
 */
public interface PolygonTrialDataModel {

  /**
   * Adds information from a trial.
   *
   * @param trial information from a trial
   */
  void addTrialData(String[][] trial);

  /**
   * Updates a graphical representation of the polygon data from an evolution iteration.
   */
  void updateGraphOfTrial();

  /**
   * Updates a leader board representing the polygons data from an evolution iteration.
   *
   */
  void updateLeaderBoardOfTrial();

  /**
   * Gets the mean distance of the better half of a list at an index. Invariant: The local mean can
   * never decrease from one trial to the next, since an evolution of polygons holds that the top
   * half a trial remains the same.
   *
   * @param loc the index
   * @return the local mean
   * @throws IllegalArgumentException when the list is empty
   */
  int getMean(int loc) throws IllegalArgumentException;

  /**
   * Gets a leader board representing the polygons data from an evolution iteration.
   *
   * @return a leader board
   */
  Image getLeaderBoard();

  /**
   * Gets a graphical representation of the polygon data from an evolution iteration.
   *
   * @return a graph
   */
  Image getGraph();
}
