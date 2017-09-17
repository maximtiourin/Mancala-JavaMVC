import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Fully working implementation of a model for mancala that implements MancalModel
 * @author Enrique Padilla (formatting/commenting done by Maxim Tiourin)
 * @version 1.00
 */
public class Model implements MancalaModel {
	private static final int MAX_UNDO = 3; //Maximum amount of undoes allowed per player per turn
	private static final int TIME_INC = 350; //The time increment to wait when doing thread sleeps in the pit selection to help with animation
	private int gameOver; //Holds either the winning player, or PLAYER_NONE if the game hasn't been started once or won yet
	private boolean gameRunning; //Boolean of whether or not a game is currently running
	private int initialStoneCount; //The initial stone count to use for pits every time the game is started, set by controller
	private int turn; //The player whose turn it currently is
	private int[] pits; //The current state of the board
	private int[] oldPits; //The previous state of the board (used for undoing turns)'
	private int[] undoCount; //The counters for how many undos the players have performed.
	private boolean justUndoed; //Boolean flag on whether an undo was just performed, to prevent two undos in a row without making a move.
	private boolean gameJustStarted; //Boolean flag on whether or not a game just started and hasn't seen any moves, to prevent undoing before a move is made.
	private boolean wasFreeTurn; //Boolean flag on whether or not the previous turn resulted in a free turn, and the turn hasn't changed, used to decide who to undo for
	private ArrayList<ChangeListener> listeners; //All of our change listeners
	
	public Model()
	{
		listeners = new ArrayList<ChangeListener>();
		pits = new int[TOTAL_PITS];
		oldPits = new int[TOTAL_PITS];
		undoCount = new int[2]; // 2 = Total players
		initialStoneCount = 0; //Set to 0 because the controller needs to tell us how much this value should be
		turn = PLAYER_NONE; //Set to none since the game hasn't been started once yet
		gameOver = PLAYER_NONE; //Set to none since the game hasn't been started once yet
		gameRunning = false; //Set to false because the game hasn't been started yet
		justUndoed = false;
		gameJustStarted = false;
		wasFreeTurn = false;
	}
	
	@Override
	public void undoTurn() 
	{
		//Determine whose turn was the previous one
		int previousTurn;
		if (wasFreeTurn)
		{
			previousTurn = turn; //Same player as last turn due to free turn
		}
		else
		{
			previousTurn = getOppositePlayer(turn); //Opposite player of current turn because turn has changed.
		}
		
		if (!justUndoed && !gameJustStarted && gameRunning)
		{
			//Conditions for undo are cleared
			if (undoCount[previousTurn] < MAX_UNDO)
			{
				//The player can still undo this turn, so undo the turn
				setArrayEqualToArray(pits, oldPits); //Set current pits to old pits
				undoCount[previousTurn]++; //Add how many times this player has undoed this turn
				justUndoed = true;
				wasFreeTurn = false;
				
				turn = previousTurn;
				
				notifyListeners();
			}
		}
	}
	
