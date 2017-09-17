import javax.swing.event.ChangeListener;

/**
 * The MancalaModel Interface describes some functionality that will be required of the
 * Model used for Mancala by the Mancala User Interface classes.
 * <br>Date Created: 11/07/2012
 * <br>Date Updated: 11/08/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
public interface MancalaModel 
{
	public static final int TOTAL_PITS = 14;
	public static final int PIT_A01 = 0;
	public static final int PIT_A02 = 1;
	public static final int PIT_A03 = 2;
	public static final int PIT_A04 = 3;
	public static final int PIT_A05 = 4;
	public static final int PIT_A06 = 5;
	public static final int PIT_A_MANCALA = 6;
	public static final int PIT_B01 = 7;
	public static final int PIT_B02 = 8;
	public static final int PIT_B03 = 9;
	public static final int PIT_B04 = 10;
	public static final int PIT_B05 = 11;
	public static final int PIT_B06 = 12;
	public static final int PIT_B_MANCALA = 13;
	public static final int PLAYER_NONE = -1;
	public static final int PLAYER_A = 0;
	public static final int PLAYER_B = 1;
	
	/**
	 * Returns true if the given pit is a mancala pit, and
	 * false if it is a normal pit.
	 * @param pit the pit to check
	 * @return boolean true if the pit is a mancala pit
	 */
	boolean isMancalaPit(int pit);
	/**
	 * Returns the index of the player that owns the given pit.
	 * @param pit the pit to find the owner of
	 * @return int the index of the player who owns the pit.
	 */
	int getOwnerOfPit(int pit);
	/**
	 * Notifies the model that a request to undo the current turn has been made.
	 */
	void undoTurn();
	/**
	 * Sets the amount of stones to be used for the game when it is
	 * first started, this will be called by the controller.
	 * @param count the amount of stones.
	 */
	void setInitialStoneCount(int count);
	/**
	 * When this is ran the game is started over using the current settings in the model.
	 * This will be called by the controller.
	 */
	void startGame();
	/**
	 * If the game is over and one of the player's has won, returns
	 * the index of the winning player. Otherwise returns -1 to represent
	 * game still going on.
	 * @return int index of winning player
	 */
	int hasGameEnded();
	/**
	 * Returns the index representing the player who's turn it is.
	 * @return int value of the player who's turn it is to make a move.
	 */
	int getPlayerTurn();
	/**
	 * Tells the model that the player has selected the given pit for their turn.
	 * The user interface will only allow the clicking of pits for the current
	 * player who's turn it is, so this does not need to be checked for validity,
	 * unless desired.
	 * <br>
	 * Assumes that pits id's will be stored in a indexed manner that can
	 * be trivially checked, EXAMPLE: such as pits[0] = Model.PIT_LEFTMANCALA,
	 * pits[1] = MODEL.PIT_BOTTOM01, or some similar implementation such as this.
	 * Buttons for the pits will be indexed in such a way, so it will help to have
	 * similar architecture in the model.	 * 
	 * @param pit the id of the pit that was selected.
	 */
	void selectPit(int pit);
	/**
	 * Returns the amount of stones in the given pit.
	 * @param pit the pit to check.
	 * @return int the amount of stones in the given pit.
	 */
	int getStonesInPit(int pit);
	/**
	 * Adds a change listener to this models listeners;
	 * @param l the ChangeListner to add.
	 */
	public void addChangeListener(ChangeListener l);
	/**
	 * Notifies all of this model's listeners that change has occured.
	 */
	public void notifyListeners();
}
