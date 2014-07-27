package af.handball.generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import af.handball.entity.FieldPlayerSkills;
import af.handball.entity.Player;
import af.handball.entity.Skill;
import af.handball.helper.SkillGeneratorHelper;

public class BackGenerator {

	public static final int AGE_TYPE_YOUNG = 1;
	public static final int AGE_YOUNG_MIN = 18;
	public static final int AGE_YOUNG_MAX = 22;
	public static final int AGE_TYPE_MIDDLE = 2;
	public static final int AGE_MIDDLE_MIN = 23;
	public static final int AGE_MIDDLE_MAX = 30;
	public static final int AGE_TYPE_OLD = 3;
	public static final int AGE_OLD_MIN = 31;
	public static final int AGE_OLD_MAX = 34;
	public static final int HEIGHT_MAX = 200;
	public static final int HEIGHT_MIN = 185;
	public static final int WEIGHT_MAX = 100;
	public static final int WEIGHT_MIN = 82;
	public static final String POSITION_LB_LABEL = "LB";
	public static final String POSITION_RB_LABEL = "RB";
	public static final String POSITION_CB_LABEL = "CB";
	public static final String SPECIAL_ABILITY_NONE = "none";
	public static final String SPECIAL_ABILITY_DEFENSIVE_WALL = "defensive_wall";
	public static final String SPECIAL_ABILITY_PASSING = "passing";
	public static final String SPECIAL_ABILITY_LONG_SHOOT = "long_shoot";
	public static final String SPECIAL_ABILITY_COUNTER_ATTACK = "counter_attack";
	public static final String MAP_PLAYER = "player";
	public static final String MAP_BACK_SKILLS = "back_skills";

	public static final int NUMBER_OF_FIELD_PLAYER_SKILLS = 30;

	public static final int SKILL_ACCELERATION_MIN = 80;
	public static final int SKILL_ACCELERATION_MAX = 100;
	public static final int SKILL_SPRINT_SPEED_MIN = 80;
	public static final int SKILL_SPRINT_SPEED_MAX = 110;
	public static final int SKILL_JUMPING_MIN = 80;
	public static final int SKILL_JUMPING_MAX = 110;
	public static final int SKILL_BALANCE_MIN = 100;
	public static final int SKILL_BALANCE_MAX = 120;
	public static final int SKILL_AGILITY_MIN = 90;
	public static final int SKILL_AGILITY_MAX = 120;
	public static final int SKILL_STAMINA_MIN = 80;
	public static final int SKILL_STAMINA_MAX = 110;
	public static final int SKILL_STRENGTH_MIN = 80;
	public static final int SKILL_STRENGTH_MAX = 110;
	public static final int SKILL_REACTIONS_MIN = 90;
	public static final int SKILL_REACTIONS_MAX = 120;
	public static final int SKILL_BLOCKING_MIN = 100;
	public static final int SKILL_BLOCKING_MAX = 130;
	public static final int SKILL_FITNESS_MIN = 80;
	public static final int SKILL_FITNESS_MAX = 110;
	public static final int SKILL_AGGRESSION_MIN = 90;
	public static final int SKILL_AGGRESSION_MAX = 120;
	public static final int SKILL_INTERCEPTIONS_MIN = 90;
	public static final int SKILL_INTERCEPTIONS_MAX = 120;
	public static final int SKILL_ATTACK_POSITION_MIN = 90;
	public static final int SKILL_ATTACK_POSITION_MAX = 120;
	public static final int SKILL_VISION_MIN = 90;
	public static final int SKILL_VISION_MAX = 120;
	public static final int SKILL_CREATIVITY_MIN = 80;
	public static final int SKILL_CREATIVITY_MAX = 120;
	public static final int SKILL_BALL_CONTROL_MIN = 90;
	public static final int SKILL_BALL_CONTROL_MAX = 120;
	public static final int SKILL_LONG_SHOTS_MIN = 80;
	public static final int SKILL_LONG_SHOTS_MAX = 100;
	public static final int SKILL_FK_ACCURACY_MIN = 90;
	public static final int SKILL_FK_ACCURACY_MAX = 110;
	public static final int SKILL_SHOT_POWER_MIN = 80;
	public static final int SKILL_SHOT_POWER_MAX = 120;
	public static final int SKILL_DRIBBLING_MIN = 90;
	public static final int SKILL_DRIBBLING_MAX = 120;
	public static final int SKILL_SHORT_PASSING_MIN = 90;
	public static final int SKILL_SHORT_PASSING_MAX = 120;
	public static final int SKILL_LONG_PASSING_MIN = 90;
	public static final int SKILL_LONG_PASSING_MAX = 120;
	public static final int SKILL_STAND_TACKLES_MIN = 90;
	public static final int SKILL_STAND_TACKLES_MAX = 120;
	public static final int SKILL_MARKING_MIN = 90;
	public static final int SKILL_MARKING_MAX = 120;
	public static final int SKILL_PENALTIES_MIN = 80;
	public static final int SKILL_PENALTIES_MAX = 110;
	public static final int SKILL_CURVE_MIN = 80;
	public static final int SKILL_CURVE_MAX = 100;
	public static final int SKILL_FINISHING_MIN = 80;
	public static final int SKILL_FINISHING_MAX = 110;
	public static final int SKILL_SIX_M_SHOTS_MIN = 90;
	public static final int SKILL_SIX_M_SHOTS_MAX = 110;
	public static final int SKILL_NINE_M_SHOTS_MIN = 80;
	public static final int SKILL_NINE_M_SHOTS_MAX = 120;
	public static final int SKILL_LOB_SHOTS_MIN = 90;
	public static final int SKILL_LOB_SHOTS_MAX = 110;
	
