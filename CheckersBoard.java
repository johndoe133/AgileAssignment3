import java.util.Scanner;

public class CheckersBoard implements Board {
	
	private int[][] board = new int[8][8];
	private boolean player1 = true;
	private boolean player2 = false;
	
	/**
	 * Default constructor. Creates a board in the starting setup
	 */
	public CheckersBoard() {
		setup();
	}
	
	/**
	 * Prints out formatted version of board
	 */
	public void print() {
		System.out.println("    0   1   2   3   4   5   6   7   ");
		System.out.println("  +---+---+---+---+---+---+---+---+");
		for (int i = 0; i < 8; i++) {
			System.out.print(i + " |");
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == 0) {
					System.out.print("   |");
				} else if (board[i][j] == 1) {
					System.out.print(" X |");
				} else {
					System.out.print(" O |");
				}
			}
			System.out.print("  " + i);
			if (i == 7) {
				System.out.println("\n  +---+---+---+---+---+---+---+---+");
			} else {
				System.out.println("\n  +---+---+---+---+---+---+---+---+");
			}
		}
		System.out.println("    0   1   2   3   4   5   6   7   ");
	}
	
	/**
	 * Moves a piece from (x1, y1) to (x2, y2) without checking validity
	 * @param x1 x position of piece
	 * @param y1 y position of piece
	 * @param x2 x position of where piece is moved
	 * @param y2 y position of where piece is moved
	 */
	public void move(int x1, int y1, int x2, int y2) {
		board[y1][x1] = 0;
		String move = moveType(x1, y1, x2, y2);
		if (player1) {
			if (move == "normal") { board[y2][x2] = 1; }
			if (move == "eaten") {board[y1+(y2-y1)/2][x1+(x2-x1)/2] = 0; board[y2][x2] = 1; }
		}
		else { 
			if (move == "normal") { board[y2][x2] = 2; }
			if (move == "eaten") {board[y1+(y2-y1)/2][x1+(x2-x1)/2] = 0; board[y2][x2] = 2; }
		} 
	}
	
	/**
	 * Checks to see if it is a valid move, by checking if a valid piece was selected, 
	 * and if the movement is allowed
	 * @param x1 x position of piece
	 * @param y1 y position of piece
	 * @param x2 x position of where piece is moved
	 * @param y2 y position of where piece is moved
	 * @return
	 */
	public boolean validMove(int x1, int y1, int x2, int y2) {
		return inRange(x2, y2) && inRange(x1, y1) && correctPiece(x1, y1) && isDiagonal(x1, y1, x2, y2);
	}
	
	/**
	 * Checks to see if player is moving own piece
	 * @param x x position of piece
	 * @param y y position of piece
	 * @return
	 */
	private boolean correctPiece(int x, int y) {
		if (player1) { return (board[y][x] == 1); }
		else { return (board[y][x] == 2); }
	}
	
	/**
	 * Checks to see if the piece is moved forwards and diagonally, using moveType
	 * @param x1 x position of piece
	 * @param y1 y position of piece
	 * @param x2 x position of where piece is moved
	 * @param y2 y position of where piece is moved
	 * @return true if valid, false if invalid
	 */
	private boolean isDiagonal(int x1, int y1, int x2, int y2) {
		return moveType(x1, y1, x2, y2) != "invalid";
	}
	
	/**
	 * Sets up board to starting positions
	 */
	public void setup() {
		for (int i = 0; i < 3; i += 2) {
			for (int j = 0; j < 8; j += 2) {
				board[i][j+1] = 1;
				board[i + 5][j] = 2;
				board[1][j] = 1;
				board[6][j+1] = 2;
			}
		}
		
	}
	
	/**
	 * Gives the type of movement the player is making
	 * @param x1 x position of piece
	 * @param y1 y position of piece
	 * @param x2 x position of where piece is moved
	 * @param y2 y position of where piece is moved
	 * @return "normal" - Piece is moved forwards and diagonally, nothing is eaten;
	 * "eaten" - A piece is moved forwards and diagonally, and eats an enemy piece in the process;
	 * "invalid" - The piece was moved in an invalid way
	 */
	private String moveType(int x1, int y1, int x2, int y2) {
		if (player1) {
			if (Math.abs(x2-x1) == 1 && (y2-y1) == 1 && board[y2][x2] == 0) { 
				return "normal";
			} else if (Math.abs(x2-x1) == 2 && (y2-y1) == 2 && board[y2][x2] == 0) { 
				int pieceInBetween = board[(y2-y1)/2+y1][(x2-x1)/2+x1];
				if ((pieceInBetween == 1 && player2) || (pieceInBetween == 2 && player1)) { return "eaten";} 
				else { return "invalid"; }
			} else {
				return "invalid";
			}
		} else {
			if (Math.abs(x2-x1) == 1 && (y2-y1) == -1 && board[y2][x2] == 0) { 
				return "normal";
			} else if (Math.abs(x2-x1) == 2 && (y2-y1) == -2 && board[y2][x2] == 0) { 
				int pieceInBetween = board[(y2-y1)/2+y1][(x2-x1)/2+x1];
				if ((pieceInBetween == 1 && player2) || (pieceInBetween == 2 && player1)) { return "eaten";} 
				else { return "invalid"; }
			} else {
				return "invalid";
			}
		}
	}
	
	/**
	 * Ends the turn
	 */
	public void endTurn() { player1 = ! player1; player2 = ! player2; }
	
	/**
	 * Checks to see if game is over. If there are no more of player 1 or 2's pieces left, it will return true.
	 * Otherwise, it returns false.
	 * @return game state
	 */
	public boolean gameOver() {
		boolean noMorePlayer1 = true;
		boolean noMorePlayer2 = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == 1) {
					noMorePlayer1 = false;
				} else if (board[i][j] == 2) {
					noMorePlayer2 = false;
				}
			}
		}
		return (noMorePlayer1 || noMorePlayer2);
	}
	
	/**
	 * Checks to see if a coordinate is within the range of the board
	 * @param x x position
	 * @param y y position
	 * @return true if in range, false if out of range
	 */
	private boolean inRange(int x, int y) {
		return (x >= 0 && x <= 7 && y >= 0 && y <= 7);
	}
	
	public boolean player1Turn() {
		return player1;
	}
}
