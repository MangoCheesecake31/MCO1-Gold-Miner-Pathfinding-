public class Miner {
    // // // // // Attributes
    public int x = 0;
    public int y = 0;

  	private int front = 1;
  	private final String[] DIRECTIONS = {"UP", "RIGHT", "DOWN", "LEFT"};
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
    	int a = x;
    	int b = y;
        switch (DIRECTIONS[front]) {
            case "UP" -> {
                if(b == 0)
                    return null;
                else
                    for (; b != 0; b--)
                        if (!map.getCell(a, b).getType().equals("EMPTY") && !map.getCell(a, b).getType().equals("MINER"))
                            return map.getCell(a, b).getType();
            }
            case "RIGHT" -> {
                if(a == map.size)
                    return null;
                else
                    for(; a != map.size - 1; a++)
                        if(!map.getCell(a, b).getType().equals("EMPTY") && !map.getCell(a, b).getType().equals("MINER"))
                            return map.getCell(a, b).getType();
            }

            case "DOWN" -> {
                if(b == map.size)
                    return null;
                else
                    for(; b != map.size - 1; b++)
                        if(!map.getCell(a, b).getType().equals("EMPTY") && !map.getCell(a, b).getType().equals("MINER"))
                            return map.getCell(a, b).getType();
            }

            case "LEFT" -> {
                if(a == 0)
                    return null;
                else
                    for(; a != map.size - 1; a--)
                        if(!map.getCell(a, b).getType().equals("EMPTY") && !map.getCell(a, b).getType().equals("MINER"))
                            return map.getCell(a, b).getType();
            }
        }

        return null;
    }

    public void move() {
        try {
	        switch (DIRECTIONS[front]) {
	        	case "UP" -> {
                    map.updateCell(x, y - 1, "MINER");
                    map.updateCell(x, y, "EMPTY");
	        	    y -= 1;
	        	}

                case "RIGHT" -> {
                    map.updateCell(x + 1, y, "MINER");
                    map.updateCell(x, y, "EMPTY");
                    x += 1;
                }

                case "DOWN" -> {
                    map.updateCell(x, y + 1, "MINER");
                    map.updateCell(x, y, "EMPTY");
                    y += 1;
                }

                case "LEFT" -> {
                    map.updateCell(x - 1, y, "MINER");
                    map.updateCell(x, y, "EMPTY");
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
}



// [0, 0] [1, 0] [2, 0] [3, 0]
// [0, 1] [1, 1] [2, 1] [3, 1]
// [0, 2] [1, 2] [2, 2] [3, 2]
// [0, 3] [1, 3] [2, 3] [3, 3]
