package TeamMate;

import java.util.Scanner;
import TeamMate.Model.*;

public class Register {
    public void PersonalityClassifier(Participant p,Scanner sc){
        System.out.print("I enjoy taking charge and leading others when working in a team.  -->");
        int Q1 = sc.nextInt();
        System.out.print("I stay calm and think carefully before making decisions. -->");
        int Q2 = sc.nextInt();
        System.out.print("I communicate clearly and help my group stay organised. -->");
        int Q3 = sc.nextInt();
        System.out.print("I like solving complex problems and analysing situations deeply. -->");
        int Q4 = sc.nextInt();
        System.out.print("I adapt easily when plans change or when unexpected issues appear. -->");
        int Q5 = sc.nextInt();
        int score = (Q1+Q2+Q3+Q4+Q5)*4;
        p.setScore(score);

        PersonalityType type;
        if (score >= 90){
            type = PersonalityType.leader;
        }else if (score >= 70){
            type = PersonalityType.balanced;
        }else if (score >= 50){
            type = PersonalityType.thinker;
        }else{
            type = PersonalityType.thinker;
        }
        p.setType(type);
    }

    public Participant ParticipantInfo(Scanner sc) {
        System.out.print("Enter the id of the participant :");
        String id = sc.nextLine();
        System.out.print("Enter the name of the participant :");
        String name = sc.nextLine();
        System.out.print("Enter the email of the participant :");
        String email = sc.nextLine();
        System.out.print("Enter the preferred game of the participant :");
        String game = sc.nextLine().toLowerCase();
        System.out.print("Enter the skill level of the participant(1 - 10) :");
        int skillLevel = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the preferred Role of the participant :");
        String input = sc.nextLine();
        input = input.toLowerCase();
        Model.Role role = Model.Role.valueOf(input);
        Model.Participant p = new Model.Participant();
        p.setId(id);
        p.setName(name);
        p.setEmail(email);
        p.setGame(game);
        p.setSkillLevel(skillLevel);
        p.setRole(role);
        return p;
    }
}
