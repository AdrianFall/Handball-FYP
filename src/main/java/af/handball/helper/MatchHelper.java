package af.handball.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import af.handball.entity.Player;
import af.handball.entity.Skill;

public class MatchHelper {
	
	public static double compareTeamsDefensiveSkills(List<Player> homeTeamPlayers, List<Player> awayTeamPlayers, List<Skill> homeTeamSkillList, List<Skill> awayTeamSkillList) {
		double homeTeamDefensiveSkillAsPercentage = 0;
		
		
		double homeTeamStrengthTotal = 0;
		double awayTeamStrengthTotal = 0;
		double homeTeamBalanceTotal = 0;
		double awayTeamBalanceTotal = 0;
		double homeTeamBlockingTotal = 0;
		double awayTeamBlockingTotal = 0;
		double homeTeamJumpingTotal = 0;
		double awayTeamJumpingTotal = 0;
		double homeTeamConditionTotal = 0;
		double awayTeamConditionTotal = 0;
		
		for (int i = 0; i < 7; i++) {
			Skill tempHomePlayerSkill= null; /*homeTeamSkillMap.get(tempHomePlayerId);*/
			Skill tempAwayPlayerSkill = null;/*awayTeamSkillMap.get(Integer.toString(awayTeamPlayers.get(i).getPlayer_id()));*/
			
			for (Skill s : homeTeamSkillList) {
				/*System.out.println("ssill.playerId = " + s.getPlayer_id());*/
				if (s.getPlayer_id() == homeTeamPlayers.get(i).getPlayer_id())
					tempHomePlayerSkill = s;
			}
			
			for (Skill s : awayTeamSkillList) {
				if (s.getPlayer_id() == awayTeamPlayers.get(i).getPlayer_id())
					tempAwayPlayerSkill = s;
			}
			homeTeamStrengthTotal += tempHomePlayerSkill.getStrength();
			awayTeamStrengthTotal += tempAwayPlayerSkill.getStrength();
			homeTeamBalanceTotal += tempHomePlayerSkill.getBalance();
			awayTeamBalanceTotal += tempAwayPlayerSkill.getBalance();
			homeTeamBlockingTotal += tempHomePlayerSkill.getBlocking();
			awayTeamBlockingTotal += tempAwayPlayerSkill.getBlocking();
			homeTeamJumpingTotal += tempHomePlayerSkill.getJumping();
			awayTeamJumpingTotal += tempAwayPlayerSkill.getJumping();
			homeTeamConditionTotal += homeTeamPlayers.get(i).getCondition()/10;
			awayTeamConditionTotal += awayTeamPlayers.get(i).getCondition()/10;
			
		} // END for 7 players

		// Strength - 25%
		// Balance - 10%
		// Blocking - 45%
		// Jumping - 10%
		// Condition - 10%
		homeTeamDefensiveSkillAsPercentage += ((homeTeamStrengthTotal / awayTeamStrengthTotal) / 2)*0.25; 
		homeTeamDefensiveSkillAsPercentage += ((homeTeamBalanceTotal / awayTeamBalanceTotal) / 2)*0.1; 
		homeTeamDefensiveSkillAsPercentage += ((homeTeamBlockingTotal/ awayTeamBlockingTotal) / 2)*0.45;
		homeTeamDefensiveSkillAsPercentage += ((homeTeamJumpingTotal / awayTeamJumpingTotal) / 2)*0.1;
		homeTeamDefensiveSkillAsPercentage += ((homeTeamConditionTotal / awayTeamConditionTotal) / 2)*0.1;
		
		return homeTeamDefensiveSkillAsPercentage;
	}

