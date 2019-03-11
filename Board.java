public interface Board {
	public void setup();
	public void print();
	public void move(int x1, int y1, int x2, int y2);
	public boolean validMove(int x1, int y1, int x2, int y2);
	public void endTurn();
	public boolean gameOver();
}
