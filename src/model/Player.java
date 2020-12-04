package model;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Random;

public class Player {
	// // // Attributes
	private Miner agent;
	private Grid copy;
	private boolean[][] traversed;
	private LinkedList<Character> move_queue;
	private LinkedList<String> previous_moves;
	private int beacon_distance;

	// // // Constructors
	public Player(Grid map, int beacon_distance, String mode) {
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

		setDistanceOfBeaconToGold(beacon_distance);

		agent = new Miner(copy);

		if (mode.toUpperCase().equals("SMART")) {
			smart();
		} else {
			random();
		}
	}

	public void setDistanceOfBeaconToGold(int distance) {
		this.beacon_distance = distance;
	}


	// // // Methods
	// to be renamed as smart mode
	public void smart() {
		previous_moves = new LinkedList<>();
		String scanned;
		ArrayList<String> possible_moves;
		ArrayList<Integer> move_precedence;
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
						// Move to Beacon
						moveManeuver(agent.getFront());

						// Beacon Case Maneuvers
						foundBeaconManeuver();
						return;

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

		System.out.println("smart Moves Complete!");
	}

	public void random() {
		Random rng = new Random();
		previous_moves = new LinkedList<>();
		int MAX_ITR = 5000;

		// TODO integrate scanned data to GUI
		// TODO moving to null cases

		while (0 < MAX_ITR) {

			switch (rng.nextInt(3)) {
				case 0 -> {
					moveManeuver(agent.getFront());
				}

				case 1 -> {
					agent.scan();
					move_queue.add('S');
				}

				case 2 -> {
					agent.rotate();
					move_queue.add('R');
				}
			}

			if (agent.checkWin() || agent.checkLose()) {
				break;
			}
		}
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

	public void foundBeaconManeuver() {
		// Fresh
		int moved;

		// 4 Directions, 4 Beelines, 4 Backtracks
		for (int i = 0; i < 4; i++) {

			System.out.println(beacon_distance);
			moved = 0;
			// Beeline till you hit something or max distance
			for (int j = 0; i < beacon_distance; j++) {

				// Scan for obstacles (Pit or Edge)
				move_queue.add('S');
				if (agent.scan() != null && !(agent.scan().equals("PIT"))) {
					// Move
					moveManeuver(agent.getFront());
					moved++;

					// Check game over
					if (agent.checkWin() || agent.checkLose()) {
						// stop function
						return;
					}

				} else {
					// Stop moving
					break;
				}
			}

			// Walk back to beacon
			String initialDirection = agent.getFront();
			for (int j = 0; j < moved; j++) {
				backTrackManeuver(initialDirection);
			}

			// Rotate and Beeline next direction
			agent.rotate();
			move_queue.add('R');
		}
	}
}