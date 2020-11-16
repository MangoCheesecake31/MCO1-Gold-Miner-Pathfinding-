public class Cell {
	// // // // // Attributes
	public final int x;
	public final int y;
	
	private String type;
	private Cell origin;
	private final String[] VALID_CELL_TYPES = {"MINER", "GOLD", "PIT", "WALL", "BEACON", "EMPTY"};


	// // // // // Constructors 
	public Cell(int x, int y, String type) {
		this.x = x;
		this.y = y;

		setType(type);
	}


	// // // // // Methods
	public void setType(String type) {
		for (String cell_type: VALID_CELL_TYPES) {
			if (type.equalsIgnoreCase(cell_type)) {
				this.type = type.toUpperCase();
				return;
			}
		}

		System.out.println("An Error has Occured: Invalid Cell Types! (" + type + ")");
	}

	public String getType() {
		return type;
	}

	public void setOrigin(Cell origin) {
		this.origin = origin;
	}

	public Cell getOrigin() {
		return origin;
	}

	@Override
	public String toString() {
		try {
			switch (type) {
				case "PIT": 	return "P";
				case "MINER": 	return "M";
				case "BEACON":	return "B";
				case "WALL":	return "W";
				case "GOLD":	return "G";
				case "EMPTY":	return " ";
				default:		return " ";
			}
		} catch (NullPointerException e) {
			return " ";
		}
	}
}