package af.handball.helper;

import java.util.Random;

public class SkillGeneratorHelper {

	public static double generateRandomSkillQuality(int maxPercent, int minPercent, double quality) {
		Random random = new Random();
		
		double randomSkill = (double) (quality
				* (random
						.nextInt((maxPercent - minPercent) + 1) + minPercent) / 100);
		
		return randomSkill;
	}
}
