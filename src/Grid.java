public class Grid {
	// // // // // Attributes
	public final int size;
	private Cell[][] grid;


	// // // // // Constructors
	public Grid(int size) {
		this.size = size;
		grid = new Cell[size][size];
		setDefaultCells();
	}


	// // // // // Methods
	public int getSize() {
		return size;
	}

	public Cell getCell(int x, int y) {
		return grid[x][y];
	}

	public void updateCell(int x, int y, String type) {
		grid[x][y].setType(type);
	}

	public void updateCell(int x, int y, String type, String origin) {
		grid[x][y].setType(type);
		grid[x][y].setOrigin(origin);
	}

	public void revertCell(int x, int y) {
		grid[x][y].setType(grid[x][y].getOrigin());
	}

	public void print() {
		// print edge
		String edge = "+ ";
		for (int x = 0; x < size; x++) {
			edge = edge.concat("- ");
		}
		edge = edge.concat("+");

		// print map
		System.out.println(edge);
		for (int y = 0; y < size; y++) {

			String line = "| ";
			for (int x = 0; x < size; x++) {
				line = line.concat(grid[x][y] + " ");
			}


			line = line.concat("|");
			System.out.println(line);
		}
		System.out.println(edge);
	}

	private void setDefaultCells() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				grid[i][j] = new Cell(i, j, "EMPTY");
			}
		}
	}
}