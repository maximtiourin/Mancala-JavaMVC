import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The primary frame for the Mancala Game.
 * <br>Date Created: 11/07/2012
 * <br>Date Updated: 11/15/2012
 * @author Maxim Tiourin
 * @version 1.00
 */
@SuppressWarnings("serial")
public class MancalaFrame extends JFrame implements ChangeListener 
{
	/**
	 * The preset title of the frame.
	 */
	public static final String TITLE = "Mancala";
	/**
	 * The preset width of the frame.
	 */
	public static final int WIDTH = 800; //600
	/**
	 * The preset height of the frame.
	 */
	public static final int HEIGHT = 400; //400
	private MancalaModel model;
	private ArrayList<BoardStyle> availableStyles;
	private BoardStyle currentStyle;
	private JPanel stylePanel;
	private JPanel countSelectionPanel;
	private JPanel boardPanel;
	private boolean smoothMouseClicked; //Boolean used to register mouse clicks better than the Java Event Dispatcher does.
	private MouseListener boardMouseListener;
	private MouseMotionListener boardMouseMotionListener;
	private MancalaPitPanel[] pits;
	private MancalaBoardPanel board;
	
	public MancalaFrame(MancalaModel model)
	{
		super();
		
		this.model = model;
		
		smoothMouseClicked = false;
		
		availableStyles = new ArrayList<BoardStyle>();
		pits = new MancalaPitPanel[MancalaModel.TOTAL_PITS];
	}
	
	/**
	 * Constructs the frame and initializes the first state.
	 * This should be called once some board styles have been added to the frame.
	 */
	public void initialize()
	{
		final JFrame frame = this;
		
		//Set title and dimensions
		this.setTitle(TITLE);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridLayout());
		
		//Add to model's listeners
		model.addChangeListener(this);

