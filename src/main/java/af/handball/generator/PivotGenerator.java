package af.handball.generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.hsqldb.rights.Right;

import scala.annotation.meta.setter;
import af.handball.entity.FieldPlayerSkills;
import af.handball.entity.Player;
import af.handball.entity.Skill;
import af.handball.helper.SkillGeneratorHelper;

public class PivotGenerator {

	public static final int AGE_TYPE_YOUNG = 1;
	public static final int AGE_YOUNG_MIN = 18;
	public static final int AGE_YOUNG_MAX = 22;
	public static final int AGE_TYPE_MIDDLE = 2;
	public static final int AGE_MIDDLE_MIN = 23;
	public static final int AGE_MIDDLE_MAX = 30;
	public static final int AGE_TYPE_OLD = 3;
	public static final int AGE_OLD_MIN = 31;
	public static final int AGE_OLD_MAX = 34;
	public static final int HEIGHT_MAX = 198;
	public static final int HEIGHT_MIN = 178;
	public static final int WEIGHT_MAX = 88;
	public static final int WEIGHT_MIN = 70;
	public static final String POSITION_PIVOT_LABEL = "PV";
	public static final String SPECIAL_ABILITY_NONE = "none";
	public static final String SPECIAL_ABILITY_DEFENSIVE_WALL = "defensive_wall";
	public static final String SPECIAL_ABILITY_COMPREHENSIVE = "comprehensive";
	public static final String SPECIAL_ABILITY_LONG_SHOOT = "long_shoot";
	public static final String SPECIAL_ABILITY_HAND_CATCHING = "hand_catching";
	public static final String MAP_PLAYER = "player";
	public static final String MAP_PIVOT_SKILLS = "pivot_skills";

	public static final int NUMBER_OF_FIELD_PLAYER_SKILLS = 19;

	public static final int SKILL_ACCELERATION_MIN = 90;
	public static final int SKILL_ACCELERATION_MAX = 110;
	public static final int SKILL_SPRINT_SPEED_MIN = 80;
	public static final int SKILL_SPRINT_SPEED_MAX = 120;
	public static final int SKILL_JUMPING_MIN = 90;
	public static final int SKILL_JUMPING_MAX = 110;
	public static final int SKILL_BALANCE_MIN = 90;
	public static final int SKILL_BALANCE_MAX = 110;
	public static final int SKILL_AGILITY_MIN = 90;
	public static final int SKILL_AGILITY_MAX = 120;
	public static final int SKILL_STAMINA_MIN = 80;
	public static final int SKILL_STAMINA_MAX = 120;
	public static final int SKILL_STRENGTH_MIN = 90;
	public static final int SKILL_STRENGTH_MAX = 120;
	public static final int SKILL_REACTIONS_MIN = 80;
	public static final int SKILL_REACTIONS_MAX = 120;
	public static final int SKILL_BLOCKING_MIN = 80;
	public static final int SKILL_BLOCKING_MAX = 90;
	public static final int SKILL_FITNESS_MIN = 90;
	public static final int SKILL_FITNESS_MAX = 120;
	public static final int SKILL_AGGRESSION_MIN = 90;
	public static final int SKILL_AGGRESSION_MAX = 120;
	public static final int SKILL_INTERCEPTIONS_MIN = 90;
	public static final int SKILL_INTERCEPTIONS_MAX = 110;
	public static final int SKILL_ATTACK_POSITION_MIN = 90;
	public static final int SKILL_ATTACK_POSITION_MAX = 120;
	public static final int SKILL_VISION_MIN = 90;
	public static final int SKILL_VISION_MAX = 120;
	public static final int SKILL_CREATIVITY_MIN = 90;
	public static final int SKILL_CREATIVITY_MAX = 120;
	public static final int SKILL_CATCHING_MIN = 90;
	public static final int SKILL_CATCHING_MAX = 120;
	public static final int SKILL_LONG_SHOTS_MIN = 80;
	public static final int SKILL_LONG_SHOTS_MAX = 110;
	public static final int SKILL_FK_ACCURACY_MIN = 90;
	public static final int SKILL_FK_ACCURACY_MAX = 120;
	public static final int SKILL_SHOT_POWER_MIN = 80;
	public static final int SKILL_SHOT_POWER_MAX = 120;
	public static final int SKILL_DRIBBLING_MIN = 90;
	public static final int SKILL_DRIBBLING_MAX = 110;
	public static final int SKILL_PASSING_MIN = 80;
	public static final int SKILL_PASSING_MAX = 110;
	public static final int SKILL_LONG_PASSING_MIN = 80;
	public static final int SKILL_LONG_PASSING_MAX = 100;
	public static final int SKILL_STAND_TACKLES_MIN = 90;
	public static final int SKILL_STAND_TACKLES_MAX = 120;
	public static final int SKILL_MARKING_MIN = 80;
	public static final int SKILL_MARKING_MAX = 90;
	public static final int SKILL_PENALTIES_MIN = 80;
	public static final int SKILL_PENALTIES_MAX = 120;
	public static final int SKILL_CURVE_MIN = 80;
	public static final int SKILL_CURVE_MAX = 100;
	public static final int SKILL_FINISHING_MIN = 90;
	public static final int SKILL_FINISHING_MAX = 120;
	public static final int SKILL_SIX_M_SHOTS_MIN = 80;
	public static final int SKILL_SIX_M_SHOTS_MAX = 100;
	public static final int SKILL_NINE_M_SHOTS_MIN = 80;
	public static final int SKILL_NINE_M_SHOTS_MAX = 110;
	public static final int SKILL_LOB_SHOTS_MIN = 80;
	public static final int SKILL_LOB_SHOTS_MAX = 110;

