import java.awt.Point;
import java.util.List;

/**
 * The stone layout interfaces describes a strategy for ordering a given amount of stones
 * in an organized matter based on the amount of stones to be ordered, so that they can be
 * drawn nicely inside of a pit.
 * <br>Date Created: 11/13/2012
 * <br>Date Updated: 11/13/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
public interface StoneLayout {
	/**
	 * Returns a list of points for the given amount of stones
	 * that denote where each stone should be drawn relative to 0, 0.
	 * @param stoneAmount the amount of stones meant to be arranged
	 * @param pitWidth width of the pit the layout is for.
	 * @param pitHeight height of the pit the layout is for.
	 * @param stoneWidth width of the stone the layout is for.
	 * @param stoneHeight height of the stone the layout is for.
	 * @return List<Point> a list of points that denote where the stone should be drawn
	 */
	List<Point> getLayout(int stoneAmount, int pitWidth, int pitHeight, int stoneWidth, int stoneHeight);
}