	public static double compareTeamsGoingForwardSkills(List<Player> homeTeamPlayers, List<Player> awayTeamPlayers, List<Skill> homeTeamSkillList, List<Skill> awayTeamSkillList) {
		double homeTeamGoingForwardSkillAsPercentage = 0;
		
		double homeTeamCreativityTotal = 0;
		double awayTeamCreativityTotal = 0;
		double homeTeamVisionTotal = 0;
		double awayTeamVisionTotal = 0;
		double homeTeamAttackPositioningTotal = 0;
		double awayTeamAttackPositioningTotal = 0;
		double homeTeamConditionTotal = 0;
		double awayTeamConditionTotal = 0;
		double homeTeamPassingTotal = 0;
		double awayTeamPassingTotal = 0;
		double homeTeamCatchingTotal = 0;
		double awayTeamCatchingTotal = 0;
		double homeTeamSprintSpeedTotal = 0;
		double awayTeamSprintSpeedTotal = 0;
		
		
		 
		
		for (int i = 0; i < 7; i++) {
			String tempHomePlayerId = Integer.toString(homeTeamPlayers.get(i).getPlayer_id());
			/*System.out.println(tempHomePlayerId);*/
			Skill tempHomePlayerSkill= null; /*homeTeamSkillMap.get(tempHomePlayerId);*/
			Skill tempAwayPlayerSkill = null;/*awayTeamSkillMap.get(Integer.toString(awayTeamPlayers.get(i).getPlayer_id()));*/
			
			for (Skill s : homeTeamSkillList) {
				/*System.out.println("ssill.playerId = " + s.getPlayer_id());*/
				if (s.getPlayer_id() == homeTeamPlayers.get(i).getPlayer_id())
					tempHomePlayerSkill = s;
			}
			
			for (Skill s : awayTeamSkillList) {
				if (s.getPlayer_id() == awayTeamPlayers.get(i).getPlayer_id())
					tempAwayPlayerSkill = s;
			}
			
			
			// Creativity - 15%
			// Vision - 15%
			// Attack Positioning - 15%
			// Condition - 20%
			// Passing - 10%
			// Catching - 10%
			// Sprint speed - 15%
			
			homeTeamCreativityTotal += tempHomePlayerSkill.getCreativity();
			awayTeamCreativityTotal += tempAwayPlayerSkill.getCreativity();
			homeTeamVisionTotal += tempHomePlayerSkill.getVision();
			awayTeamVisionTotal += tempAwayPlayerSkill.getVision();
			homeTeamAttackPositioningTotal += tempHomePlayerSkill.getAttack_position();
			awayTeamAttackPositioningTotal += tempAwayPlayerSkill.getAttack_position();
			homeTeamConditionTotal += homeTeamPlayers.get(i).getCondition()/10;
			awayTeamConditionTotal += awayTeamPlayers.get(i).getCondition()/10;
			homeTeamPassingTotal += tempHomePlayerSkill.getPassing();
			awayTeamPassingTotal += tempAwayPlayerSkill.getPassing();
			homeTeamCatchingTotal += tempHomePlayerSkill.getCatching();
			awayTeamCatchingTotal += tempAwayPlayerSkill.getCatching();
			homeTeamSprintSpeedTotal += tempHomePlayerSkill.getSprint_speed();
			awayTeamSprintSpeedTotal += tempAwayPlayerSkill.getSprint_speed(); 
		}// END for first 7 players

		homeTeamGoingForwardSkillAsPercentage += ((homeTeamCreativityTotal / awayTeamCreativityTotal) / 2)*0.15; // 0.56
		homeTeamGoingForwardSkillAsPercentage += ((homeTeamVisionTotal / awayTeamVisionTotal)/2)*0.15;
		homeTeamGoingForwardSkillAsPercentage += ((homeTeamAttackPositioningTotal / awayTeamAttackPositioningTotal)/2)*0.15;
		homeTeamGoingForwardSkillAsPercentage += ((homeTeamConditionTotal / awayTeamConditionTotal)/2)*0.2;
		homeTeamGoingForwardSkillAsPercentage += ((homeTeamPassingTotal / awayTeamPassingTotal)/2)*0.1;
		homeTeamGoingForwardSkillAsPercentage += ((homeTeamCatchingTotal / awayTeamCatchingTotal)/2)*0.1;
		homeTeamGoingForwardSkillAsPercentage += ((homeTeamSprintSpeedTotal / awayTeamSprintSpeedTotal)/2)*0.15;
			
			/*double awayTeamCreativityPerc = (awayTeamCreativityTotal / homeTeamCreativityTotal) / 2; // 0.44
	*/		
		
		return homeTeamGoingForwardSkillAsPercentage;
	}
	
