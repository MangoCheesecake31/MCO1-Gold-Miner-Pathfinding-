package model;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Player {
	// // // Attributes
	private Miner agent;
	private Grid copy;
	private boolean[][] traversed;
	private LinkedList<Character> move_queue;
	private LinkedList<String> previous_moves;


	// // // Constructors
	public Player(Grid map) {
		move_queue = new LinkedList<>();
		copy = new Grid(map.getSize());
		traversed = new boolean[map.getSize()][map.getSize()];

		// Copy Map
		for (int i = 0; i < map.getSize(); i++) {
			for (int j = 0; j < map.getSize(); j++) {
				traversed[i][j] = false;

				if (!(map.getCell(i, j).getType().equals("EMPTY"))) {
					copy.updateCell(i, j, map.getCell(i, j).getType(), map.getCell(i, j).getType());
				}
			}
		}

		agent = new Miner(copy);
		auto();
	}


	// // // Methods
	public void auto() {
		previous_moves = new LinkedList<>();
		String scanned;
		ArrayList<String> possible_moves;
		ArrayList<Integer> move_precedence;
		int beacon_distance = 0;
		int highest_precedence = 0;

		while (!(agent.checkWin() || agent.checkLose())) {
			possible_moves = new ArrayList<>();
			move_precedence = new ArrayList<>();

			/*
				Precedence Levels
				GOLD 	4
				BEACON 	3
				EMPTY 	1
			*/

			// Scan 4 Directions
			for (int i = 0; i < 4; i++) {
				// Scan
				scanned = agent.scan();
				move_queue.add('S');

				// Evaluate Scanned
				if (scanned != null) {
					if (scanned.equals("GOLD")) {
						possible_moves.add(agent.getFront());
						move_precedence.add(4);
						break;

					} else if (scanned.equals("BEACON")) {
						possible_moves.add(agent.getFront());
						move_precedence.add(3);
						break;

						// TODO GET BEACON DATA

					} else if (!(scanned.equals("PIT"))) {
						// Check Traversed Cells
						try {
							switch (agent.getFront()) {
								case "UP" -> {
									if (!(traversed[agent.x][agent.y - 1])) {
										possible_moves.add(agent.getFront());
										move_precedence.add(1);
									}
								}
								case "RIGHT" -> {
									if (!(traversed[agent.x + 1][agent.y])) {
										possible_moves.add(agent.getFront());
										move_precedence.add(1);
									}
								}
								case "DOWN" -> {
									if (!(traversed[agent.x][agent.y + 1])) {
										possible_moves.add(agent.getFront());
										move_precedence.add(1);
									}
								}
								case "LEFT" -> {
									if (!(traversed[agent.x - 1][agent.y])) {
										possible_moves.add(agent.getFront());
										move_precedence.add(1);
									}
								}
							}
						} catch (ArrayIndexOutOfBoundsException e) {

						}
					}
				}

				// Rotate
				agent.rotate();
				move_queue.add('R');
			}

			// Evaluate Possible Moves
			if (possible_moves.size() != 0) {
				// Determine Next Move
				highest_precedence = Collections.max(move_precedence);
				moveManeuver(possible_moves.get(move_precedence.indexOf(highest_precedence)));

			} else {
				// Back Track
				backTrackManeuver(previous_moves.pop());
			}
		}
		
		System.out.println("Auto Moves Complete!");
	}

	public void backTrackManeuver(String previous) {
		String back_track = Miner.DIRECTIONS[(directionIndexOf(previous) + 2) % 4];
		
		while (!(back_track.equals(agent.getFront()))) {
			agent.rotate();
			move_queue.add('R');
		}

		traversed[agent.x][agent.y] = true;

		agent.move();
		move_queue.add('M');
	}

	public void moveManeuver(String direction) {
		while (!(direction.equals(agent.getFront()))) {
			agent.rotate();
			move_queue.add('R');
		}

		traversed[agent.x][agent.y] = true;
		previous_moves.push(direction);

		agent.move();
		move_queue.add('M');
	}

	public char getNextMove() {
		return move_queue.remove();
	}

	private int directionIndexOf(String direction) {
		for (int i = 0; i < 4; i++) {
			if (direction.equals(Miner.DIRECTIONS[i])) {
				return i;
			}
		}
		return -1;
	}
}