	private int leagueLevel;
	private BufferedReader br;
	private ArrayList<String> generatedRandomNumbersList;
	private String pivotNames;
	private String[] separatedPivotNames;
	private int numberOfPivotNames;
	private Random randomGenerator;

	public PivotGenerator(int leagueLevel) {
		this.leagueLevel = leagueLevel;
		// Obtain the text file with the Pivot player names

		try {

			br = new BufferedReader(new FileReader(
					"C://Users//Adrian//Desktop//handball//polish-pivot.txt"));
			System.out.println("Obtained the pivot text file");
			pivotNames = br.readLine();
			separatedPivotNames = pivotNames.split(",");
			numberOfPivotNames = separatedPivotNames.length;
			randomGenerator = new Random();

		} catch (FileNotFoundException e) {
			System.out.println("Obtained the pivot text file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out
					.println("Error when reading line in the pivot text file");
			e.printStackTrace();
		}

	}

	public Map<String, Object> generatePivot(int qualityType, int ageType,
			int teamLevel, int playerNumber) {
		// Declare & Instantiate the entity beans
		Player player = new Player();
		/*FieldPlayerSkills fieldPlayerSkills = new FieldPlayerSkills();*/
		// Declare & Instantiate a HashMap, which will be used for returning
		// (method) the above entity beans
		Map<String, Object> entityBeansMap = new HashMap<String, Object>();

		// Obtain a random player name from the list of players positioned as
		// pivot
		String name = separatedPivotNames[randomGenerator
				.nextInt(numberOfPivotNames)];
		player.setName(name);

		// Obtain an age through calling a method to generate random age
		// within the bounds of the inputed ageType (i.e. the min and max)
		int randomAge = generateAge(ageType);
		player.setAge(randomAge);

		// Set the player number
		player.setNumber(playerNumber);

		// Set the condition of the player to be 100% by default
		player.setCondition(100);
		// Set the form of the player to 0 by default
		player.setForm(0);

		// Generate the pivot as right handed
		player.setHanded(Player.PLAYER_HAND_RIGHT);

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
			specialAbility = generateRandomAbility(30);
		else if (randomAge >= 25 && randomAge <= 33)
			specialAbility = generateRandomAbility(50);
		else if (randomAge == 34 || randomAge == 35)
			specialAbility = generateRandomAbility(95);
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
		player.setPlay_position(POSITION_PIVOT_LABEL);
		// Generate random weight
		player.setWeight(randomGenerator.nextInt((WEIGHT_MAX - WEIGHT_MIN) + 1)
				+ WEIGHT_MIN);
		// Put the player object to the entity beans map
		entityBeansMap.put(MAP_PLAYER, player);

		// Generate pivot's skills
		Skill skills = generatePivotSkills(quality);

		entityBeansMap.put(MAP_PIVOT_SKILLS, skills);

		/*
		 * fieldPlayerSkills = generatePivotSkillss(quality, fieldPlayerSkills);
		 * entityBeansMap.put(MAP_WINGER_SKILLS, fieldPlayerSkills);
		 */

		return entityBeansMap;
	}

	private Skill generatePivotSkills(double quality) {
		Skill skills = new Skill();
		// The remaining/expended quality points (due to random quality
		// generation)
		double offset = 0;

		// Generate skills in the Mental area
		double aggression = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_AGGRESSION_MAX, SKILL_AGGRESSION_MIN, quality));

		double attackPosition = (SkillGeneratorHelper
				.generateRandomSkillQuality(SKILL_ATTACK_POSITION_MAX,
						SKILL_ATTACK_POSITION_MIN, quality));

