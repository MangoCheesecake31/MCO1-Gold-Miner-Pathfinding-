import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		System.out.println("| ------------------------------ MCO1 Gold Miner ------------------------------ |");
		System.out.println("| Map Specs: ");
		Scanner sc = new Scanner(System.in);

		int map_size = sc.nextInt();	// Size of Map 8 to 64 only
		int num_cell = sc.nextInt();	// Num. of Special Cells	(WALL, PIT)

		// Gold Cell
		int cell_x = sc.nextInt();
		int cell_y = sc.nextInt();
		String type;

		// Grid Instance
		Grid map = new Grid(map_size);
		map.updateCell(cell_x, cell_y, "GOLD", "GOLD");

		// Other Cells
		for (int i = 0; i < num_cell; i++) {
			cell_x = sc.nextInt();
			cell_y = sc.nextInt();
			type = sc.next();

			map.updateCell(cell_x, cell_y, type, type);
		}

		cls();

		Miner player = new Miner(map);
		String scanned = "null";

		while (true) {
			System.out.println("| ------------------------------ MCO1 Gold Miner ------------------------------ |");
			System.out.printf("| Dashboard -> Rotates: %3d  Moves: %3d  Scans: %3d  Face: %5s                |\n", player.getRotateCount(), player.getMoveCount(), player.getScanCount(), player.getFront());
			System.out.printf("| Last Scanned: %6s                                                          |\n", scanned);

			map.print();

			// Status
			if (player.checkWin()) {
				System.out.println("You Win! You found the gold :)");
				break;
			} else if (player.checkLose()) {
				System.out.println("You Lose! You fell into a pit :(");
				break;
			}

			// Controls
			switch (sc.next().toUpperCase().charAt(0)) {
				case 'M': player.move();
					break;
				case 'R': player.rotate();
					break;
				case 'S': scanned = player.scan();
					break;
				case 'E':
					System.out.println("Terminating Program...");
					System.exit(0);
					break;
				default:
			}
			
			cls();

		}
	}

	private static void cls() {
    	try {
		    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    	} catch (Exception e) {
    		System.out.println("XD");
    	}
    }
}