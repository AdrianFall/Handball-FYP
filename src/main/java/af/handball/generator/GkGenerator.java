package af.handball.generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import af.handball.entity.GkSkills;
import af.handball.entity.Player;

public class GkGenerator {

	public static final int AGE_TYPE_YOUNG = 1;
	public static final int AGE_YOUNG_MIN = 18;
	public static final int AGE_YOUNG_MAX = 22;
	public static final int AGE_TYPE_MIDDLE = 2;
	public static final int AGE_MIDDLE_MIN = 23;
	public static final int AGE_MIDDLE_MAX = 32;
	public static final int AGE_TYPE_OLD = 3;
	public static final int AGE_OLD_MIN = 33;
	public static final int AGE_OLD_MAX = 37;
	public static final int HEIGHT_MAX = 201;
	public static final int HEIGHT_MIN = 176;
	public static final int WEIGHT_MAX = 104;
	public static final int WEIGHT_MIN = 66;
	public static final String POSITION_GK_LABEL = "GK";
	public static final String SPECIAL_ABILITY_NONE = "none";
	public static final String SPECIAL_ABILITY_ONE_ON_ONE_SAVE = "one_on_one_save";
	public static final String SPECIAL_ABILITY_SEVEN_M_SAVE = "seven_m_save";
	public static final String SPECIAL_ABILITY_LONG_SHOOTS_SAVE = "long_shoots_save";
	public static final String MAP_PLAYER = "player";
	public static final String MAP_GK_SKILLS = "gkskills";
	private int leagueLevel;
	private BufferedReader br;
	private ArrayList<String> generatedRandomNumbersList;
	private String goalKeeperNames;
	private String[] separatedGoalKeeperNames;
	private int numberOfGoalKeeperNames;
	private Random randomGenerator;

	public GkGenerator(int leagueLevel) {
		this.leagueLevel = leagueLevel;
		
		try { // Obtain the text file with the GK names

			br = new BufferedReader(new FileReader(
					"C://Users//Adrian//Desktop//handball//polish-GK.txt"));
			System.out.println("Obtained the GK text file");
			goalKeeperNames = br.readLine();
			separatedGoalKeeperNames = goalKeeperNames.split(",");
			numberOfGoalKeeperNames = separatedGoalKeeperNames.length;
			randomGenerator = new Random();

		} catch (FileNotFoundException e) {
			System.out.println("Couldn't obtain the file with GK names.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error when reading line in the GK text file");
			e.printStackTrace();
		}

	}