		double vision = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_VISION_MAX, SKILL_VISION_MIN, quality));

		double creativity = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_CREATIVITY_MAX, SKILL_CREATIVITY_MIN, quality));
		// END of generating skills in the mental area

		// Generate skills in the Physical area
		double sprintSpeed = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_SPRINT_SPEED_MAX, SKILL_SPRINT_SPEED_MIN, quality));
		double jumping = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_JUMPING_MAX, SKILL_JUMPING_MIN, quality));
		double balance = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_BALANCE_MAX, SKILL_BALANCE_MIN, quality));
		double stamina = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_STAMINA_MAX, SKILL_STAMINA_MIN, quality));
		double strength = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_STRENGTH_MAX, SKILL_STRENGTH_MIN, quality));
		double blocking = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_BLOCKING_MAX, SKILL_BLOCKING_MIN, quality));
		// END of generating skills in the physical area

		// Generate skills in the Technical area
		double catching = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_CATCHING_MAX, SKILL_CATCHING_MIN, quality));
		double shotPower = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_SHOT_POWER_MAX, SKILL_SHOT_POWER_MIN, quality));
		double dribbling = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_DRIBBLING_MAX, SKILL_DRIBBLING_MIN, quality));
		double passing = (SkillGeneratorHelper.generateRandomSkillQuality(
				SKILL_PASSING_MAX, SKILL_PASSING_MIN, quality));
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
		// END of generating skills in the technical area

		// Increment the current offset with each offset generated by mental
		// area skills.
		offset += quality - aggression;
		offset += quality - attackPosition;
		offset += quality - vision;
		offset += quality - creativity;
		// END Increment offset by mental area skills

		// Increment the current offset with each offset generated by physical
		// area
		offset += quality - sprintSpeed;
		offset += quality - jumping;
		offset += quality - balance;
		offset += quality - stamina;
		offset += quality - strength;
		offset += quality - blocking;
		// End Increment offset by physical area skills

		// Increment the current offset with each offset generated by technical
		// area
		offset += quality - catching;
		offset += quality - shotPower;
		offset += quality - dribbling;
		offset += quality - passing;
		offset += quality - penalties;
		offset += quality - curve;
		offset += quality - finishing;
		offset += quality - sixMShots;
		offset += quality - nineMShots;
		// End Increment offset by technical area

		if (offset > 0 || offset < 0) {
			double averageOffSet = offset / NUMBER_OF_FIELD_PLAYER_SKILLS;
			/* Add the offset to mental area skills */
			aggression += averageOffSet;
			attackPosition += averageOffSet;
			vision += averageOffSet;
			creativity += averageOffSet;
			/* END adding offset to mental area skills */

			/* Add the offset to physical area skills */
			sprintSpeed += averageOffSet;
			jumping += averageOffSet;
			balance += averageOffSet;
			stamina += averageOffSet;
			strength += averageOffSet;
			blocking += averageOffSet;
			/* END adding offset to physical area skills */

			/* Add the offset to technical area skills */
			catching += averageOffSet;
			shotPower += averageOffSet;
			dribbling += averageOffSet;
			passing += averageOffSet;
			penalties += averageOffSet;
			curve += averageOffSet;
			finishing += averageOffSet;
			sixMShots += averageOffSet;
			nineMShots += averageOffSet;
			/* END adding the offset to technical area skills */

		} // END if (offset bigger or less than 0)

		// Add the skills to the Skill entity bean
		skills.setAggression(aggression);
		skills.setAttack_position(attackPosition);
		skills.setVision(vision);
		skills.setCreativity(creativity);

		skills.setSprint_speed(sprintSpeed);
		skills.setJumping(jumping);
		skills.setBalance(balance);
		skills.setStamina(stamina);
		skills.setStrength(strength);
		skills.setBlocking(blocking);

		skills.setCatching(catching);
		skills.setShot_power(shotPower);
		skills.setDribbling(dribbling);
		skills.setPassing(passing);
		skills.setPenalties(penalties);
		skills.setCurve(curve);
		skills.setFinishing(finishing);
		skills.setSix_m_shots(sixMShots);
		skills.setNine_m_shots(nineMShots);

		// Add low amount of GK skills to entity bean
		skills.setReflexes(SkillGeneratorHelper.generateRandomSkillQuality(3,
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
		// END adding skills to Skill entity bean

		return skills;
	}
/*
	private FieldPlayerSkills generatePivotSkillss(double quality,
			FieldPlayerSkills fieldPlayerSkills) {
		// The remaining/expended quality points (due to random quality
		// generation)
		double offset = 0;
		// Roll for each of the field player's skills
		double blockSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_BLOCKING_MAX - SKILL_BLOCKING_MIN) + 1) + SKILL_BLOCKING_MIN) / 100);
		double positioningSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_POSITIONING_MAX - SKILL_POSITIONING_MIN) + 1) + SKILL_POSITIONING_MIN) / 100);
		double handlingSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_HANDLING_MAX - SKILL_HANDLING_MIN) + 1) + SKILL_HANDLING_MIN) / 100);
		double markingSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_MARKING_MAX - SKILL_MARKING_MIN) + 1) + SKILL_MARKING_MIN) / 100);
		double passingSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_PASSING_MAX - SKILL_PASSING_MIN) + 1) + SKILL_PASSING_MIN) / 100);
		double oneOnOneSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_ONE_ON_ONE_MAX - SKILL_ONE_ON_ONE_MIN) + 1) + SKILL_ONE_ON_ONE_MIN) / 100);
		double sevenMShootSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_SEVEN_M_SHOOT_MAX - SKILL_SEVEN_M_SHOOT_MIN) + 1) + SKILL_SEVEN_M_SHOOT_MIN) / 100);
		double nineMShootSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_NINE_M_SHOOT_MAX - SKILL_NINE_M_SHOOT_MIN) + 1) + SKILL_NINE_M_SHOOT_MIN) / 100);
		double wingShootSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_WING_SHOOT_MAX - SKILL_WING_SHOOT_MIN) + 1) + SKILL_WING_SHOOT_MIN) / 100);
		double fitnessSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_FITNESS_MAX - SKILL_FITNESS_MIN) + 1) + SKILL_FITNESS_MIN) / 100);
		double creativitySkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_CREATIVITY_MAX - SKILL_CREATIVITY_MIN) + 1) + SKILL_CREATIVITY_MIN) / 100);
		double speedSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_SPEED_MAX - SKILL_SPEED_MIN) + 1) + SKILL_SPEED_MIN) / 100);
		double strengthSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_STRENGTH_MAX - SKILL_STRENGTH_MIN) + 1) + SKILL_STRENGTH_MIN) / 100);
		double aggressionSkill = (double) (quality
				* (randomGenerator
						.nextInt((SKILL_AGGRESSION_MAX - SKILL_AGGRESSION_MIN) + 1) + SKILL_AGGRESSION_MIN) / 100);

		// Increment the current offset with each offset generated by skill.
		offset += quality - blockSkill;
		offset += quality - positioningSkill;
		offset += quality - handlingSkill;
		offset += quality - markingSkill;
		offset += quality - passingSkill;
		offset += quality - oneOnOneSkill;
		offset += quality - sevenMShootSkill;
		offset += quality - nineMShootSkill;
		offset += quality - wingShootSkill;
		offset += quality - fitnessSkill;
		offset += quality - creativitySkill;
		offset += quality - speedSkill;
		offset += quality - strengthSkill;
		offset += quality - aggressionSkill;

		if (offset > 0 || offset < 0) {
			double averageOffSet = offset
					/ FieldPlayerSkills.NUMBER_OF_FIELD_PLAYER_SKILLS;
			System.out.println("Average off set = " + averageOffSet);
			blockSkill += averageOffSet;

			positioningSkill += averageOffSet;
			handlingSkill += averageOffSet;
			markingSkill += averageOffSet;
			passingSkill += averageOffSet;
			oneOnOneSkill += averageOffSet;
			sevenMShootSkill += averageOffSet;
			nineMShootSkill += averageOffSet;
			wingShootSkill += averageOffSet;
			fitnessSkill += averageOffSet;
			creativitySkill += averageOffSet;
			speedSkill += averageOffSet;
			strengthSkill += averageOffSet;
			aggressionSkill += averageOffSet;

		}
		fieldPlayerSkills.setBlocking(blockSkill);
		fieldPlayerSkills.setPositioning(positioningSkill);
		fieldPlayerSkills.setHandling(handlingSkill);
		fieldPlayerSkills.setMarking(markingSkill);
		fieldPlayerSkills.setPassing(passingSkill);
		fieldPlayerSkills.setOne_on_one(oneOnOneSkill);
		fieldPlayerSkills.setSeven_m_shoot(sevenMShootSkill);
		fieldPlayerSkills.setNine_m_shoot(nineMShootSkill);
		fieldPlayerSkills.setWing_shoot(wingShootSkill);
		fieldPlayerSkills.setFitness(fitnessSkill);
		fieldPlayerSkills.setCreativity(creativitySkill);
		fieldPlayerSkills.setSpeed(speedSkill);
		fieldPlayerSkills.setStrength(strengthSkill);
		fieldPlayerSkills.setAggression(aggressionSkill);

		return fieldPlayerSkills;
	} // END of Generate Pivot's Skills
*/
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
				specialAbility = SPECIAL_ABILITY_COMPREHENSIVE;
				break;
			case 3:
				specialAbility = SPECIAL_ABILITY_HAND_CATCHING;
				break;
			case 4:
				specialAbility = SPECIAL_ABILITY_LONG_SHOOT;
				break;
			}
		}

		return specialAbility;
	}

}
