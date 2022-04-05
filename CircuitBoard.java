import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 * @author Rich Boundji
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	@SuppressWarnings("resource")
	public CircuitBoard(String filename) throws FileNotFoundException {
		
		try {
			Scanner fileScan = new Scanner(new File(filename));
			Scanner scanner = new Scanner(fileScan.nextLine());
			int rows = 0;
			int cols = 0;
			try {
				rows = Integer.parseInt(scanner.next());
				cols = Integer.parseInt(scanner.next());
			} catch (NumberFormatException e){
				throw new InvalidFileFormatException("first line does not contain valid row and column values");
			}
			
			this.ROWS = rows;
			this.COLS = cols;
			
			if(scanner.hasNext()) throw new InvalidFileFormatException("missing values in a row");
			scanner.close();
			
			//System.out.println(ROWS + " " + ROWS);
			this.board = new char[ROWS][COLS];
			
			for(int i =0; i < ROWS; i++) {
				Scanner lineScanner = new Scanner(fileScan.nextLine());
				for(int j=0; j< COLS; j++) {
					// 1 X 0
					String value = lineScanner.next();
					//System.out.println(value);
					
					if (value.length() != 1) throw new InvalidFileFormatException("the file contains an incorrect value");
					
					char character = value.charAt(0);
					if(character == OPEN || character == CLOSED || character == START|| character == END || character == TRACE) {
						if(character == START) {
							if(startingPoint == null) {
								startingPoint = new Point(i,j);
							} else {
								throw new InvalidFileFormatException("multiple starting points");
							}
						} 
						
						if(character == END) {
							if(endingPoint == null) {
								endingPoint = new Point(i,j);
							} else {
								throw new InvalidFileFormatException("multiple ending points");
							}
						} 
					board[i][j] = value.charAt(0);
					} else {
						 throw new InvalidFileFormatException("invalid character " + "'" + value + "'");
					}
					if(j != COLS-1 && !lineScanner.hasNext()) throw new InvalidFileFormatException("missing values in a row");
					if(j == COLS-1 && lineScanner.hasNext()) throw new InvalidFileFormatException("row contains extra values");
				}
				if(i != ROWS-1 && !fileScan.hasNextLine()) throw new InvalidFileFormatException("the mumber of rows is to short");
				if(i == ROWS-1 && fileScan.hasNextLine()) throw new InvalidFileFormatException("extra rows");
				lineScanner.close();
			}
			if(startingPoint == null || endingPoint == null) throw new InvalidFileFormatException("missing start or end point");
			
			fileScan.close();
		}
		catch(InvalidFileFormatException e) {
			throw e;
		}
		catch (FileNotFoundException e) {
			throw e;
		}
		catch (Exception e) {
			throw new FileNotFoundException("anything");
		}
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
