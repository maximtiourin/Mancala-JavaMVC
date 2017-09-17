import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * This is a board style that displays greenish colors for the board elements
 * <br>Date Created: 11/22/2012
 * <br>Date Updated: 11/22/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
public class Jungle implements BoardStyle 
{
	private static final String NAME = "Jungle";
	private static final Color COLOR_BACKGROUND = new Color(63, 69, 60);
	private static final Color COLOR_BOARD = new Color(85, 114, 71);
	private static final Color COLOR_BOARD2 = new Color(57, 77, 48);
	private static final Color COLOR_BOARD3 = new Color(45, 60, 37);
	private static final Color COLOR_PIT = new Color(32, 34, 31);
	private static final Color COLOR_STONE = new Color(182, 203, 172);
	private static final Color COLOR_LABELSCORE = new Color(182, 203, 172);
	private static final Color COLOR_LABELPLAYER = new Color(32, 34, 31);
	private static final Color COLOR_LABELSTATUS = new Color(32, 34, 31);
	private static final Color COLOR_HIGHLIGHT = new Color(163, 179, 156);
	private static final Color COLOR_BUTTON_BORDER = new Color(32, 34, 31);
	private static final Color COLOR_BUTTON_BACKGROUND = new Color(85, 114, 71);
	private static final Color COLOR_BUTTON_FOREGROUND = new Color(182, 203, 172);
	private static final String FONT_TITLE_FAMILY = Font.SANS_SERIF;
	private static final int FONT_TITLE_WEIGHT = Font.BOLD;
	private static final int FONT_TITLE_SIZE = 48;
	private static final String FONT_PLAYERLABEL_FAMILY = Font.SANS_SERIF;
	private static final int FONT_PLAYERLABEL_WEIGHT = Font.BOLD;
	private static final int FONT_PLAYERLABEL_SIZE = 14;
	private static final String FONT_STONEAMOUNT_FAMILY = Font.SANS_SERIF;
	private static final int FONT_STONEAMOUNT_WEIGHT = Font.BOLD;
	private static final int FONT_STONEAMOUNT_SIZE = 32;
	private static final String FONT_SCORE_FAMILY = Font.SANS_SERIF;
	private static final int FONT_SCORE_WEIGHT = Font.BOLD;
	private static final int FONT_SCORE_SIZE = 16;
	private static final int ROUNDX = 25; //How much to round the horizontal corners for round rects in this style
	private static final int ROUNDY = 25; //How much to round the vertical corners for round rects in this style
	
