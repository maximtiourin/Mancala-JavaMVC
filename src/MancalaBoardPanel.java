import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * The Mancala board panel is in charge of drawing the Mancala board, as well as
 * all of it's contained pits.
 * <br>Date Created: 11/13/2012
 * <br>Date Updated: 11/14/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
@SuppressWarnings("serial")
public class MancalaBoardPanel extends JPanel {
	/**
	 * Width of the board
	 */
	public static final int BOARD_WIDTH = 490; //490
	/**
	 * Height of the board
	 */
	public static final int BOARD_HEIGHT = 200; //200
	private static final int BOARD_DRAW_PADDING = 100; //Just add some extra drawing room for BoardStyles to make things look nice
	private MancalaFrame frame;
	private ArrayList<MancalaPitPanel> pitPanels;
	private int currentTurn; //Current player turn
	
	public MancalaBoardPanel(MancalaFrame frame)
	{
		this.frame = frame;
		pitPanels = new ArrayList<MancalaPitPanel>();
		currentTurn = MancalaModel.PLAYER_NONE;
		
		this.setSize(BOARD_WIDTH + BOARD_DRAW_PADDING, BOARD_HEIGHT + BOARD_DRAW_PADDING);
	}
	
	/**
	 * Adds the Mancala Pit Panel to the list of panels to be drawn by the mancala board
	 */
	public void addPitPanel(MancalaPitPanel panel)
	{
		pitPanels.add(panel);
	}
	
	/**
	 * Sets the boards current turn status to the given player.
	 * @param player the player who's turn it now is
	 */
	public void setCurrentTurn(int player)
	{
		currentTurn = player;
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		//Draw Board
		frame.getCurrentStyle().drawBoard(g2, BOARD_WIDTH, BOARD_HEIGHT);
		
		//Draw Player labels
		int hOffset = 20;
		int vOffset = 20;
		
		frame.getCurrentStyle().drawBoardPlayerLabel(g2, "Player B", MancalaPitPanel.PIT_MANCALA_WIDTH + hOffset, MancalaPitPanel.PIT_NORMAL_HEIGHT + vOffset, currentTurn == MancalaModel.PLAYER_B, true);
		frame.getCurrentStyle().drawBoardPlayerLabel(g2, "Player A", BOARD_WIDTH - MancalaPitPanel.PIT_MANCALA_WIDTH - hOffset, BOARD_HEIGHT - MancalaPitPanel.PIT_NORMAL_HEIGHT - (vOffset / 2), currentTurn == MancalaModel.PLAYER_A, false);
		
		//Draw title Label
		frame.getCurrentStyle().drawBoardTitleString(g2, "Mancala", BOARD_WIDTH / 2, BOARD_HEIGHT / 2);
				
		//Draw pit panels
		for (MancalaPitPanel e : pitPanels)
		{
			AffineTransform oldTransform = g2.getTransform();
			g2.translate(e.getX(), e.getY());
			e.paint(g2);
			g2.setTransform(oldTransform);
		}
	}
}
