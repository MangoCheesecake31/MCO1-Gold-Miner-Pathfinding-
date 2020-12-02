package model;

public class Cell {
	// // // // // Attributes
	public final int x;
	public final int y;
	
	private String type;
	private String origin;
	private final String[] VALID_CELL_TYPES = {"MINER", "GOLD", "PIT", "BEACON", "EMPTY"};


	// // // // // Constructors 
	public Cell(int x, int y, String type) {
		this.x = x;
		this.y = y;

		setType(type);
		setOrigin(this.type);
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

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOrigin() {
		return origin;
	}

	@Override
	public String toString() {
		try {
			switch (type) {
				case "PIT": 	return "P";
				case "MINER": 	return "M";
				case "BEACON":	return "B";
				case "GOLD":	return "G";
				case "EMPTY":	return " ";
				default:		return " ";
			}
		} catch (NullPointerException e) {
			return " ";
		}
	}
}