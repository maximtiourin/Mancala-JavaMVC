import java.awt.Color;
import java.awt.Graphics;

import javax.swing.border.Border;

/**
 * The board style interface defines a strategy for creating a unique
 * board style for the Mancala Board.
 * <br>Date Created: 11/07/2012
 * <br>Date Updated: 11/14/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
public interface BoardStyle {
	/**
	 * Draws a pit highlight to the graphics relative to 0,0, with the given 
	 * width and height.
	 * @param g Graphics context
	 * @param w width
	 * @param h height
	 */
	void drawPitHighlight(Graphics g, int w, int h);
	/**
	 * Draws a stone amount string at the center of x and y, for the normal pit.
	 * @param g Graphics context
	 * @param amount the stone amount string to draw
	 * @param centerx the center x position to draw at
	 * @param centery the center y position to draw at
	 */
	void drawPitStoneAmountString(Graphics g, String amount, int centerx, int centery);
	/**
	 * Draws a score string for the mancala pit at the center of x and y.
	 * @param g Graphics context
	 * @param score score string for pit
	 * @param centerx the center x coordinate to draw at
	 * @param centery the center y coordinate to draw at
	 * @param addition boolean if this label's dimension based positioning should be added to x and y, or subtracted from them.
	 */
	void drawBoardMancalaScoreString(Graphics g, String score, int centerx, int centery, boolean addition);
	/**
	 * Draws a player label for the board with the given string at x and y, and
	 * will also draw a graphic to signify that this player is currently
	 * selected if the selected flag is true.
	 * @param g Graphics context
	 * @param label the label of the player
	 * @param x the x coordinate to draw at
	 * @param y the y coordinate to draw at
	 * @param selected boolean if this label so also draw a player selection graphic.
	 * @param addition boolean if this label's dimension based positioning should be added to x and y, or subtracted from them.
	 */
	void drawBoardPlayerLabel(Graphics g, String label, int x, int y, boolean selected, boolean addition);
	/**
	 * Draws the board Title string in the center of the board.
	 * @param g Graphics context
	 * @param title the title of the board
	 * @param centerx the center x coordinate of the board
	 * @param centery the center y coordinate of the board
	 */
	void drawBoardTitleString(Graphics g, String title, int centerx, int centery);
	/**
	 * Draws a stone to the graphics context at the x and y position
	 * relative to 0,0, with the given width and height.
	 * @param g Graphics context
	 * @param x x position
	 * @param y y position
	 * @param w width
	 * @param h height
	 */
	void drawStone(Graphics g, int x, int y, int w, int h);
	/**
	 * Draws a pit to the graphics relative to 0,0, with the given 
	 * width and height.
	 * @param g Graphics context
	 * @param w width
	 * @param h height
	 */
	void drawPit(Graphics g, int w, int h);
	/**
	 * Draws a board to the graphics relative to 0,0, with the given 
	 * width and height.
	 * @param g Graphics context
	 * @param w width
	 * @param h height
	 */
	void drawBoard(Graphics g, int w, int h);
	/**
	 * Creates and returns the default button
	 * border for this style.
	 * @return Border the default border
	 */
	Border createButtonDefaultBorder();
	/**
	 * Creates and returns the highlighted button
	 * border for this style.
	 * @return Border the highlighted border
	 */
	Border createButtonHighlightBorder();
	/**
	 * Returns the name of the style.
	 */
	String getName();
	/**
	 * Returns the background color;
	 * @return Color background color;
	 */
	Color getBackgroundColor();
	/**
	 * Returns the board color;
	 * @return Color board color;
	 */
	Color getBoardColor();
	/**
	 * Returns the pit color;
	 * @return Color pit color;
	 */
	Color getPitColor();
	/**
	 * Returns the stone color;
	 * @return Color stone color;
	 */
	Color getStoneColor();
	/**
	 * Returns the label Score color;
	 * @return Color label score color;
	 */
	Color getLabelScoreColor();
	/**
	 * Returns the label Player color;
	 * @return Color label player color;
	 */
	Color getLabelPlayerColor();
	/**
	 * Returns the label Status color;
	 * @return Color label status color;
	 */
	Color getLabelStatusColor();
	/**
	 * Returns the highlight color;
	 * @return Color highlight color;
	 */
	Color getHighlightColor();
	/**
	 * Returns the button border color;
	 * @return Color button border color;
	 */
	Color getButtonBorderColor();
	/**
	 * Returns the button background color;
	 * @return Color button background color;
	 */
	Color getButtonBackgroundColor();
	/**
	 * Returns the button foreground color;
	 * @return Color button foreground color;
	 */
	Color getButtonForegroundColor();
}