	public Map<String, Object> generateGoalKeeper(int qualityType, int ageType, int teamLevel) {
		// Declare & Instantiate the entity beans
		Player player = new Player();
		GkSkills gkSkills = new GkSkills();
		// Declare & Instantiate a HashMap, which will be used for returning (method) the above entity beans
		Map<String,Object> entityBeansMap = new HashMap<String, Object>();

		// Obtain a random player name from the list of players positioned as goal keeper
		String randomName = separatedGoalKeeperNames[randomGenerator
				.nextInt(numberOfGoalKeeperNames)];
		// Set the obtained random name
		player.setName(randomName);
		System.out.println("Goal Keeper name has been set to: " + randomName);

		// Obtain an age through calling a method to generate random age
		// within the bounds of the inputed ageType (i.e. the min and max)
		int randomAge = generateAge(ageType);
		System.out.println("Goal Keeper age = " + randomAge);
		// Set the obtained random age
		player.setAge(randomAge);
		// Set the condition of the player to be 100% by default
		player.setCondition(100);
		// Set the form of the player to 0 by default
		player.setForm(0);
		
		// Generate random number either 1 (for right hand) or 2 (left hand) or 3 (both handed)
		int handed = randomGenerator.nextInt(2) + 1;
		switch (handed) {
		case 1: // right hand
			player.setHanded(Player.PLAYER_HAND_RIGHT);
			break;
		case 2: // left hand
			player.setHanded(Player.PLAYER_HAND_LEFT);
			break;
		case 3: // both hands
			player.setHanded(Player.PLAYER_HAND_BOTH);
			break;
		} // End of switch (hand generation)
		
		// Generate random height
		player.setHeight(randomGenerator.nextInt((HEIGHT_MAX - HEIGHT_MIN) + 1) + HEIGHT_MIN);
		
		player.setInjury_cause("none");
		
		player.setInjury_days(0);
		
		// Special ability creator
		 // Rules:
		  // 1 - players aged 18 are not entitled to obtain a special ability
		  // 2 - players aged 19 and 20 have 10% chance to obtain a special ability
		  // 3 - players aged 21-24 have 30% chance for a special ability
		  // 4 - players aged 25-33 have 50% chance
		  // 5 - players aged 34-35 have 95% chance
		String specialAbility = SPECIAL_ABILITY_NONE;
		if (randomAge == 18) specialAbility = SPECIAL_ABILITY_NONE;
		else if (randomAge == 19 || randomAge == 20) specialAbility = generateRandomAbility(10);
		else if (randomAge >= 21 && randomAge <= 24) specialAbility = generateRandomAbility(30);
		else if (randomAge >= 25 && randomAge <= 33) specialAbility = generateRandomAbility(50);
		else if (randomAge == 34 || randomAge == 35) specialAbility = generateRandomAbility(95);
		// END special ability creator
		// Set the random generated special ability
		player.setSpecial_ability(specialAbility);
		
		int minQuality = QualityGenerator.generateMinQuality(qualityType, teamLevel);
		int maxQuality = QualityGenerator.generateMaxQuality(qualityType, teamLevel);
		double quality = QualityGenerator.generateRandomQuality(minQuality, maxQuality);
		player.setPlayer_quality(quality);
		
		// Generate the market value
		int marketValue = generateMarketValue(quality, randomAge, specialAbility);
		// Set the obtained market value
		player.setMarket_value(marketValue);
		// Set the morale of the player to be 100% by default
		player.setMorale(100);
		// TODO
		player.setNationality("TO_BE_DONE!");
		// Set the player position
		player.setPlay_position(POSITION_GK_LABEL);
		// Generate random weight
		player.setWeight(randomGenerator.nextInt((WEIGHT_MAX - WEIGHT_MIN) + 1) + WEIGHT_MIN);
		// Put the player object to the entity beans map
		entityBeansMap.put(MAP_PLAYER, player);
		
		// Generate goal keeper's skills
		gkSkills.setFitness(quality);
		gkSkills.setNine_m_save(quality);
		gkSkills.setOne_on_one_save(quality);
		gkSkills.setSeven_m_save(quality);
		gkSkills.setWing_save(quality);
		// Put the gkSkills object to the entity beans map
		entityBeansMap.put(MAP_GK_SKILLS, gkSkills);
		
		return entityBeansMap;
	} // END of generateGoalKeeper

	private int generateMarketValue(double quality, int age,
			String specialAbility) {
		double higherFeePercentage = 1;
		if (age >= AGE_YOUNG_MIN && age <= AGE_YOUNG_MAX) higherFeePercentage = 1.65;
		else if (age >= AGE_MIDDLE_MIN && age <= AGE_MIDDLE_MAX) higherFeePercentage = 1.35;
		
		
		int marketValue = 0;
		if (!specialAbility.equals(SPECIAL_ABILITY_NONE)) {
			marketValue = (int) (((quality * higherFeePercentage) + (quality * 0.25))*1000);
		} else {
			marketValue = (int) ((quality*higherFeePercentage) * 1000);
		}
	
		return marketValue;
	}

	private String generateRandomAbility(int chancePercentage) {
		String specialAbility = SPECIAL_ABILITY_NONE;
		
		// Generate a random value between 1-100
		int randomRoll = randomGenerator.nextInt(100) + 1;
		
		
		if (chancePercentage >= randomRoll) {
			// Roll for a random ability
			  // Rules - rolling 1 = one on one saves
			   //                2 = seven m saves
			    //               3 = long shoots saves
			int randomAbilityRoll = randomGenerator.nextInt(2) + 1;
			switch (randomAbilityRoll) {
			case 1: 
				specialAbility = SPECIAL_ABILITY_ONE_ON_ONE_SAVE;
				break;
			case 2:
				specialAbility = SPECIAL_ABILITY_SEVEN_M_SAVE;
				break;
			case 3:
				specialAbility = SPECIAL_ABILITY_LONG_SHOOTS_SAVE;
				break;
			}
		}
		
		return specialAbility;
	}

	private int generateAge(int ageType) {
		int age = -1;
		switch (ageType) {
		case AGE_TYPE_YOUNG:
			age = randomGenerator.nextInt((AGE_YOUNG_MAX - AGE_YOUNG_MIN) + 1)
					+ AGE_YOUNG_MIN;
			break;
		case AGE_TYPE_MIDDLE:
			age = randomGenerator
					.nextInt((AGE_MIDDLE_MAX - AGE_MIDDLE_MIN) + 1)
					+ AGE_MIDDLE_MIN;
			break;
		case AGE_TYPE_OLD:
			age = randomGenerator.nextInt((AGE_OLD_MAX - AGE_OLD_MIN) + 1)
					+ AGE_OLD_MIN;
			break;
		}

		return age;
	}

}
