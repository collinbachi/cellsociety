package cellsociety_team09;

import java.util.HashMap;
import javafx.scene.paint.Color;

public class Conway extends Simulation {
	private static final Color[] COLORS = { Color.WHITE, Color.BLACK };
	private static final int TOTAL_STATES = 2;
	private static final int OFF = 0;
	private static final int ON = 1;

	public Conway(HashMap<String, Double> parameterMap) {
		super(TOTAL_STATES, COLORS, parameterMap);
	}

	@Override
	public void checkRules(Cell cell) {
		int[] neighborInfo = collectNeighborInfo(cell.getMyNeighbors());
		if (neighborInfo[ON] < 2) {
			cell.setMyNextState(OFF);
		} else if (neighborInfo[ON] == 3 && cell.getMyCurrentState() == OFF) {
			cell.setMyNextState(ON);
		} else if (neighborInfo[ON] > 3) {
			cell.setMyNextState(OFF);
		} else if (neighborInfo[ON] <= 3) {
			cell.setMyNextState(ON);
		}
	}

}