	public static int assessPenaltyOfDefense(String playerPrimaryPosition, String playPosition) {
		// Penalties (defending)
		//             GK by anyone - 10%;
		//             CB by LB or RB - 4%;
		//             back by forwards - 12%;
		//             forwards by forwards - 4%;
		//				forward by GK - 12%;          
		//				back by GK - 30%;
		int penalty = 0;
		boolean isBackPlayPosition = (playPosition.equals("CB") || playPosition.equals("LB") || playPosition.equals("RB"));
		boolean isForwardPlayPosition = (playPosition.equals("RW") || playPosition.equals("PV") || playPosition.equals("LW"));
		boolean isBackPrimaryPlayPosition = (playerPrimaryPosition.equals("LB") || playerPrimaryPosition.equals("RB") || playerPrimaryPosition.equals("CB"));
		boolean isForwardPrimaryPlayPosition = (playerPrimaryPosition.equals("RW") || playerPrimaryPosition.equals("PV") || playerPrimaryPosition.equals("LW"));
		
		if (!playerPrimaryPosition.equals(playPosition)) {
			if (playPosition.equals("GK")) {
				penalty = 12;
			} else if (isBackPlayPosition) {
				if (playerPrimaryPosition.equals("GK")) {
					penalty = 30;
				} else if (isBackPrimaryPlayPosition) {
					penalty = 4;
				} else if (isForwardPrimaryPlayPosition) {
					penalty = 12;
				}
			} else if (isForwardPlayPosition) {
				if (playerPrimaryPosition.equals("GK")) {
					penalty = 20;
				} else if (isBackPrimaryPlayPosition) {
					penalty = 3;
				} else if (isForwardPlayPosition) {
					penalty = 2;
				}
			}
		}
		
		return penalty;
		
	}
	
	public static int assessPenaltyGoingForward(String playerPrimaryPosition, String playPosition) {
		// Penalties (going forward)
		//             GK by anyone  - 10%
		//             CB by LB or RB - 3%
		//             backs by forwards - 5%;
		//             forwards by forwards - 3%;
		//             forwards by GK - 25%;
		//             back by GK - 15%;
		int penalty = 0;
		if (!playerPrimaryPosition.equals(playPosition)) {
			if (playPosition.equals("GK")) {
				penalty = 7;
			} else if (playPosition.equals("CB") || playPosition.equals("LB") || playPosition.equals("RB")) { // back
				if (playerPrimaryPosition.equals("LB") || playerPrimaryPosition.equals("RB") || playerPrimaryPosition.equals("CB")) {
					penalty = 3;
				} else if (playerPrimaryPosition.equals("RW") || playerPrimaryPosition.equals("PV") || playerPrimaryPosition.equals("LW")) {
					penalty = 5;
				} else if (playerPrimaryPosition.equals("GK")) {
					penalty = 15;
				}
			} else if (playPosition.equals("RW") || playPosition.equals("PV") || playPosition.equals("LW")) { // forward
				if (playerPrimaryPosition.equals("GK")) {
					penalty = 25;
				} else if (playerPrimaryPosition.equals("RW") || playerPrimaryPosition.equals("PV") || playerPrimaryPosition.equals("LW")) {
					penalty = 3;
				} else if (playerPrimaryPosition.equals("LB") || playerPrimaryPosition.equals("RB") || playerPrimaryPosition.equals("CB")) {
					penalty = 7;
				}
			}
		}
		
		
		
		
		return penalty;
		
	}
	
