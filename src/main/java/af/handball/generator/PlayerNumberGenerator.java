package af.handball.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerNumberGenerator {

	public static final String MAP_GK_FIRST = "1GK";
	public static final String MAP_GK_SECOND = "2GK";
	public static final String MAP_RW_FIRST = "1RW";
	public static final String MAP_RW_SECOND = "2RW";
	public static final String MAP_LW_FIRST = "1LW";
	public static final String MAP_LW_SECOND = "2LW";
	public static final String MAP_CB_FIRST = "1CB";
	public static final String MAP_CB_SECOND = "2CB";
	public static final String MAP_RB_FIRST = "1RB";
	public static final String MAP_RB_SECOND = "2RB";
	public static final String MAP_LB_FIRST = "1LB";
	public static final String MAP_LB_SECOND = "2LB";
	public static final String MAP_PV_FIRST = "1PV";
	public static final String MAP_PV_SECOND = "2PV";
	
	public static Map<String, Object> generateRandomNumbers() {
		Random random = new Random();
		
		// Keep list of already used numbers
		ArrayList<Integer> usedNumberList = new ArrayList<Integer>(); 
		
		Map<String, Object> numberMaps = new HashMap<String, Object>();
		
		
		// First goal keeper always as number 1
		numberMaps.put(MAP_GK_FIRST, 1);
		usedNumberList.add(1);
		// Roll the second keeper number between 51-99
		int randomSecondKeeperRoll = random.nextInt((99 - 51) + 1) + 51;
		numberMaps.put(MAP_GK_SECOND, randomSecondKeeperRoll);
		usedNumberList.add(randomSecondKeeperRoll);
		
		ArrayList<Integer> numbersList = new ArrayList<Integer>();
		// Roll random numbers between 2-50 for the other 12 players 
		for (int i = 0; i < 12; i++) {
			int temporaryRandomNumber = random.nextInt((50 - 2) + 1) + 2;
			while (usedNumberList.contains(temporaryRandomNumber)) {
				// Re-roll
				temporaryRandomNumber = random.nextInt((50 - 2) + 1) + 2;
			}
			numbersList.add(temporaryRandomNumber);
			usedNumberList.add(temporaryRandomNumber);
		} // End for loop (12 players number generation)
		
		// Add the first RW
		numberMaps.put(MAP_RW_FIRST, numbersList.get(0));
		// Add the second RW
		numberMaps.put(MAP_RW_SECOND, numbersList.get(1));
		// Add the first LW
		numberMaps.put(MAP_LW_FIRST, numbersList.get(2));
		// Add the second LW
		numberMaps.put(MAP_LW_SECOND, numbersList.get(3));
		// Add the first CB
		numberMaps.put(MAP_CB_FIRST, numbersList.get(4));
		// Add the second CB
		numberMaps.put(MAP_CB_SECOND, numbersList.get(5));
		// Add the first LB
		numberMaps.put(MAP_LB_FIRST, numbersList.get(6));
		// Add the second LB
		numberMaps.put(MAP_LB_SECOND, numbersList.get(7));
		// Add the first RB
		numberMaps.put(MAP_RB_FIRST, numbersList.get(8));
		// Add the second RB
		numberMaps.put(MAP_RB_SECOND, numbersList.get(9));
		// Add the first PV
		numberMaps.put(MAP_PV_FIRST, numbersList.get(10));
		// Add the second PV
		numberMaps.put(MAP_PV_SECOND, numbersList.get(11));
		
		
		
		return numberMaps;
	}
}
