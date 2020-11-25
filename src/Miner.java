public class Miner {
    // // // // // Attributes
    public int x = 0;
    public int y = 0;
  	public static final String[] DIRECTIONS = {"UP", "RIGHT", "DOWN", "LEFT"};

  	private int front = 1;
  	private final Grid map;

  	private int scan_count;
  	private int move_count;
  	private int rotate_count;


    // // // // // Constructors
  	public Miner(Grid map) {
  		this.map = map;
  		map.updateCell(0, 0, "MINER");
  	}


    // // // // // Methods
    public String scan() {
    	scan_count++;

        try {
            switch (DIRECTIONS[front]) {
                case "UP":      return map.getCell(x, y - 1).getType();
                case "RIGHT":   return map.getCell(x + 1, y).getType();
                case "DOWN":    return map.getCell(x, y + 1).getType();
                case "LEFT":    return map.getCell(x - 1, y).getType();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        
        return null;
    }

    public void move() {
        move_count++;
        
        try {
	        switch (DIRECTIONS[front]) {
	        	case "UP" -> {
                    map.updateCell(x, y - 1, "MINER");
                    map.revertCell(x, y);
	        	    y -= 1;
	        	}

                case "RIGHT" -> {
                    map.updateCell(x + 1, y, "MINER");
                    map.revertCell(x, y);
                    x += 1;
                }

                case "DOWN" -> {
                    map.updateCell(x, y + 1, "MINER");
                    map.revertCell(x, y);
                    y += 1;
                }

                case "LEFT" -> {
                    map.updateCell(x - 1, y, "MINER");
                    map.revertCell(x, y);
                    x -= 1;
                }
	        }
        } catch (ArrayIndexOutOfBoundsException e) {
        	System.out.println("Out of Bounds!");
        }
    }
    
    public void rotate() {
    	rotate_count++;
    	front = (front + 1) % 4;
    }

    public String getFront() {
    	return DIRECTIONS[front];
    }

    public int getRotateCount() {
    	return rotate_count;
    }

    public int getMoveCount() {
    	return move_count;
    }

    public int getScanCount() {
    	return scan_count;
    }

    public boolean checkWin() {
        return map.getCell(x, y).getOrigin().equals("GOLD");
    }

    public boolean checkLose() {
        return map.getCell(x, y).getOrigin().equals("PIT");
    }
}