	private int leagueLevel;
	private BufferedReader br;
	private ArrayList<String> generatedRandomNumbersList;
	private String rightWingNames;
	private String[] separatedBackNames;
	private int numberOfWingerNames;
	private Random randomGenerator;

	public BackGenerator(int leagueLevel, String positionLabel) {
		this.leagueLevel = leagueLevel;
		// Obtain the text file with the Right Wing player names

		try {

			br = new BufferedReader(new FileReader(
					"C://Users//Adrian//Desktop//handball//polish-"
							+ positionLabel + ".txt"));
			System.out.println("Obtained the " + positionLabel + " text file");
			rightWingNames = br.readLine();
			separatedBackNames = rightWingNames.split(",");
			numberOfWingerNames = separatedBackNames.length;
			randomGenerator = new Random();

		} catch (FileNotFoundException e) {
			System.out.println("Couldn't obtain the file with " + positionLabel
					+ " names.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error when reading line in the "
					+ positionLabel + " text file");
			e.printStackTrace();
		}

	}

	public Map<String, Object> generateBack(int qualityType, int ageType,
			int teamLevel, String positionLabel, int playerNumber) {
		// Declare & Instantiate the entity beans
		Player player = new Player();
		// Declare & Instantiate a HashMap, which will be used for returning
		// (method) the above entity beans
		Map<String, Object> entityBeansMap = new HashMap<String, Object>();

		// Obtain a random player name from the list of players
		String name = separatedBackNames[randomGenerator
				.nextInt(numberOfWingerNames)];
		player.setName(name);
		System.out.println(positionLabel + " player set to: " + name);

		// Set the player number
		player.setNumber(playerNumber);

		// Obtain an age through calling a method to generate random age
		// within the bounds of the inputed ageType (i.e. the min and max)
		int randomAge = generateAge(ageType);
		player.setAge(randomAge);

		// Set the condition of the player to be 100% by default
		player.setCondition(100);
		// Set the form of the player to 0 by default
		player.setForm(0);

		// Generate the central backs as .... handed
		// left back as .... handed
		// right back as .... handed
		if (positionLabel.equals(POSITION_CB_LABEL)) {
			// Generate random number either 1 (for right hand) or 2 (left hand)
			// or
			// 3 (both handed)
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
		} else if (positionLabel.equals(POSITION_LB_LABEL))
			player.setHanded(Player.PLAYER_HAND_RIGHT);
		else if (positionLabel.equals(POSITION_RB_LABEL))
			player.setHanded(Player.PLAYER_HAND_LEFT);

		/*
		
		 */
		player.setHeight(randomGenerator.nextInt((HEIGHT_MAX - HEIGHT_MIN) + 1)
				+ HEIGHT_MIN);

		player.setInjury_cause("none");

		player.setInjury_days(0);

		// Set the percentage/offset of skill by default to 0
		player.setSkill_gain(0);

		// Set the skill points by default to 0
		player.setSkill_points(0);

		// Set the player intensity to half by default
		player.setIntensity(50);

		// Special ability creator
		// Rules:
		// 1 - players aged 18 are not entitled to obtain a special ability
		// 2 - players aged 19 and 20 have 10% chance to obtain a special
		// ability
		// 3 - players aged 21-24 have 30% chance for a special ability
		// 4 - players aged 25-33 have 50% chance
		// 5 - players aged 34-35 have 95% chance
		String specialAbility = SPECIAL_ABILITY_NONE;
		if (randomAge == 18)
			specialAbility = SPECIAL_ABILITY_NONE;
		else if (randomAge == 19 || randomAge == 20)
			specialAbility = generateRandomAbility(10);
		else if (randomAge >= 21 && randomAge <= 24)
			specialAbility = generateRandomAbility(20);
		else if (randomAge >= 25 && randomAge <= 33)
			specialAbility = generateRandomAbility(35);
		else if (randomAge == 34 || randomAge == 35)
			specialAbility = generateRandomAbility(85);
		// END special ability creator
		// Set the random generated special ability
		player.setSpecial_ability(specialAbility);

		int minQuality = QualityGenerator.generateMinQuality(qualityType,
				teamLevel);
		int maxQuality = QualityGenerator.generateMaxQuality(qualityType,
				teamLevel);
		double quality = QualityGenerator.generateRandomQuality(minQuality,
				maxQuality);
		player.setPlayer_quality(quality);

		// Generate the market value
		int marketValue = generateMarketValue(quality, randomAge,
				specialAbility);
		// Set the obtained market value
		player.setMarket_value(marketValue);
		// Set the morale of the player to be 100% by default
		player.setMorale(100);
		// TODO
		player.setNationality("TO_BE_DONE!");
		// Set the player position
		player.setPlay_position(positionLabel);
		// Generate random weight
		player.setWeight(randomGenerator.nextInt((WEIGHT_MAX - WEIGHT_MIN) + 1)
				+ WEIGHT_MIN);
		// Put the player object to the entity beans map
		entityBeansMap.put(MAP_PLAYER, player);

		// Generate winger's skills
		Skill skills = generateBackSkills(quality);
		entityBeansMap.put(MAP_BACK_SKILLS, skills);

		return entityBeansMap;
	}

