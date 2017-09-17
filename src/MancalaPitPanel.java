import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * The mancala pit button is in charge of drawing the pit for the mancala board,
 * as well as the correct number of stones inside of it.
 * <br>Date Created: 11/08/2012
 * <br>Date Updated: 11/14/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
@SuppressWarnings("serial")
public class MancalaPitPanel extends JPanel 
{
	/**
	 * Width of a Mancala Pit
	 */
	public static final int PIT_MANCALA_WIDTH = 50; //50
	/**
	 * Height of a Mancala Pit
	 */
	public static final int PIT_MANCALA_HEIGHT = 180; //180
	/**
	 * Width of a Normal pit
	 */
	public static final int PIT_NORMAL_WIDTH = 50; //50
	/**
	 * Height of a Normal pit
	 */
	public static final int PIT_NORMAL_HEIGHT = 50; //50
	/**
	 * Width of a Stone in the pit
	 */
	public static final int STONE_WIDTH = 8;
	/**
	 * Height of a Stone in the pit
	 */
	public static final int STONE_HEIGHT = 8;
	private MancalaFrame frame;
	private int stones; //amount of stones in pit
	private int player; //owner of pit
	private boolean isMancala; //if this pit is a mancala
	private boolean isHighlighted; //if this pit is currently highlighted
	private int x;
	private int y;
	
	public MancalaPitPanel(MancalaFrame frame)
	{
		this.frame = frame;
		stones = 0;
		player = MancalaModel.PLAYER_NONE;
		isMancala = false;
		isHighlighted = false;
		x = 0;
		y = 0;
	}
	
	/**
	 * Sets the amount of stones to display in this panel
	 * @param stones amount of stones
	 */
	public void setStones(int stones)
	{
		this.stones = stones;
	}
	
	/**
	 * Sets the owner of this panel
	 * @param player the index of the player
	 */
	public void setPlayer(int player)
	{
		this.player = player;
	}
	
	/**
	 * Sets whether or not this panel is a mancala pit
	 * @param isMancala true if this panel is a mancala pit
	 */
	public void setMancala(boolean isMancala)
	{
		this.isMancala = isMancala;
	}
	
	/**
	 * Sets whether or not this pit is currently highlighted by a valid player
	 */
	public void setHighlighted(boolean isHighlighted)
	{
		this.isHighlighted = isHighlighted;
	}
	
	/**
	 * Returns the x position of the pit panel
	 * @return int x position
	 */
	public int getX() 
	{
		return x;
	}
	
	/**
	 * Sets the x position of the pit panel
	 * @param x position
	 */
	public void setX(int x) 
	{
		this.x = x;
	}
	
	/**
	 * Returns the y position of the pit panel
	 * @return int y position
	 */
	public int getY() 
	{
		return y;
	}
	
	/**
	 * Sets the y position of the pit panel
	 * @param y position
	 */
	public void setY(int y) 
	{
		this.y = y;
	}
	
	/**
	 * Returns the owner of this pit panel
	 * @return int owner
	 */
	public int getPlayer()
	{
		return player;
	}

	@Override
	public void paint(Graphics g)
	{			
		Graphics2D g2 = (Graphics2D) g;
		
		if (isMancala)
		{
			//Draw Mancala Pit
			frame.getCurrentStyle().drawPit(g2, PIT_MANCALA_WIDTH, PIT_MANCALA_HEIGHT);
			
			//Draw Numerical amount of stones in mancala pit based on owner (SCORE)
			int voffset = -2;
			
			if (player == MancalaModel.PLAYER_B)
			{
				//Display score at top
				frame.getCurrentStyle().drawBoardMancalaScoreString(g2, stones + "", (PIT_MANCALA_WIDTH / 2), voffset, true);
			}
			else if (player == MancalaModel.PLAYER_A)
			{
				//Display score at bottom
				frame.getCurrentStyle().drawBoardMancalaScoreString(g2, stones + "", (PIT_MANCALA_WIDTH / 2), PIT_MANCALA_HEIGHT - voffset, false);
			}
			
			//Draw stones with Mancala pit layout
			StoneLayout layout = new PitMancalaLayout();
			for (Point e : layout.getLayout(stones, PIT_MANCALA_WIDTH, PIT_MANCALA_HEIGHT, STONE_WIDTH, STONE_HEIGHT))
			{
				frame.getCurrentStyle().drawStone(g2, (int) e.getX(), (int) e.getY(), STONE_WIDTH, STONE_HEIGHT);
			}
		}
		else
		{
			//Draw Normal Pit
			frame.getCurrentStyle().drawPit(g2, PIT_NORMAL_WIDTH, PIT_NORMAL_HEIGHT);
			
			//Draw stones with Normal pit layout if stones are <= 10, otherwise just draw the number of stones
			if (stones <= PitNormalLayout.MAXSTONES)
			{
				//Draw Stones
				StoneLayout layout = new PitNormalLayout();
				for (Point e : layout.getLayout(stones, PIT_NORMAL_WIDTH, PIT_NORMAL_HEIGHT, STONE_WIDTH, STONE_HEIGHT))
				{
					frame.getCurrentStyle().drawStone(g2, (int) e.getX(), (int) e.getY(), STONE_WIDTH, STONE_HEIGHT);
				}
			}
			else
			{
				//Draw Amount of stones instead
				frame.getCurrentStyle().drawPitStoneAmountString(g2, stones + "", (PIT_NORMAL_WIDTH / 2), (PIT_NORMAL_HEIGHT / 2));
			}
			
			//Draw highlight
			if (isHighlighted)
			{
				frame.getCurrentStyle().drawPitHighlight(g2, PIT_NORMAL_WIDTH, PIT_NORMAL_HEIGHT);
			}
		}
	}
}