		// Set initial position
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - (this.getWidth() / 2)),	(int) (screenSize.getHeight() / 2 - (this.getHeight() / 2)));

		// Style Selection Panel
		stylePanel = getStylePanel();

		// Initialize first state
		add(stylePanel);
		
		//Create Mouse listeners but don't add them yet
		boardMouseListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (!smoothMouseClicked)
				{				
					//Update and see if a valid pit is selected
					if (model.hasGameEnded() <= -1)
					{
						//Find pit at mouse position
						MancalaPitPanel pit = findPitAtMousePosition(e.getX(), e.getY());
						
						for (int i = 0; i < MancalaModel.TOTAL_PITS; i++)
						{
							final int index = i;
							if (pit != null && pit.equals(pits[index]))
							{
								if (pit.getPlayer() == model.getPlayerTurn())
								{							
									Thread t = new Thread(new Runnable(){
										@Override
										public void run() {
											model.selectPit(index);
										}										
									});
									
									t.start();
									
									pit.setHighlighted(false);
								}
							}
						}
	
						frame.repaint();
					}
					
					smoothMouseClicked = true;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				smoothMouseClicked = false;
			}
			
		};
		
		boardMouseMotionListener = new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {				
				//Update and see if a valid pit is selected
				if (model.hasGameEnded() <= -1)
				{
					//Find pit at mouse position
					MancalaPitPanel pit = findPitAtMousePosition(e.getX(), e.getY());
					
					for (int i = 0; i < MancalaModel.TOTAL_PITS; i++)
					{
						if (pit == null)
						{
							pits[i].setHighlighted(false);
						}
						else if (pit.equals(pits[i]))
						{
							if (pit.getPlayer() == model.getPlayerTurn())
							{
								pits[i].setHighlighted(true);
							}
						}
						else
						{
							pits[i].setHighlighted(false);
						}
					}

					frame.repaint();
				}
			}			
		};

		// Set default close operation
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		// Update pit states
		updateBoard();
		
		repaint();
	}
	
	/**
	 * Adds the style to the frame's list of available board styles.
	 * @param style the board style to add
	 */
	public void addBoardStyle(BoardStyle style)
	{
		availableStyles.add(style);
	}
	
	/**
	 * Returns the current board style of the frame.
	 * @return BoardStyle the current style
	 */
	public BoardStyle getCurrentStyle()
	{
		return currentStyle;
	}
	
	/**
	 * Constructs and returns the style panel
	 * @return JPanel the panel
	 */
	private JPanel getStylePanel()
	{
		final JFrame frame = this;
		final int topOffset = 50;
		
		//Get available styles from model
		ArrayList<BoardStyle> styles = availableStyles;
		
		//Create main panel, also add some extra drawing to the panels paint method
		JPanel panel = new JPanel() {
			@Override
			public void paint(Graphics g)
			{
				//Draw Header plate for the panel
				super.paint(g);
				
				Graphics2D g2 = (Graphics2D) g;
				
				//Colors and fills the header plate
				g2.setColor(Color.gray);
				g2.fill(new Rectangle2D.Double(0, 0, this.getWidth(), topOffset));
				
				//Get font and font metrics for the title string
				Font font = new Font(Font.SANS_SERIF, Font.BOLD, 24);
				FontMetrics metrics = g2.getFontMetrics(font);
				
				String title = "Select Mancala Board Style";
				int width = metrics.stringWidth(title);
				int height = metrics.getHeight();
				
				//Draw the title string
				int alignment = 2;
				int outlineOffset = 1;
				g2.setFont(font);
				g2.setColor(Color.black);
				g2.setColor(Color.black);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment - outlineOffset, topOffset - (height / 2) - outlineOffset);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment + outlineOffset, topOffset - (height / 2) - outlineOffset);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment - outlineOffset, topOffset - (height / 2) + outlineOffset);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment + outlineOffset, topOffset - (height / 2) + outlineOffset);
				g2.setColor(Color.white);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment, topOffset - (height / 2));
			}
		};
		panel.setLayout(null);
		panel.setBackground(Color.darkGray);
		
		//Create inner button panel		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setSize(WIDTH / 2, HEIGHT - topOffset);
		buttonPanel.setLocation(WIDTH / 4, topOffset);
		buttonPanel.setBackground(Color.lightGray);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		//Add inner button panel to main panel
		panel.add(buttonPanel);
		
		//Create buttons and add them to button panel
		for (BoardStyle s : styles)
		{
			final BoardStyle style = s;
			
			//Create button and set it's text and colors to the style's name and colors
			JButton button = new JButton(style.getName());
			button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
			button.setPreferredSize(new Dimension(150, 40));
			button.setFocusPainted(false);
			button.addMouseListener(new ButtonHighlightMouseListener(button, style));
			button.setBackground(style.getButtonBackgroundColor());
			button.setForeground(style.getButtonForegroundColor());
			
			//Add button listener
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					//Set current style to selected style
					currentStyle = style;
					
					//Change states to displaying the count selection panel
					frame.remove(stylePanel);
					countSelectionPanel = getCountSelectionPanel();
					frame.add(countSelectionPanel);
					frame.pack();
					frame.setSize(WIDTH, HEIGHT);
					frame.repaint();
				}				
			});
			
			buttonPanel.add(button);
		}
		
		return panel;
	}
	
	/**
	 * Constructs and returns the count selection panel
	 * @return JPanel the panel
	 */
	private JPanel getCountSelectionPanel()
	{
		final JFrame frame = this;
		final int topOffset = 50;
		
		// Create main panel, also add some extra drawing to the panels paint
		// method
		JPanel panel = new JPanel() {
			@Override
			public void paint(Graphics g) 
			{
				// Draw Header plate for the panel
				super.paint(g);

				Graphics2D g2 = (Graphics2D) g;

				// Colors and fills the header plate
				g2.setColor(currentStyle.getPitColor());
				g2.fill(new Rectangle2D.Double(0, 0, this.getWidth(), topOffset));

				// Get font and font metrics for the title string
				Font font = new Font(Font.SANS_SERIF, Font.BOLD, 24);
				FontMetrics metrics = g2.getFontMetrics(font);

				String title = "Select Starting Stones per Pit";
				int width = metrics.stringWidth(title);
				int height = metrics.getHeight();

				// Draw the title string
				int alignment = 2;
				int outlineOffset = 1;
				g2.setFont(font);
				g2.setColor(Color.black);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment - outlineOffset, topOffset - (height / 2) - outlineOffset);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment + outlineOffset, topOffset - (height / 2) - outlineOffset);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment - outlineOffset, topOffset - (height / 2) + outlineOffset);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment + outlineOffset, topOffset - (height / 2) + outlineOffset);
				g2.setColor(Color.white);
				g2.drawString(title, ((this.getWidth() / 4) + (((this.getWidth() / 2) - width) / 2)) + alignment, topOffset - (height / 2));
			}
		};
		panel.setLayout(null);
		panel.setBackground(currentStyle.getBoardColor());

		// Create inner button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setSize(WIDTH / 2, HEIGHT - topOffset);
		buttonPanel.setLocation(WIDTH / 4, topOffset);
		buttonPanel.setBackground(currentStyle.getBackgroundColor());
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		// Add inner button panel to main panel
		panel.add(buttonPanel);
		
		//Create buttons
		for (int i = 3; i <= 4; i++)
		{
			final int index = i;
			
			JButton btn = new JButton(index + "");
			btn.addMouseListener(new ButtonHighlightMouseListener(btn, currentStyle));
			btn.setBackground(currentStyle.getButtonBackgroundColor());
			btn.setForeground(currentStyle.getButtonForegroundColor());
			btn.setPreferredSize(new Dimension(100, 100));
			btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
			
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{										
					model.setInitialStoneCount(index);
					
					frame.remove(countSelectionPanel);
					boardPanel = getBoardPanel();
					frame.add(boardPanel);
					frame.pack();
					frame.setSize(WIDTH, HEIGHT);
					frame.repaint();
				}			
			});
			
			buttonPanel.add(btn);
		}
		
		//Create change style button
		JButton btn = new JButton("Change Style");
		btn.addMouseListener(new ButtonHighlightMouseListener(btn, currentStyle));
		btn.setBackground(currentStyle.getButtonBackgroundColor());
		btn.setForeground(currentStyle.getButtonForegroundColor());
		btn.setPreferredSize(new Dimension(200, 25));
		btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(countSelectionPanel);
				frame.add(stylePanel);
				frame.pack();
				frame.setSize(WIDTH, HEIGHT);
				frame.repaint();
			}			
		});
		
		buttonPanel.add(btn);
		
		return panel;
	}
	
	/**
	 * Constructs and returns the board panel
	 * @return JPanel the panel
	 */
	private JPanel getBoardPanel()
	{				
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(currentStyle.getBackgroundColor());
		
		MancalaBoardPanel boardPanel = new MancalaBoardPanel(this);
		boardPanel.setLocation((this.getContentPane().getWidth() / 2) - (MancalaBoardPanel.BOARD_WIDTH / 2), (this.getContentPane().getHeight() / 2) - (MancalaBoardPanel.BOARD_HEIGHT / 2)); //Center in the middle of the frame
		boardPanel.setLayout(null);
		
		//Add pits
		MancalaPitPanel pit;
		
		int horOffset = ((MancalaBoardPanel.BOARD_WIDTH) - (2 * MancalaPitPanel.PIT_MANCALA_WIDTH) - (6 * MancalaPitPanel.PIT_NORMAL_WIDTH)) / 9; //Horizontal offsets for pits
		int verOffset = 10; //Vertical offsets for pits		
		/* B_Mancala */
		pit = new MancalaPitPanel(this);
		pit.setMancala(true);
		pit.setX((horOffset * 1) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 0) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 0));
		pit.setY(verOffset);
		pits[MancalaModel.PIT_B_MANCALA] = pit;
		boardPanel.addPitPanel(pit);
		/* A_01 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 2) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 0));
		pit.setY(MancalaBoardPanel.BOARD_HEIGHT - verOffset - MancalaPitPanel.PIT_NORMAL_HEIGHT);
		pits[MancalaModel.PIT_A01] = pit;
		boardPanel.addPitPanel(pit);
		/* A_02 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 3) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 1));
		pit.setY(MancalaBoardPanel.BOARD_HEIGHT - verOffset - MancalaPitPanel.PIT_NORMAL_HEIGHT);
		pits[MancalaModel.PIT_A02] = pit;
		boardPanel.addPitPanel(pit);
		/* A_03 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 4) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 2));
		pit.setY(MancalaBoardPanel.BOARD_HEIGHT - verOffset - MancalaPitPanel.PIT_NORMAL_HEIGHT);
		pits[MancalaModel.PIT_A03] = pit;
		boardPanel.addPitPanel(pit);
		/* A_04 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 5) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 3));
		pit.setY(MancalaBoardPanel.BOARD_HEIGHT - verOffset - MancalaPitPanel.PIT_NORMAL_HEIGHT);
		pits[MancalaModel.PIT_A04] = pit;
		boardPanel.addPitPanel(pit);
		/* A_05 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 6) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 4));
		pit.setY(MancalaBoardPanel.BOARD_HEIGHT - verOffset - MancalaPitPanel.PIT_NORMAL_HEIGHT);
		pits[MancalaModel.PIT_A05] = pit;
		boardPanel.addPitPanel(pit);
		/* A_06 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 7) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 5));
		pit.setY(MancalaBoardPanel.BOARD_HEIGHT - verOffset - MancalaPitPanel.PIT_NORMAL_HEIGHT);
		pits[MancalaModel.PIT_A06] = pit;
		boardPanel.addPitPanel(pit);
		/* A_Mancala */
		pit = new MancalaPitPanel(this);
		pit.setMancala(true);
		pit.setX((horOffset * 8) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 6));
		pit.setY(verOffset);
		pits[MancalaModel.PIT_A_MANCALA] = pit;
		boardPanel.addPitPanel(pit);
		/* B_06 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 2) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 0));
		pit.setY(verOffset);
		pits[MancalaModel.PIT_B06] = pit;
		boardPanel.addPitPanel(pit);
		/* B_05 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 3) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 1));
		pit.setY(verOffset);
		pits[MancalaModel.PIT_B05] = pit;
		boardPanel.addPitPanel(pit);
		/* B_04 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 4) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 2));
		pit.setY(verOffset);
		pits[MancalaModel.PIT_B04] = pit;
		boardPanel.addPitPanel(pit);
		/* B_03 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 5) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 3));
		pit.setY(verOffset);
		pits[MancalaModel.PIT_B03] = pit;
		boardPanel.addPitPanel(pit);
		/* B_02 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 6) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 4));
		pit.setY(verOffset);
		pits[MancalaModel.PIT_B02] = pit;
		boardPanel.addPitPanel(pit);
		/* B_01 */
		pit = new MancalaPitPanel(this);
		pit.setX((horOffset * 7) + (MancalaPitPanel.PIT_MANCALA_WIDTH * 1) + (MancalaPitPanel.PIT_NORMAL_WIDTH * 5));
		pit.setY(verOffset);
		pits[MancalaModel.PIT_B01] = pit;
		boardPanel.addPitPanel(pit);
		
		int buttonOffset = 10;
		/*New Game Button*/
		JButton btn = new JButton("New Game");
		btn.setLocation(buttonOffset, buttonOffset);
		btn.setSize(100, 25);
		btn.setFocusable(false);
		btn.addMouseListener(new ButtonHighlightMouseListener(btn, currentStyle));
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				model.startGame();
			}			
		});
		btn.setBackground(currentStyle.getButtonBackgroundColor());
		btn.setForeground(currentStyle.getButtonForegroundColor());
		mainPanel.add(btn);
		/*Undo Turn Button*/
		btn = new JButton("Undo Turn");
		btn.setLocation(this.getContentPane().getWidth() - 100 - buttonOffset, buttonOffset);
		btn.setSize(100, 25);
		btn.setFocusable(false);
		btn.addMouseListener(new ButtonHighlightMouseListener(btn, currentStyle));
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				model.undoTurn();
			}			
		});
		btn.setBackground(currentStyle.getButtonBackgroundColor());
		btn.setForeground(currentStyle.getButtonForegroundColor());
		mainPanel.add(btn);
		
		board = boardPanel;
		
		//Add mouse listener and bounds for appropriate pits
		this.getContentPane().addMouseListener(boardMouseListener);
		this.getContentPane().addMouseMotionListener(boardMouseMotionListener);
		
		//Update current stone counts for pits
		updateBoard();
		
		mainPanel.add(boardPanel);		
		
		return mainPanel;
	}
	
	/**
	 * Returns the pit at the given mouse position, if there is one.
	 */
	private MancalaPitPanel findPitAtMousePosition(int mousex, int mousey)
	{
		for (int i = 0; i < MancalaModel.TOTAL_PITS; i++)
		{
			if ((i != MancalaModel.PIT_A_MANCALA) && (i != MancalaModel.PIT_B_MANCALA))
			{
				//Check Bounds
				int x = pits[i].getX();
				int y = pits[i].getY();
				int w = MancalaPitPanel.PIT_NORMAL_WIDTH;
				int h = MancalaPitPanel.PIT_NORMAL_HEIGHT;
				int bw = MancalaBoardPanel.BOARD_WIDTH;
				int bh = MancalaBoardPanel.BOARD_HEIGHT;
				int xoffset = (this.getContentPane().getWidth() / 2) - (bw / 2);
				int yoffset = (this.getContentPane().getHeight() / 2) - (bh / 2);
				
				if (((mousex >= xoffset + x) && (mousex <= xoffset + x + w)) 
						&& ((mousey >= yoffset + y) && (mousey <= yoffset + y + h)))
				{
					return pits[i];
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Updates the information in the pit panels
	 */
	private void updateBoard()
	{
		//Update Board Panel
		board.setCurrentTurn(model.getPlayerTurn());
		
		//Update Pit Panels
		for (int i = 0; i < pits.length; i++)
		{
			pits[i].setStones(model.getStonesInPit(i));
			pits[i].setPlayer(model.getOwnerOfPit(i));
		}
	}
	
	/**
	 * Displays proper borders based on style for a button.
	 * <br>Date Created: 11/13/2012
	 * <br>Date Updated: 11/13/2012
	 * @author Maxim Tiourin
	 * @version 1.00
	 */
	private static final class ButtonHighlightMouseListener implements MouseListener {
		private JButton button;
		private BoardStyle currentStyle;
		
		public ButtonHighlightMouseListener(JButton button, BoardStyle currentStyle)
		{
			this.button = button;
			this.currentStyle = currentStyle;
			
			button.setBorder(this.currentStyle.createButtonDefaultBorder());
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			button.setBorder(currentStyle.createButtonHighlightBorder());
		}

		@Override
		public void mouseExited(MouseEvent e) {
			button.setBorder(currentStyle.createButtonDefaultBorder());
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}		
	}
}
