package TeamMate;

import java.util.*;

import TeamMate.Model.*;

public class TeamBuilder {
    final int MAX_GAME_COUNT = 2;
    final int MAX_TEAM_SIZE = 5;
    public void CreateTeams(List<Participant> participants,List<Team> teams) {
        List<Participant> leaderList = CreatePersonalityTypeList(participants,PersonalityType.leader);
        int leaderCount = leaderList.size();
        for  (int i = 1; i <= leaderCount; i++) {
            Team team = new Team(i);
            if (!leaderList.isEmpty()){
                Participant leader = leaderList.getFirst();
                team.addMember(leader);
                leaderList.remove(leader);
            }
            teams.add(team);
        }
    }
    public void addRestOfTheMembers(List<Participant> participants,List<Team> teams) {
        List<Participant> thinkerList = CreatePersonalityTypeList(participants,PersonalityType.thinker);
        List<Participant> balancedList = CreatePersonalityTypeList(participants,PersonalityType.balanced);
        List<Participant> pool = new ArrayList<>();
        pool.addAll(thinkerList);
        pool.addAll(balancedList);
        pool.sort(Comparator.comparingInt(Participant::getSkillLevel).reversed());

        int totalSkill = teams.stream().mapToInt(this::getTeamTotalSkill).sum() +pool.stream().mapToInt(Participant::getSkillLevel).sum();
        int teamCount = teams.size();
        double targetPerTeam = (double) totalSkill / (double) teamCount;
        for (Participant p : pool) {
            Team bestTeam = chooseBestTeamFor(p,teams,targetPerTeam);
            if (bestTeam != null) {
                bestTeam.addMember(p);
            }
        }
    }

    public List<Participant> CreatePersonalityTypeList(List<Participant> participants,PersonalityType type) {
        List<Participant> list = new ArrayList<>();
        for (Participant p : participants) {
            if (p.getType()== type) {
                list.add(p);
            }
        }
        Collections.shuffle(list);
        return list;
    }

    private long thinkerCount(Team team) {
        return team.getMembers().stream().filter(m -> m.getType()==PersonalityType.thinker).count();
    }

    private int getTeamTotalSkill(Team team) {
        return team.getMembers().stream().mapToInt(Participant::getSkillLevel).sum();
    }

    private int getSameGameCount(Team team,Participant p) {
        return Math.toIntExact(team.getMembers().stream().filter(m -> m.getGame().equals(p.getGame())).count());
    }
    private long getDistinctRoleCounts(Team team) {
        return team.getMembers().stream().map(Participant::getRole).distinct().count();
    }
    private Team chooseBestTeamFor(Participant p,List<Team> teams,double targetPerTeam) {
        PersonalityType type = p.getType();
        String role = p.getRole().toString();
        Team bestTeam = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        for (Team team : teams) {
            if (team.getMembers().size() >= MAX_TEAM_SIZE) {
                continue;
            }
            if (getSameGameCount(team,p) >= MAX_GAME_COUNT) {
                continue;
            }
            if(type == PersonalityType.leader) {
                continue;
            }
            long thinkerCount = thinkerCount(team);
            if(type == PersonalityType.thinker &&  thinkerCount >=2 ){
                continue;
            }
            long teamSkillBefore = getTeamTotalSkill(team);
            long teamSkillAfter = teamSkillBefore + p.getSkillLevel();

            double score = 0.0;

            if (type == PersonalityType.thinker && thinkerCount == 0){
                score += 50;
            }
            long rolesBefore = getDistinctRoleCounts(team);
            boolean roleNew = team.getMembers().stream().noneMatch(m -> m.getRole() == p.getRole());
            if (roleNew) {
                score += (rolesBefore < 3) ? 30.0 : 10.0;
            }
            double diffBefore = Math.abs(teamSkillBefore - targetPerTeam);
            double diffAfter = Math.abs(teamSkillAfter - targetPerTeam);

            score += diffBefore + diffAfter;
            if (score>bestScore) {
                bestScore = score;
                bestTeam = team;
            }
        }
        return bestTeam;
    }
}
