package af.handball.generator;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import af.handball.entity.Match;
import af.handball.entity.MatchDay;
import af.handball.entity.Team;
import af.handball.generator.exception.ScheduleGenerationException;

public class ScheduleGenerator {
	
	static final int MAX_HOUR = 16;
	static final int MIN_HOUR = 16;
	static final int MAX_MINUTE = 59;
	static final int MIN_MINUTE = 0;
	
	
	// Generations Rules
	// A pivot team (i.e. the first grabbed team by algorithm) must play 
	// one day home and the next away, for all the season.
	// Every team must face the same opponent at home and away.
	// Every team must have equal amount of home and away matches during the season.
	// Any team should not be playing three or more matches at home or away in a row. 

	// When does a team face the same opponent, time-wise?
	// The algorithm used attempts to provide the best distribution of the
	// same opponent matches to the user as it can.
	// As an example assuming there are 22 days to play a league of 12
	// teams, when the algorithms picks up a team A on
	// the first day of the season against a team B, then the algorithm will
	// schedule a match between team B and team A
	// (notice team A is playing AWAY this time) on the 12th day of the
	// season. By saying team C is playing against
	// team V on 19th day of season, we should be able to infer that team V
	// has played team C on the 8th day of season.
	
	
	/*PSEUDOCODE*/
//		1. Obtain the listOfTeams.
//		2. Obtain the first element from the listOfTeams and store it as string called pivot.
//		3. Remove the first element from the listOfTeams. 
//		4. FOR i = 1 to listOfTeams size 
//		       FOR j = 1 to (((listOfTeams size + 1) / 2))
//					IF i MOD 2 = 0 THEN
//						IF j = 1 THEN
//							teamPlayingHome = pivot
//							teamPlayingAway = listOfTeams.getElementAtIndex(listOfTeams size)
//						ELSE
//							teamPlayingHome = listOfTeams. getElementAtIndex (j)
//							teamPlayingAway = listOfTeams. getElementAtIndex(listOfTeams size – j)
//						ENDIF
//					ELSE
//						IF j = 1 THEN
//							teamPlayingHome = listOfTeams. getElementAtIndex(listOfTeams size)
//							teamPlayingAway = pivot
//						ELSE
//							teamPlayingHome = listOfTeams. getElementAtIndex(listOfTeams size - j)
//							teamPlayingAway = listOfTeams.getElementAtIndex(j)
//						ENDIF		
//					ENDIF
//					add teamPlayingHome and teamPlayingAway to the list of scheduled competitions
//		       ENDFOR
//	           rotate listOfTeams by shifting each element to the right (last element becomes first)
//		   ENDFOR 
//		5. RETURN list of scheduled competitions 
	
	
	public Map<String, Map<String, Map<String, String>>> generateAllMatchesCombinationList(
			ArrayList<String> teamList, int userTeamId) throws ScheduleGenerationException {


		
		if (teamList.size() % 2 != 0) throw new ScheduleGenerationException("The generation couldn't be processed since the team list size was not an even number. The team list size = " + teamList.size());
	
	

		Map<String, Map<String, Map<String, String>>> mappedMatches = new HashMap<String, Map<String, Map<String, String>>>();



		ArrayList<String> obtainedTeamList = new ArrayList<String>(teamList);

		// Generation with round-robin tournament algorithm
		// Obtain the pivot and remove it from the obtainedTeamList
		String pivot = obtainedTeamList.get(0);
		obtainedTeamList.remove(0);
		boolean pivotPlayingHome = false;
		for (int i = 0; i < (teamList.size() - 1); i++) {

			if (((i + 1) % 2) == 1)
				pivotPlayingHome = true;
			else
				pivotPlayingHome = false;
			// Loop through the days
			for (int j = 0; j < (teamList.size() / 2); j++) {
				Map<String, Map<String, String>> teamNameMap = new HashMap<String, Map<String, String>>();
				Map<String, String> matchRecordMap = new HashMap<String, String>();
				Map<String, String> secondSeasonMatchRecordMap = new HashMap<String, String>();
				String home = null;
				String away = null;
				if (pivotPlayingHome) {
					// Home
					if (j == 0) {
						home = pivot;
						away = obtainedTeamList
								.get(obtainedTeamList.size() - 1);
					} else { // Stage 2 of algorithm
						home = obtainedTeamList.get(j - 1);
						away = obtainedTeamList
								.get((obtainedTeamList.size() - 1) - j);
					}

			
				} else if (!pivotPlayingHome) {
					if (j == 0) {
						home = obtainedTeamList
								.get(obtainedTeamList.size() - 1);
						away = pivot;
					} else { // Stage 2
						home = obtainedTeamList
								.get((obtainedTeamList.size() - 1) - j);
						away = obtainedTeamList.get(j - 1);
					}
				}
				matchRecordMap.put("home", home);
				matchRecordMap.put("away", away);
				GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
				System.out.println("Current date: " + gc.getTime().toString());
				
				
				
				if (i == 0 && (userTeamId == Integer.parseInt(home) || userTeamId == Integer.parseInt(away))) { // ONLY IF day = 1 && real user (i.e. if the league is created by the user team creation)
					gc.add(Calendar.SECOND, 15);
					
					matchRecordMap.put("day", Integer.toString(gc.get(Calendar.DAY_OF_MONTH)));
					matchRecordMap.put("month", Integer.toString(gc.get(Calendar.MONTH)));
					matchRecordMap.put("year", Integer.toString(gc.get(Calendar.YEAR)));
					matchRecordMap.put("hour", Integer.toString(gc.get(Calendar.HOUR_OF_DAY)));
					matchRecordMap.put("minute", Integer.toString(gc.get(Calendar.MINUTE)));
					matchRecordMap.put("second", Integer.toString(gc.get(Calendar.SECOND)));
					System.out.println("Generated match for real user @ " + gc.getTime().toString());
				} else if (i == 0) {
					Random random = new Random();
					/*gc.add(Calendar.MINUTE, random.nextInt((30 - 12) + 1) + 12);*/
					// FIXME change back
					gc.add(Calendar.MINUTE, random.nextInt((8 - 6) + 1) + 6);
					matchRecordMap.put("day", Integer.toString(gc.get(Calendar.DAY_OF_MONTH)));
					matchRecordMap.put("month", Integer.toString(gc.get(Calendar.MONTH)));
					matchRecordMap.put("year", Integer.toString(gc.get(Calendar.YEAR)));
					matchRecordMap.put("hour", Integer.toString(gc.get(Calendar.HOUR_OF_DAY)));
					matchRecordMap.put("minute", Integer.toString(gc.get(Calendar.MINUTE)));
					matchRecordMap.put("second", Integer.toString(gc.get(Calendar.SECOND)));
				} else {
					Random random = new Random();
					//t(randomGenerator.nextInt((HEIGHT_MAX - HEIGHT_MIN) + 1)+ HEIGHT_MIN);
					
					int randomHour = random.nextInt((MAX_HOUR - MIN_HOUR) + 1) + MIN_HOUR;
					int randomMinute = random.nextInt((MAX_MINUTE - MIN_MINUTE) + 1) + MIN_MINUTE;
					
					gc.add(Calendar.HOUR, (24*(i)));
					
					gc.set(Calendar.HOUR_OF_DAY, randomHour);
					gc.set(Calendar.MINUTE, randomMinute);
					
					matchRecordMap.put("day", Integer.toString(gc.get(Calendar.DAY_OF_MONTH)));
					matchRecordMap.put("month", Integer.toString(gc.get(Calendar.MONTH)));
					matchRecordMap.put("year", Integer.toString(gc.get(Calendar.YEAR)));
					matchRecordMap.put("hour", Integer.toString(gc.get(Calendar.HOUR_OF_DAY)));
					matchRecordMap.put("minute", Integer.toString(gc.get(Calendar.MINUTE)));
					matchRecordMap.put("second", Integer.toString(gc.get(Calendar.SECOND)));
					
				}
			
				
				
				

				secondSeasonMatchRecordMap.put("home", away);
				secondSeasonMatchRecordMap.put("away", home);
				
				gc = (GregorianCalendar) Calendar.getInstance();
				
				Random random = new Random();
				//t(randomGenerator.nextInt((HEIGHT_MAX - HEIGHT_MIN) + 1)+ HEIGHT_MIN);
				
				int randomHour = random.nextInt((MAX_HOUR - MIN_HOUR) + 1) + MIN_HOUR;
				int randomMinute = random.nextInt((MAX_MINUTE - MIN_MINUTE) + 1) + MIN_MINUTE;
				
				gc.add(Calendar.HOUR_OF_DAY, (24*(i+teamList.size() - 1)));
				
				gc.set(Calendar.HOUR_OF_DAY, randomHour);
				gc.set(Calendar.MINUTE, randomMinute);
				
				secondSeasonMatchRecordMap.put("day", Integer.toString(gc.get(Calendar.DAY_OF_MONTH)));
				secondSeasonMatchRecordMap.put("month", Integer.toString(gc.get(Calendar.MONTH)));
				secondSeasonMatchRecordMap.put("year", Integer.toString(gc.get(Calendar.YEAR)));
				secondSeasonMatchRecordMap.put("hour", Integer.toString(gc.get(Calendar.HOUR_OF_DAY)));
				secondSeasonMatchRecordMap.put("minute", Integer.toString(gc.get(Calendar.MINUTE)));
				secondSeasonMatchRecordMap.put("second", Integer.toString(gc.get(Calendar.SECOND)));
				
				//"day" + (i + teamList.size())
	
				

				// Obtain to obtain the current teamNameMap
				try {
					teamNameMap = mappedMatches.get("day" + (i + 1));
					if (teamNameMap == null)
						teamNameMap = new HashMap<String, Map<String, String>>();

				} catch (Exception e) {
					// keep the teamNameMap empty.
					teamNameMap = new HashMap<String, Map<String, String>>();
				}

				teamNameMap.put(home, matchRecordMap);
				teamNameMap.put(away, matchRecordMap);

				mappedMatches.put("day" + (i + 1), teamNameMap);

				// Check if there exists a mapped day (in the 2nd half of season)  
				try {
					teamNameMap = mappedMatches.get("day" + (i + teamList.size()));
					if (teamNameMap == null)
						teamNameMap = new HashMap<String, Map<String, String>>();

				} catch (Exception e) {
					// keep the teamNameMap empty.
					teamNameMap = new HashMap<String, Map<String, String>>();
				}
				// Allocate the match 
				teamNameMap.put(home, secondSeasonMatchRecordMap);
				teamNameMap.put(away, secondSeasonMatchRecordMap);
				mappedMatches.put("day" + (i + teamList.size()), teamNameMap);

			} // End looping for half of team size

			// Rotate the obtainedTeamList (part of the round-robin tournament scheduling algorithm)
			Collections.rotate(obtainedTeamList, 1);

		} // END looping for days
		return mappedMatches;
	}
}