	// Sort players by formation
	public static ArrayList<Player> sortPlayerList(List<Player> playerList) {

		HashMap<String, Player> playerMap = new HashMap<String, Player>();
		// An array list to hold the sorted players by formation
		ArrayList<Player> sortedPlayerList = new ArrayList<Player>();

		int countReserves = 0;
		int countBench = 0;

		// Sort the list of Player entities
		for (int i = 0; i < playerList.size(); i++) {
			Player tempPlayer = playerList.get(i);
			String tempFormation = tempPlayer.getFormation();
			/*
			 * System.out.println("tempFormation (" + (i) + ") = " +
			 * tempFormation);
			 */

			// Sorting rules:
			// The first 7 players in the array must be the first squad
			// players
			// where the first is GK, second LW, third RW,
			// fourth is CB, fifth is RB, sixth is LB
			// and seventh is PV
			// The following 7 players have to be a bench players
			// FIXME currently having no sorting order

			// The other players are reserves and
			// FIXME currently having no sorting order

			// START Sorting the player list
			if (tempFormation.equals(Player.FORMATION_FIRST_SQUAD)) {
				String tempCurrentFirstSquadPlayerPlayPosition = tempPlayer
						.getFirst_squad_play_position();

				if (tempCurrentFirstSquadPlayerPlayPosition.equals("GK")) {
					playerMap.put("GK", tempPlayer);
				} else if (tempCurrentFirstSquadPlayerPlayPosition.equals("LW")) {
					playerMap.put("LW", tempPlayer);
				} else if (tempCurrentFirstSquadPlayerPlayPosition.equals("RW")) {
					playerMap.put("RW", tempPlayer);
				} else if (tempCurrentFirstSquadPlayerPlayPosition.equals("CB")) {
					playerMap.put("CB", tempPlayer);
				} else if (tempCurrentFirstSquadPlayerPlayPosition.equals("RB")) {
					playerMap.put("RB", tempPlayer);
				} else if (tempCurrentFirstSquadPlayerPlayPosition.equals("LB")) {
					playerMap.put("LB", tempPlayer);
				} else if (tempCurrentFirstSquadPlayerPlayPosition.equals("PV")) {
					playerMap.put("PV", tempPlayer);
				} else {
					System.out
							.println("Houston, we got a problem. Player position doesn't match any known prefix. Player position: "
									+ tempCurrentFirstSquadPlayerPlayPosition);
				}

			} else if (tempFormation.equals(Player.FORMATION_BENCH)) {
				playerMap.put("BP" + (countBench + 1), tempPlayer);
				countBench += 1;

			} else if (tempFormation.equals(Player.FORMATION_RESERVES)) {
				playerMap.put("RP" + (countReserves + 1), tempPlayer);
				countReserves += 1;

			}

			// END Sorting the player list
		} // END Loop for player entities
		/*
		 * System.out.println("Player map size = " + playerMap.size());
		 * System.out.println("Player map = " + playerMap);
		 */
		// Obtain the mapped players and insert into sortedPlayerList
		for (int i = 0; i < playerMap.size(); i++) {

			/* System.out.println("MAP ITERATION = " + (i + 1)); */

			if (i >= 0 && i < 7) {
				int d = new Integer(i);
				switch (d) {
				case 0:
					sortedPlayerList.add(playerMap.get("GK"));
					break;
				case 1:
					sortedPlayerList.add(playerMap.get("LW"));
					break;
				case 2:
					sortedPlayerList.add(playerMap.get("RW"));
					break;
				case 3:
					sortedPlayerList.add(playerMap.get("CB"));
					break;
				case 4:
					sortedPlayerList.add(playerMap.get("RB"));
					break;
				case 5:
					sortedPlayerList.add(playerMap.get("LB"));
					break;
				case 6:
					sortedPlayerList.add(playerMap.get("PV"));
					break;

				}
			} else if (i > 6 && i < 14) {
				/*
				 * System.out.println("BENCH PLAYER!!!!!!  " +
				 * playerMap.get("BP" + (i - 6)));
				 */
				sortedPlayerList.add(playerMap.get("BP" + (i - 6)));

			} else if (i > 13) {
				/*
				 * System.out.println("RESERVE PLAYER!!!!!");
				 * System.out.println("RP .... " + playerMap.get("RP" + (i -
				 * 13)));
				 */
				sortedPlayerList.add(playerMap.get("RP" + (i - 13)));
			}

		} // END loop for player map
		return sortedPlayerList;
	}
}
