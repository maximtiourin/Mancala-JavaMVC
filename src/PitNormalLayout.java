import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The Normal Pit Stone Layout is a stone layout for a Normal Pit.
 * <br>Date Created: 11/13/2012
 * <br>Date Updated: 11/13/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
public class PitNormalLayout implements StoneLayout {
	/**
	 * Maximum amount of stones this layout is capable of displaying
	 */
	public static final int MAXSTONES = 10;
	
	@Override
	public List<Point> getLayout(int stoneAmount, int pitWidth, int pitHeight, int stoneWidth, int stoneHeight) {
		ArrayList<Point> layout = new ArrayList<Point>();
		
		if (stoneAmount <= 0)
		{
			//Return empty list for 0 stones
			return layout;
		}
			
		//Construct layout based on stone amount
		int w = pitWidth;
		int h = pitHeight;
		int sw = stoneWidth;
		int sh = stoneHeight;
		int so = -2; //Stone Individual Offset
		switch (stoneAmount)
		{
			case 1:
			{
				/*
				 *  o
				 */
				layout.add(new Point((w / 2) - (sw / 2), (h / 2) - (sh / 2)));
				break;
			}
			case 2:
			{
				/*
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2)));
				break;
			}
			case 3:
			{
				/*
				 *   o
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2), (h / 2) - (sh / 2) - sh - so));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + sh + so));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + sh + so));
				break;
			}
			case 4:
			{
				/*
				 *  o o
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) - sh - so));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) - sh - so));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + sh + so));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + sh + so));
				break;
			}
			case 5:
			{
				/*
				 * 
				 *  o o
				 *   o
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				break;
			}
			case 6:
			{
				/*
				 * 
				 *   o 
				 * o o o
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2), (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (2 * sw) - (2 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (2 * sw) + (2 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				break;
			}
			case 7:
			{
				/*
				 * 
				 *  o o 
				 * o o o
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (2 * sw) - (2 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (2 * sw) + (2 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				break;
			}
			case 8:
			{
				/*
 
				 *   o o
				 * o o o o
				 *   o o
				 */
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (3 * sw) - (3 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (1 * sw) - (1 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (1 * sw) + (1 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (3 * sw) + (3 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				break;
			}
			case 9:
			{
				/*
 
				 *    o
				 *  o   o
				 *   o o
				 *  o   o
				 *   o o
				 */
				layout.add(new Point((w / 2) - (sw / 2), (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (3 * sw) - (3 * so), (h / 2) - (sh / 2) + (-1 * sh) + (-1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (3 * sw) + (3 * so), (h / 2) - (sh / 2) + (-1 * sh) + (-1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (1 * sw) - (1 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (1 * sw) + (1 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (3 * sw) - (3 * so), (h / 2) - (sh / 2) + (1 * sh) + (1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (3 * sw) + (3 * so), (h / 2) - (sh / 2) + (1 * sh) + (1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (1 * sw) - (1 * so), (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (1 * sw) + (1 * so), (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				break;
			}
			case 10:
			{
				/*
 
				 *   o o
				 *  o   o
				 *   o o
				 *  o   o
				 *   o o
				 */
				layout.add(new Point((w / 2) - (sw / 2) - (1 * sw) - (1 * so), (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (1 * sw) + (1 * so), (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (3 * sw) - (3 * so), (h / 2) - (sh / 2) + (-1 * sh) + (-1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (3 * sw) + (3 * so), (h / 2) - (sh / 2) + (-1 * sh) + (-1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (1 * sw) - (1 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (1 * sw) + (1 * so), (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (3 * sw) - (3 * so), (h / 2) - (sh / 2) + (1 * sh) + (1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (3 * sw) + (3 * so), (h / 2) - (sh / 2) + (1 * sh) + (1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - (1 * sw) - (1 * so), (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + (1 * sw) + (1 * so), (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				break;
			}
		}
	
		return layout;
	}
}