	@Override
	public void selectPit(int pit) 
	{
		//Only works if the game is currently being played, and not in an inactive state, and the pit selected isn't already empty
		if (gameRunning && (getStonesInPit(pit) > 0))
		{
			//Set previous state of board to current state, and game no longer JUST started, also reset undoCount of correct player based on whether or not we had free turn
			setArrayEqualToArray(oldPits, pits);
			gameJustStarted = false;
			if (wasFreeTurn)
			{
				undoCount[turn] = 0;
			}
			else
			{
				undoCount[getOppositePlayer(turn)] = 0;
			}
			
			int currentTurn = turn; //Keep track of the turn
			turn = PLAYER_NONE; //Set turn to none to prevent the viewer from sending multiple requests
			boolean freeTurn = false; //Boolean to keep track of if the current player get's an extra turn
			
			//Transfer pieces into a variable, and remove them from the selected pit (Essentially picking up the pieces)
			int pieces = getStonesInPit(pit);
			setStonesInPit(pit, 0);
			
			//Notify listeners to draw the current state of the board, then wait 1/4th of the Time Increment
			notifyListeners();
			sleep(TIME_INC / 4);
			
			//Start placing the picked up pieces in an animated way!
			int piecesPlaced = 0; //How many pieces we have placed so far
			int currentPit = constrainPit(pit + 1); //Set our current placement pit as the next pit past our select pit.
			while (piecesPlaced < pieces)
			{
				/* Check all conditions */
				
				//First Check if we are trying to place into our enemy mancala
				if ((isMancalaPit(currentPit)) && (getOwnerOfPit(currentPit) != currentTurn))
				{
					//It is an enemy mancala! Skip it!
					currentPit = constrainPit(currentPit + 1);
				}
				
				//Check if we are placing our last piece
				if (piecesPlaced == pieces - 1)
				{
					//This is our last piece, do something special
					if ((getStonesInPit(currentPit) == 0) 
							&& (getOwnerOfPit(currentPit) == currentTurn)
							&& (!isMancalaPit(currentPit)))
					{
						//Our last piece, and we're dropping it into an empty pit on our side
						//Steal!
						
						//Place stone into current pit, then pause to show the stone was placed
						addStonesToPit(currentPit, 1);
						sleep(TIME_INC);
						notifyListeners();
						
						//Determine stolen pieces
						int stolenPieces = getStonesInPit(getOppositePit(currentPit)); //Pick up the stolen pieces
						
						//Notify then Pause for a 1/4th of TIME_INC to show the empty enemy pit
						notifyListeners();
						sleep(TIME_INC / 4);
						
						//Place the stolen pieces + our last piece into our mancala in an animated way
						for (int p = 0; p < stolenPieces + 1; p++)
						{
							if (p == stolenPieces)
							{
								//If we are placing the last piece, remove the stone we placed in current pit to show it has been placed as well
								setStonesInPit(currentPit, 0);
							}
							else
							{
								addStonesToPit(getOppositePit(currentPit), -1); //Subtract a stone from the enemy pit
							}
							
							addStonesToPit(getMancalaForPlayer(currentTurn), 1);
							
							//Pause and notify to create animation of the stones being placed
							sleep(TIME_INC);
							notifyListeners();
						}
						
						piecesPlaced++;
					}
					else if ((isMancalaPit(currentPit))
							&& (getOwnerOfPit(currentPit) == currentTurn))
					{
						//Our last piece, and we're dropping it into our own mancala.
						//Free Turn!
						freeTurn = true;
						addStonesToPit(currentPit, 1);
						piecesPlaced++;
					}
					else
					{
						//Our last piece, and nothing special is happening to it, just place it
						addStonesToPit(currentPit, 1);
						piecesPlaced++;
					}
				}
				else
				{
					//Not our last piece, place it and move on.
					addStonesToPit(currentPit, 1);
					piecesPlaced++;
				}
				
				//Pause and then notify listeners to create animation effect
				sleep(TIME_INC);
				notifyListeners();
				
				currentPit = constrainPit(currentPit + 1); //Move on to next pit
			}
			
			//Done placing pieces, evaluate the current game state to determine if game is over
			if (sideForPlayerIsEmpty(PLAYER_A))
			{
				//Game Over, Player A side is clear! Clear Player B's side and place into his mancala
				for (int i = PIT_B01; i <= PIT_B06; i++)
				{
					int totalStonesInPit = getStonesInPit(i);
					for (int s = 1; s <= totalStonesInPit; s++)
					{
						addStonesToPit(i, -1); //Subtract stone from pit
						addStonesToPit(getMancalaForPlayer(PLAYER_B), 1); //Add stone to mancala
						
						//Pause and notify to create animation effect
						sleep(TIME_INC / 2);
						notifyListeners();
					}
				}
				gameRunning = false;
			}
			else if (sideForPlayerIsEmpty(PLAYER_B))
			{
				// Game Over, Player B side is clear! Clear Player A's side and place into his mancala
				for (int i = PIT_A01; i <= PIT_A06; i++) 
				{
					int totalStonesInPit = getStonesInPit(i);
					for (int s = 1; s <= totalStonesInPit; s++)
					{
						addStonesToPit(i, -1); //Subtract stone from pit
						addStonesToPit(getMancalaForPlayer(PLAYER_A), 1); //Add stone to mancala
						
						//Pause and notify to create animation effect
						sleep(TIME_INC / 2);
						notifyListeners();
					}
				}
				gameRunning = false;
			}
			
			//If game is over, determine winner, otherwise determine next turn
			if (!gameRunning)
			{
				//Game is over, determine winner!
				turn = PLAYER_NONE; //Set turn to no one
				
				if (getMancalaForPlayer(PLAYER_A) > getMancalaForPlayer(PLAYER_B))
				{
					gameOver = PLAYER_A; //Player A won
				}
				else if (getMancalaForPlayer(PLAYER_B) > getMancalaForPlayer(PLAYER_A))
				{
					gameOver = PLAYER_B; //Player B won
				}
			}
			else
			{
				//If current player gets a free turn, change nothing, otherwise alternate to next player.
				if (!freeTurn)
				{
					wasFreeTurn = false;
					
					if (currentTurn == PLAYER_A)
					{
						turn = PLAYER_B;
					}
					else if (currentTurn == PLAYER_B)
					{
						turn = PLAYER_A;
					}
				}
				else
				{
					wasFreeTurn = true;
					turn = currentTurn;
				}
				
				justUndoed = false; //Reset undo constraint tracker
			}
			
			//Final notify to listeners
			notifyListeners();
		}
	}

