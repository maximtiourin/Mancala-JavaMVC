import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The Mancala Pit Stone Layout is a stone layout for a Mancala Pit.
 * <br>Date Created: 11/13/2012
 * <br>Date Updated: 11/13/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
public class PitMancalaLayout implements StoneLayout {	
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
				 *   o
				 *  o o
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2), (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				break;
			}
			case 6:
			{
				/*
				 *  o o
				 *  o o
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (-2 * sh) + (-2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (0 * sh) + (0 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (2 * sh) + (2 * so)));
				break;
			}
			case 7:
			{
				/*
				 *   o
				 *  o o
				 *  o o
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2), (h / 2) - (sh / 2) + (-3 * sh) + (-3 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (-1 * sh) + (-1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (-1 * sh) + (-1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (1 * sh) + (1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (1 * sh) + (1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (3 * sh) + (3 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (3 * sh) + (3 * so)));
				break;
			}
			case 8:
			{
				/*
				 *  o o
				 *  o o
				 *  o o
				 *  o o
				 */
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (-3 * sh) + (-3 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (-3 * sh) + (-3 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (-1 * sh) + (-1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (-1 * sh) + (-1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (1 * sh) + (1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (1 * sh) + (1 * so)));
				layout.add(new Point((w / 2) - (sw / 2) - sw - so, (h / 2) - (sh / 2) + (3 * sh) + (3 * so)));
				layout.add(new Point((w / 2) - (sw / 2) + sw + so, (h / 2) - (sh / 2) + (3 * sh) + (3 * so)));
				break;
			}
			default:
			{
				/*
				 * Repeats pattern while squishing it down the more stones there are
				 *    o
				 *   o
				 *  o
				 * o
				 */
				int stoneLimit = Math.min(stoneAmount, 48);
				for (int i = 0; i < stoneLimit; i++)
				{
					layout.add(new Point(((w / 2) - (sw / 2) + sw + so - ((i % 4) * (sw + so))) - so, ((sh / 2) - ((Math.min(stoneLimit / 12, 2)) * sh) + (i * sh / (Math.min(stoneLimit / 8, 3)))) + (h / 5)));
				}
			}
		}
	
		return layout;
	}
}
