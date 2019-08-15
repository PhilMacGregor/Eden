package cz.macgregor.eden;

import cz.macgregor.eden.core.GameHandler;
import cz.macgregor.eden.util.filecrawler.FileCrawler;

/**
 * main class for the application.
 * 
 * @author MacGregor
 *
 */
public class Eden {

  /**
   * main method. Initializes the game handler and starts it.
   * 
   * @param args
   *          args
   */
  public static void main(String[] args) {
    try {
      FileCrawler.getInstance();
      GameHandler game = new GameHandler();
      game.start();
    } catch (Throwable t) {
      t.printStackTrace();
      System.exit(1);
    }

  }

}
