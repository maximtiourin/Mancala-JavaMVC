/**
 * This is where main injection occurs, and loads up the MVC architecture.
 * <br>Date Created: 11/07/2012
 * <br>Date Updated: 11/07/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
public class MancalaTest 
{
	public static void main(String[] args)
	{
		MancalaModel model = new Model();
		
		MancalaFrame frame = new MancalaFrame(model);
		frame.addBoardStyle(new BlueSteel());
		frame.addBoardStyle(new Jungle());
		frame.initialize();
		frame.setVisible(true);
	}
}