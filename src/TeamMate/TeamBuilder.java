package TeamMate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import TeamMate.Model.*;

public class TeamBuilder {
    final int TEAM_SIZE = 5;
    final int MAX_GAME_COUNT = 2;
    public List<Participant> ShuffleParticipants(List<Participant> participants) {
        Collections.shuffle(participants);
        participants.sort(Comparator.comparing(Participant::getName).reversed());
        return participants;
    }

    public List<Team> CreateTeams(List<Participant> participants) {
        int num = 0;
        int teamCount = (int) Math.ceil((double) participants.size() /TEAM_SIZE);
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < teamCount; i++) {
            teams.add(new Team(i + 1));
        }
        for  (Participant p : participants) {
            Team team = teams.get(num % teamCount);
            team.addMember(p);
            num++;
        }
        return teams;
    }
    public boolean canAddToTeam(Team team, Participant p,int teamSize) {
        int leaderCount = 0;
        int thinkerCount = 0;
        int sameGameCount = 0;
        if (team.getMembers().size() >= teamSize) {
            return false;
        }
        for (Participant p1: team.getMembers()){
            if (p1.getType() == PersonalityType.leader) {
                leaderCount++;
            }else if (p1.getType() == PersonalityType.thinker) {
                thinkerCount++;
            }else if (p1.getGame().equalsIgnoreCase(p.getGame())) {
                sameGameCount++;
            }
        }
        if (sameGameCount == MAX_GAME_COUNT) {
            return false;
        } else if (leaderCount == 1) {
            return false;
        }else if (thinkerCount >= 2) {
            return false;
        }
        return true;
    }

    public boolean hasRoleDiversity(Team team){
        long distinctRoles = team.getMembers().stream().map(Participant::getRole).distinct().count();
        return distinctRoles >=3;
    }

    public boolean hasGoodPersonalityMix(Team team){
        long leaders = team.getMembers().stream().filter(p -> p.getType() == PersonalityType.leader).count();
        long thinkers =  team.getMembers().stream().filter(p -> p.getType() == PersonalityType.thinker).count();
        return leaders >=1 && thinkers >= 1 && thinkers <=2;
    }
}