	@Override
	public void drawPitHighlight(Graphics g, int w, int h)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		//Draw Pit Highlight		
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));
		
		g2.setColor(getHighlightColor());
		g2.drawRoundRect(0, 0, w, h, ROUNDX, ROUNDY); //Draw initial white selection
		
		g2.setStroke(oldStroke);
	}
	
	@Override
	public void drawPitStoneAmountString(Graphics g, String amount, int centerx, int centery)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		Font font = new Font(FONT_STONEAMOUNT_FAMILY, FONT_STONEAMOUNT_WEIGHT, FONT_STONEAMOUNT_SIZE);
		FontMetrics metrics = g2.getFontMetrics(font);

		int fwidth = metrics.stringWidth(amount);
		int fheight = metrics.getHeight();
		
		//Draw Shadows with using a color thats darker than pit		
		int shadowOffset = 3;
		
		g2.setColor(getPitColor().darker());
		g2.setFont(font);
		
		g2.drawString(amount, centerx - (fwidth / 2) + shadowOffset, centery + (fheight / 4) + shadowOffset);
		g2.drawString(amount, centerx - (fwidth / 2) + Math.max(shadowOffset - 1, 0), centery + (fheight / 4) + Math.max(shadowOffset - 1, 0));
		g2.drawString(amount, centerx - (fwidth / 2) + Math.max(shadowOffset - 2, 0), centery + (fheight / 4) + Math.max(shadowOffset - 2, 0));
		//Draw normal amount with normal
		
		g2.setColor(getStoneColor());
		g2.setFont(font);
		
		g2.drawString(amount, centerx - (fwidth / 2), centery + (fheight / 4));
	}
	
	@Override
	public void drawBoardMancalaScoreString(Graphics g, String score, int centerx, int centery, boolean addition)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		Font font = new Font(FONT_SCORE_FAMILY, FONT_SCORE_WEIGHT, FONT_SCORE_SIZE);
		FontMetrics metrics = g2.getFontMetrics(font);

		g2.setFont(font);
		
		int vOffset; //vertical offset
		
		int fwidth = metrics.stringWidth(score);
		int fheight = metrics.getHeight();
		
		/* Determine positioning */
		if (addition)
		{
			vOffset = fheight;
		}
		else
		{
			vOffset = -(fheight / 2);
		}
		
		//Draw shadows using a color darker than pit
		int shadowOffset = 2;
		g2.setColor(getPitColor().darker());
		g2.drawString(score, centerx - (fwidth / 2) + shadowOffset, centery + vOffset + shadowOffset);
		//Draw label using label color
		g2.setColor(getLabelScoreColor());
		g2.drawString(score, centerx - (fwidth / 2), centery + vOffset);
	}
	
	@Override
	public void drawBoardPlayerLabel(Graphics g, String label, int x, int y, boolean selected, boolean addition)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		Font font = new Font(FONT_PLAYERLABEL_FAMILY, FONT_PLAYERLABEL_WEIGHT, FONT_PLAYERLABEL_SIZE);
		FontMetrics metrics = g2.getFontMetrics(font);

		g2.setFont(font);
		
		//Draw label
		int hOffset; //horizontal offset
		int vOffset; //vertical offset
		int vhOffset; //vertical height offset
		int sOffset = 5; //selection offset
		
		int fwidth = metrics.stringWidth(label) - 2;
		int fheight = metrics.getHeight();
		
		/* Determine positioning */
		if (addition)
		{
			hOffset = 2;
			vOffset = (fheight / 2);
			vhOffset = 0;
		}
		else
		{
			hOffset = -fwidth  + 2;
			vOffset = -(fheight / 2);
			vhOffset = -fheight;
		}
		
		if (selected)
		{
			g2.setColor(getBoardColor());
			g2.fillRoundRect(x - sOffset + hOffset, y - sOffset + vhOffset, fwidth + (2 * sOffset), (fheight / 2) + (2 * sOffset), ROUNDX, ROUNDY);
			g2.setColor(getLabelPlayerColor());
			g2.drawString(label, x + hOffset, y + vOffset);
			g2.drawRoundRect(x - sOffset + hOffset, y - sOffset + vhOffset, fwidth + (2 * sOffset), (fheight / 2) + (2 * sOffset), ROUNDX, ROUNDY);
			g2.drawRoundRect(x - sOffset + hOffset, y - sOffset + vhOffset - 2, fwidth + (2 * sOffset), (fheight / 2) + (2 * sOffset) + 4, ROUNDX, ROUNDY);
			g2.drawRoundRect(x - sOffset + hOffset, y - sOffset + vhOffset - 3, fwidth + (2 * sOffset), (fheight / 2) + (2 * sOffset) + 6, ROUNDX, ROUNDY);
			g2.drawRoundRect(x - sOffset + hOffset - 2, y - sOffset + vhOffset, fwidth + (2 * sOffset) + 4, (fheight / 2) + (2 * sOffset), ROUNDX, ROUNDY);
			g2.drawRoundRect(x - sOffset + hOffset - 3, y - sOffset + vhOffset, fwidth + (2 * sOffset) + 6, (fheight / 2) + (2 * sOffset), ROUNDX, ROUNDY);
		}
		else
		{
			g2.setColor(getLabelPlayerColor());
			g2.drawString(label, x + hOffset, y + vOffset);
		}
	}
	
	@Override
	public void drawBoardTitleString(Graphics g, String title, int centerx, int centery)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		Font font = new Font(FONT_TITLE_FAMILY, FONT_TITLE_WEIGHT, FONT_TITLE_SIZE);
		FontMetrics metrics = g2.getFontMetrics(font);
		int fwidth = metrics.stringWidth(title);
		int fheight = metrics.getHeight();
		
		//Draw Title Outlines
		int offset = 2; //outline offsets
		g2.setColor(getBoardColor().darker());
		g2.setFont(font);
		
		g2.drawString(title, centerx - (fwidth / 2) - offset, centery + (fheight / 4) - offset);
		g2.drawString(title, centerx - (fwidth / 2) - offset, centery + (fheight / 4) + offset);
		g2.drawString(title, centerx - (fwidth / 2) + offset, centery + (fheight / 4) - offset);
		g2.drawString(title, centerx - (fwidth / 2) + offset, centery + (fheight / 4) + offset);
		
		offset = 1; //outline offsets
		g2.setColor(getLabelStatusColor().brighter().brighter().brighter());
		g2.setFont(font);
		
		g2.drawString(title, centerx - (fwidth / 2) - offset, centery + (fheight / 4) - offset);
		g2.drawString(title, centerx - (fwidth / 2) - offset, centery + (fheight / 4) + offset);
		g2.drawString(title, centerx - (fwidth / 2) + offset, centery + (fheight / 4) - offset);
		g2.drawString(title, centerx - (fwidth / 2) + offset, centery + (fheight / 4) + offset);
		//Draw Title
		g2.setColor(getLabelStatusColor());
		g2.setFont(font);
		g2.drawString(title, centerx - (fwidth / 2), centery + (fheight / 4));
	}
	
	@Override
	public void drawStone(Graphics g, int x, int y, int w, int h)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		//Draw Stone Pit Shadow 
		int shadowOffset = 1;
		g2.setColor(getPitColor().darker());
		g2.fillOval(x + (3 * shadowOffset), y + (3 * shadowOffset), w / 2, h);
		//Draw Stone Shadow
		g2.setColor(getStoneColor().darker());
		g2.fillOval(x + shadowOffset, y + shadowOffset, w / 2, h);
		//Draw Stone
		g2.setColor(getStoneColor());
		g2.fillOval(x, y, w / 2, h);
		//Draw Stone Highlight
		int highlightOffset = 1;
		g2.setColor(getStoneColor().darker().darker());
		g2.fillOval(x + w - highlightOffset - (w / 3), y + highlightOffset, w / 3 / 2, h / 3);
	}
	
	@Override
	public void drawPit(Graphics g, int w, int h)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		//Draw Pit inner shadow
		int shadowOffset = 5;
		g2.setColor(getPitColor().darker());
		g2.fillRoundRect(0, 0, w, h, ROUNDX, ROUNDY);
		//Draw Pit
		g2.setColor(getPitColor());
		g2.fillRoundRect(0 + shadowOffset, 0 + shadowOffset, w - shadowOffset, h - shadowOffset, ROUNDX, ROUNDY);
		//Draw Pit Edge
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));
		g2.setColor(getPitColor().brighter());
		g2.drawRoundRect(0, 0, w, h, ROUNDX, ROUNDY);
		g2.setStroke(oldStroke);
	}
	
	@Override
	public void drawBoard(Graphics g, int w, int h)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		//Draw Board shadow
		int shadowOffset = 6;
		g2.setColor(getBoardColor().darker());
		g2.fillRoundRect(shadowOffset, shadowOffset, w, h, ROUNDX, ROUNDY);
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));
		g2.setColor(getBoardColor().darker().darker());
		g2.drawRoundRect(shadowOffset, shadowOffset, w - 2, h - 2, ROUNDX, ROUNDY);
		g2.setColor(getBoardColor().darker().darker().darker());
		g2.drawRoundRect(shadowOffset, shadowOffset, w, h, ROUNDX, ROUNDY);
		g2.setStroke(oldStroke);
		//Draw Board
		g2.setColor(getBoardColor());
		g2.fillRoundRect(0, 0, w, h, ROUNDX, ROUNDY);
		g2.setColor(COLOR_BOARD2);
		g2.fillRoundRect(25, 25, w - 50, h - 50, ROUNDX, ROUNDY);
		g2.setColor(COLOR_BOARD3);
		g2.fillOval(135, (h / 2) - 8, w - 270, h - (h / 2) - 80);
		//Draw Board Edge
		oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));
		g2.setColor(getBoardColor().brighter());
		g2.drawRoundRect(0, 0, w, h, ROUNDX, ROUNDY);
		g2.setStroke(oldStroke);
	}
	
	@Override
	public Border createButtonDefaultBorder()
	{
		return BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, getButtonBackgroundColor(), getButtonBackgroundColor().darker());
		//return BorderFactory.createLineBorder(getButtonBorderColor(), 2); Old Default Border
	}
	
	@Override
	public Border createButtonHighlightBorder()
	{
		return BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(getButtonBorderColor(), 1), BorderFactory.createLineBorder(getHighlightColor(), 2));
	}
	
	@Override
	public String getName() 
	{
		return NAME;
	}

	@Override
	public Color getBackgroundColor() 
	{
		return COLOR_BACKGROUND;
	}

	@Override
	public Color getBoardColor() 
	{
		return COLOR_BOARD;
	}

	@Override
	public Color getPitColor() 
	{
		return COLOR_PIT;
	}

	@Override
	public Color getStoneColor() 
	{
		return COLOR_STONE;
	}

	@Override
	public Color getLabelScoreColor()
	{
		return COLOR_LABELSCORE;
	}
	
	@Override
	public Color getLabelPlayerColor()
	{
		return COLOR_LABELPLAYER;
	}
	
	@Override
	public Color getLabelStatusColor()
	{
		return COLOR_LABELSTATUS;
	}
	
	@Override
	public Color getHighlightColor() 
	{
		return COLOR_HIGHLIGHT;
	}

	@Override
	public Color getButtonBorderColor() 
	{
		return COLOR_BUTTON_BORDER;
	}
	
	@Override
	public Color getButtonBackgroundColor() 
	{
		return COLOR_BUTTON_BACKGROUND;
	}

	@Override
	public Color getButtonForegroundColor() 
	{
		return COLOR_BUTTON_FOREGROUND;
	}
}