	@Override
	public boolean isMancalaPit(int pit) 
	{
		//If the pit is equal to a mancala, return so.
		if (pit == PIT_A_MANCALA || pit == PIT_B_MANCALA)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public int getOwnerOfPit(int pit) 
	{
		/*If the given pit is below 0, or equal to or greater than the total pits we have, 
		return no player since it is not a valid pit.*/
		if (pit < 0 || pit >= TOTAL_PITS)
		{
			return PLAYER_NONE;
		}
		
		//If the pit belongs to player A return so, otherwise it must belong to player B so return that.
		if (pit <= PIT_A_MANCALA) return PLAYER_A;
		return PLAYER_B;
	}

	@Override
	public void setInitialStoneCount(int count) 
	{
		initialStoneCount = count;
	}

	@Override
	public void startGame() 
	{
		//Set initial
		gameOver = PLAYER_NONE;
		gameRunning = true;

		// Set turn
		turn = PLAYER_A;
		
		// Set initial undo count
		undoCount[PLAYER_A] = 0;
		undoCount[PLAYER_B] = 0;
		justUndoed = false;
		gameJustStarted = true;
		wasFreeTurn = false;

		// Set initial stones
		for (int i = 0; i < pits.length; i++) 
		{
			if (!isMancalaPit(i)) 
			{
				//Normal pit, set to initial stone count.
				pits[i] = initialStoneCount;
			} 
			else 
			{
				//Mancala Pit, set to empty.
				pits[i] = 0;
			}
		}

		notifyListeners();
	}

	@Override
	public int hasGameEnded() 
	{
		return gameOver;
	}

	@Override
	public int getPlayerTurn() 
	{
		return turn;
	}

	@Override
	public int getStonesInPit(int pit) 
	{
		return pits[pit];
	}

	@Override
	public void addChangeListener(ChangeListener l) 
	{
		listeners.add(l);
	}

	@Override
	public void notifyListeners() 
	{
		for (ChangeListener e : listeners)
		{
			e.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Sets the stones in the given pit to the given amount
	 * @param pit the pit to set stones in
	 * @param amount the amount of stones to set to
	 */
	private void setStonesInPit(int pit, int amount)
	{
		pits[pit] = 0;
	}
	
	/**
	 * Adds the stone amount to the given pit.
	 * @param pit the pit to add to
	 * @param amount the amount to add 
	 */
	private void addStonesToPit(int pit, int amount)
	{
		pits[pit] += amount;
	}
	
	/**
	 * Sets array a's values from range [0, b.length) equal to the same values
	 * of array b across that same range.  If array a is smaller than array b, no
	 * change occurs.
	 */
	private void setArrayEqualToArray(int[] a, int[] b)
	{
		if (a.length >= b.length)
		{
			for (int i = 0; i < b.length; i++)
			{
				a[i] = b[i];
			}
		}
	}
	
	/**
	 * Constrians the pit integer to always refer to a pit, so if it is higher than
	 * the highest pit identifier, it rolls back to 0 and then adds the difference and subtracting
	 * by 1 to account for the first indentifier starting at 0.
	 * EX: pit = 17; The highest pit identifier is 13, so constrainPit(17) returns 3 (17 - 13 - 1 = 3)
	 */
	private int constrainPit(int pit)
	{
		if (pit > (TOTAL_PITS - 1))
		{
			return (pit % (TOTAL_PITS - 1)) - 1;
		}
		
		return pit;
	}
	
	/**
	 * Returns the mancala pit owned by the given player,
	 * returns -1 if invalid player.
	 * @param player the player to return the mancala pit for
	 * @return int the identifier of the mancala pit
	 */
	private int getMancalaForPlayer(int player)
	{
		if (player == PLAYER_A)
		{
			return PIT_A_MANCALA;
		}
		else if (player == PLAYER_B)
		{
			return PIT_B_MANCALA;
		}
		
		return -1;
	}
	
	/**
	 * Returns the opponent of the given player
	 * @param player the player to get the opposite of
	 * @return the identifier of the opposite player
	 */
	private int getOppositePlayer(int player)
	{
		if (player == PLAYER_A)
		{
			return PLAYER_B;
		}
		else if (player == PLAYER_B)
		{
			return PLAYER_A;
		}
		
		return PLAYER_NONE;
	}
	
	/**
	 * Returns the opposite pit across the board from the given pit.
	 * @param pit the pit to check the opposite of
	 * @return int the identifier of the opposite pit
	 */
	private int getOppositePit(int pit)
	{
		switch (pit) 
		{
			case PIT_A01:
				return PIT_B06;
			case PIT_A02:
				return PIT_B05;
			case PIT_A03:
				return PIT_B04;
			case PIT_A04:
				return PIT_B03;
			case PIT_A05:
				return PIT_B02;
			case PIT_A06:
				return PIT_B01;
			case PIT_A_MANCALA:
				return PIT_B_MANCALA;
			case PIT_B01:
				return PIT_A06;
			case PIT_B02:
				return PIT_A05;
			case PIT_B03:
				return PIT_A04;
			case PIT_B04:
				return PIT_A03;
			case PIT_B05:
				return PIT_A02;
			case PIT_B06:
				return PIT_A01;
			case PIT_B_MANCALA:
				return PIT_A_MANCALA;
		}
		
		return -1;
	}
	
	/**
	 * Returns true if the player's normal pits are all empty
	 * @param player the player to check for
	 * @return boolean true if the normal pits are all empty for player.
	 */
	private boolean sideForPlayerIsEmpty(int player)
	{
		if (player == PLAYER_A)
		{
			if ((getStonesInPit(PIT_A01)
					+ getStonesInPit(PIT_A02)
					+ getStonesInPit(PIT_A03)
					+ getStonesInPit(PIT_A04)
					+ getStonesInPit(PIT_A05)
					+ getStonesInPit(PIT_A06)) 
					== 0)
			{
				return true;
			}
		}
		else if (player == PLAYER_B)
		{
			if ((getStonesInPit(PIT_B01)
					+ getStonesInPit(PIT_B02)
					+ getStonesInPit(PIT_B03)
					+ getStonesInPit(PIT_B04)
					+ getStonesInPit(PIT_B05)
					+ getStonesInPit(PIT_B06)) 
					== 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Pauses the current thread and sleeps for the given time increment.
	 * @param timeIncrement how long to sleep for
	 */
	private void sleep(int timeIncrement)
	{
		try {
			Thread.sleep(timeIncrement);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
