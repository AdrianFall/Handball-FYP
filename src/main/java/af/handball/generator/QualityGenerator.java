package af.handball.generator;

import java.util.Random;

public class QualityGenerator {
	public static final int QUALITY_TYPE_WORLD_STAR = 1;
	public static final int QUALITY_TYPE_EXCELLENT = 2;
	public static final int QUALITY_TYPE_VERY_GOOD = 3;
	public static final int QUALITY_TYPE_GOOD = 4;
	public static final int QUALITY_TYPE_DECENT = 5;

	/*
	 * Recurisve method to generate a minimum quality based on the input quality
	 * type and level
	 */
	public static int generateMinQuality(int qualityType, int level) {
		int quality = -1;
		if (qualityType == QUALITY_TYPE_DECENT)
			return 1;
		else if (qualityType == QUALITY_TYPE_GOOD && level == 1)
			return 5;
		else if (qualityType == QUALITY_TYPE_VERY_GOOD && level == 1)
			return 10;
		else if (qualityType == QUALITY_TYPE_EXCELLENT && level == 1)
			return 15;
		else if (qualityType == QUALITY_TYPE_WORLD_STAR && level == 1)
			return 18;
		else {
			if (qualityType == QUALITY_TYPE_GOOD)
				return generateMinQuality(qualityType, --level) + 5;
			else if (qualityType == QUALITY_TYPE_VERY_GOOD)
				return generateMinQuality(QUALITY_TYPE_EXCELLENT, --level);
			else if (qualityType == QUALITY_TYPE_EXCELLENT)
				return generateMinQuality(QUALITY_TYPE_WORLD_STAR, --level);
			else if (qualityType == QUALITY_TYPE_WORLD_STAR)
				return generateMinQuality(QUALITY_TYPE_EXCELLENT, level) + 3;
		}

		return quality;
	} // END of generateMinQuality method

	/*
	 * Recurisve method to generate a maximum quality based on the input quality
	 * type and level
	 */
	public static int generateMaxQuality(int qualityType, int level) {
		int quality = -1;
		if (qualityType == QUALITY_TYPE_DECENT && level == 1)
			return 4;
		else if (qualityType == QUALITY_TYPE_GOOD && level == 1)
			return 9;
		else if (qualityType == QUALITY_TYPE_VERY_GOOD && level == 1)
			return 14;
		else if (qualityType == QUALITY_TYPE_EXCELLENT && level == 1)
			return 17;
		else if (qualityType == QUALITY_TYPE_WORLD_STAR && level == 1)
			return 20;
		else {
			if (qualityType == QUALITY_TYPE_DECENT)
				return generateMaxQuality(QUALITY_TYPE_GOOD, --level);
			if (qualityType == QUALITY_TYPE_GOOD)
				return generateMaxQuality(QUALITY_TYPE_VERY_GOOD, --level);
			else if (qualityType == QUALITY_TYPE_VERY_GOOD)
				return generateMaxQuality(QUALITY_TYPE_EXCELLENT, --level);
			else if (qualityType == QUALITY_TYPE_EXCELLENT)
				return generateMaxQuality(QUALITY_TYPE_WORLD_STAR, --level);
			else if (qualityType == QUALITY_TYPE_WORLD_STAR)
				return generateMaxQuality(QUALITY_TYPE_EXCELLENT, level) + 3;
		}

		return quality;
	} // END of generateMaxQuality method.

	public static double generateRandomQuality(int min, int max) {

		Random random = new Random();

		int randomQuality = (int) ((random
				.nextInt((max * 100 - min * 100) + 1 * 100) + min * 100));
		
		return (double) (randomQuality) / (double) 100;
	}
}
