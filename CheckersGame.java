import java.util.Scanner;

public class CheckersGame implements BoardGame {
	private CheckersBoard b = new CheckersBoard();
	public void start() {
		while(b.gameOver() == false) {
			int x1, x2, y1, y2;
			try {
				do {
					b.print();
					Scanner s = new Scanner(System.in);
					if (b.player1Turn()) { System.out.println("Player 1 turn");}
					else {System.out.println("Player 2 turn");}
					System.out.println("Select a piece");
					System.out.print("x: ");
					x1 = s.nextInt();
					System.out.print("y: ");
					y1 = s.nextInt();
					System.out.println("Select a square to move it to");
					System.out.print("x: ");
					x2 = s.nextInt();
					System.out.print("y: ");
					y2 = s.nextInt();
					System.out.println();
					if (!(b.validMove(x1, y1, x2, y2))) {
						System.out.println("Invalid move");
					}
				} while (!(b.validMove(x1, y1, x2, y2)));
				b.move(x1, y1, x2, y2);
				b.endTurn();
			} catch(Exception InputMismatchException) {
				System.out.println("Invalid input: Please enter an integer from 0 to 7");
			}
			
		}
		
	}
	
	
	public static void main(String args[]) {
		CheckersGame g = new CheckersGame();
		g.start();
	}
	
	
}