	private Skill generateBackSkills(double quality) {
		Skill skills = new Skill();
		// The remaining/expended quality points (due to random quality
		// generation)
		double offset = 0;

		// Generate skills in the Mental area
		double aggression = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_AGGRESSION_MAX, SKILL_AGGRESSION_MIN, quality));

		double interceptions = (SkillGeneratorHelper
				.generateRandomSkillQuality(SKILL_INTERCEPTIONS_MAX,
						SKILL_INTERCEPTIONS_MIN, quality));

		double attackPosition = (SkillGeneratorHelper
				.generateRandomSkillQuality(SKILL_ATTACK_POSITION_MAX,
						SKILL_ATTACK_POSITION_MIN, quality));

		double vision = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_VISION_MAX, SKILL_VISION_MIN, quality));

		double creativity = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_CREATIVITY_MAX, SKILL_CREATIVITY_MIN, quality));
		// END of generating skills in the mental area

		// Generate skills in the Physical area
		double acceleration = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_ACCELERATION_MAX, SKILL_ACCELERATION_MIN, quality));
		double sprintSpeed = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_SPRINT_SPEED_MAX, SKILL_SPRINT_SPEED_MIN, quality));
		double jumping = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_JUMPING_MAX, SKILL_JUMPING_MIN, quality));
		double balance = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_BALANCE_MAX, SKILL_BALANCE_MIN, quality));
		double agility = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_AGILITY_MAX, SKILL_AGILITY_MIN, quality));
		double stamina = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_STAMINA_MAX, SKILL_STAMINA_MIN, quality));
		double strength = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_STRENGTH_MAX, SKILL_STRENGTH_MIN, quality));
		double reactions = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_REACTIONS_MAX, SKILL_REACTIONS_MIN, quality));
		double blocking = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_BLOCKING_MAX, SKILL_BLOCKING_MIN, quality));
		double fitness = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_FITNESS_MAX, SKILL_FITNESS_MIN, quality));
		// END of generating skills in the physical area

		// Generate skills in the Technical area
		double ballControl = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_BALL_CONTROL_MAX, SKILL_BALL_CONTROL_MIN, quality));
		double longShots = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_LONG_SHOTS_MAX, SKILL_LONG_SHOTS_MIN, quality));
		double fkAccuracy = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_FK_ACCURACY_MAX, SKILL_FK_ACCURACY_MIN, quality));
		double shotPower = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_SHOT_POWER_MAX, SKILL_SHOT_POWER_MIN, quality));
		double dribbling = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_DRIBBLING_MAX, SKILL_DRIBBLING_MIN, quality));
		double shortPassing = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_SHORT_PASSING_MAX, SKILL_SHORT_PASSING_MIN, quality));
		double longPassing = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_LONG_PASSING_MAX, SKILL_LONG_PASSING_MIN, quality));
		double standTackles = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_STAND_TACKLES_MAX, SKILL_STAND_TACKLES_MIN, quality));
		double marking = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_MARKING_MAX, SKILL_MARKING_MIN, quality));
		double penalties = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_PENALTIES_MAX, SKILL_PENALTIES_MIN, quality));
		double curve = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_CURVE_MAX, SKILL_CURVE_MIN, quality));
		double finishing = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_FINISHING_MAX, SKILL_FINISHING_MIN, quality));
		double sixMShots = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_SIX_M_SHOTS_MAX, SKILL_SIX_M_SHOTS_MIN, quality));
		double nineMShots = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_NINE_M_SHOTS_MAX, SKILL_NINE_M_SHOTS_MIN, quality));
		double lobShots = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_LOB_SHOTS_MAX, SKILL_LOB_SHOTS_MIN, quality));
		// END of generating skills in the technical area

		// Increment the current offset with each offset generated by mental
		// area skills.
		offset += quality - aggression;
		offset += quality - interceptions;
		offset += quality - attackPosition;
		offset += quality - vision;
		offset += quality - creativity;
		// END Increment offset by mental area skills

		// Increment the current offset with each offset generated by physical
		// area
		offset += quality - acceleration;
		offset += quality - sprintSpeed;
		offset += quality - jumping;
		offset += quality - balance;
		offset += quality - agility;
		offset += quality - stamina;
		offset += quality - strength;
		offset += quality - reactions;
		offset += quality - blocking;
		offset += quality - fitness;
		// End Increment offset by physical area skills

		// Increment the current offset with each offset generated by technical
		// area
		offset += quality - ballControl;
		offset += quality - longShots;
		offset += quality - fkAccuracy;
		offset += quality - shotPower;
		offset += quality - dribbling;
		offset += quality - shortPassing;
		offset += quality - longPassing;
		offset += quality - standTackles;
		offset += quality - marking;
		offset += quality - penalties;
		offset += quality - curve;
		offset += quality - finishing;
		offset += quality - sixMShots;
		offset += quality - nineMShots;
		offset += quality - lobShots;
		// End Increment offset by technical area

		if (offset > 0 || offset < 0) {
			double averageOffSet = offset / NUMBER_OF_FIELD_PLAYER_SKILLS;
			/* Add the offset to mental area skills */
			aggression += averageOffSet;
			interceptions += averageOffSet;
			attackPosition += averageOffSet;
			vision += averageOffSet;
			creativity += averageOffSet;
			/* END adding offset to mental area skills */

			/* Add the offset to physical area skills */
			acceleration += averageOffSet;
			sprintSpeed += averageOffSet;
			jumping += averageOffSet;
			balance += averageOffSet;
			agility += averageOffSet;
			stamina += averageOffSet;
			strength += averageOffSet;
			reactions += averageOffSet;
			blocking += averageOffSet;
			fitness += averageOffSet;
			/* END adding offset to physical area skills */

			/* Add the offset to technical area skills */
			ballControl += averageOffSet;
			longShots += averageOffSet;
			fkAccuracy += averageOffSet;
			shotPower += averageOffSet;
			dribbling += averageOffSet;
			shortPassing += averageOffSet;
			longPassing += averageOffSet;
			standTackles += averageOffSet;
			marking += averageOffSet;
			penalties += averageOffSet;
			curve += averageOffSet;
			finishing += averageOffSet;
			sixMShots += averageOffSet;
			nineMShots += averageOffSet;
			lobShots += averageOffSet;
			/* END adding the offset to technical area skills */

		} // END if (offset bigger or less than 0)

		// Add the skills to the Skill entity bean
		skills.setAggression(aggression);
		skills.setInterceptions(interceptions);
		skills.setAttack_position(attackPosition);
		skills.setVision(vision);
		skills.setCreativity(creativity);

		skills.setAcceleration(acceleration);
		skills.setSprint_speed(sprintSpeed);
		skills.setJumping(jumping);
		skills.setBalance(balance);
		skills.setAgility(agility);
		skills.setStamina(stamina);
		skills.setStrength(strength);
		skills.setReactions(reactions);
		skills.setBlocking(blocking);
		skills.setFitness(fitness);

		skills.setBall_control(ballControl);
		skills.setLong_shots(longShots);
		skills.setFk_accuracy(fkAccuracy);
		skills.setShot_power(shotPower);
		skills.setDribbling(dribbling);
		skills.setShort_passing(shortPassing);
		skills.setLong_passing(longPassing);
		skills.setStand_tackles(standTackles);
		skills.setMarking(marking);
		skills.setPenalties(penalties);
		skills.setCurve(curve);
		skills.setFinishing(finishing);
		skills.setSix_m_shots(sixMShots);
		skills.setNine_m_shots(nineMShots);
		skills.setLob_shots(lobShots);

		// Add low amount of GK skills to entity bean
		skills.setReflexes(SkillGeneratorHelper.generateRandomSkillQuality(3,
				1, quality));
		skills.setHandling(SkillGeneratorHelper.generateRandomSkillQuality(3,
				1, quality));
		skills.setPositioning(SkillGeneratorHelper.generateRandomSkillQuality(
				4, 2, quality));
		skills.setLeg_saves(SkillGeneratorHelper.generateRandomSkillQuality(2,
				1, quality));
		skills.setPenalty_saves(SkillGeneratorHelper
				.generateRandomSkillQuality(4, 1, quality));
		skills.setSix_m_saves(SkillGeneratorHelper.generateRandomSkillQuality(
				3, 1, quality));
		skills.setNine_m_saves(SkillGeneratorHelper.generateRandomSkillQuality(
				2, 1, quality));
		skills.setCommunication(SkillGeneratorHelper
				.generateRandomSkillQuality(8, 4, quality));
		skills.setAngles(SkillGeneratorHelper.generateRandomSkillQuality(2, 1,
				quality));
		skills.setCatching(SkillGeneratorHelper.generateRandomSkillQuality(9,
				5, quality));
		// END adding skills to Skill entity bean

		return skills;
	} // END of Generate Back Skills

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

	private int generateMarketValue(double quality, int age,
			String specialAbility) {
		double higherFeePercentage = 1;
		if (age >= AGE_YOUNG_MIN && age <= AGE_YOUNG_MAX)
			higherFeePercentage = 1.65;
		else if (age >= AGE_MIDDLE_MIN && age <= AGE_MIDDLE_MAX)
			higherFeePercentage = 1.35;

		int marketValue = 0;
		if (!specialAbility.equals(SPECIAL_ABILITY_NONE)) {
			marketValue = (int) (((quality * higherFeePercentage) + (quality * 0.25)) * 1000);
		} else {
			marketValue = (int) ((quality * higherFeePercentage) * 1000);
		}

		return marketValue;
	}

	private String generateRandomAbility(int chancePercentage) {
		String specialAbility = SPECIAL_ABILITY_NONE;

		// Generate a random value between 1-100
		int randomRoll = randomGenerator.nextInt(100) + 1;

		if (chancePercentage >= randomRoll) {
			// Roll for a random ability
			int randomAbilityRoll = randomGenerator.nextInt(3) + 1;
			switch (randomAbilityRoll) {
			case 1:
				specialAbility = SPECIAL_ABILITY_DEFENSIVE_WALL;
				break;
			case 2:
				specialAbility = SPECIAL_ABILITY_PASSING;
				break;
			case 3:
				specialAbility = SPECIAL_ABILITY_COUNTER_ATTACK;
				break;
			case 4:
				specialAbility = SPECIAL_ABILITY_LONG_SHOOT;
				break;
			}
		}

		return specialAbility;
	}

}
