package TeamMate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import TeamMate.Model.*;

public class Register {
    public int askQuestion(Scanner sc, String question) {
        int answer;
        while (true) {
            System.out.print(question + " (1–5) --> ");
            if (sc.hasNextInt()) {
                answer = sc.nextInt();
                sc.nextLine();
                if (answer >= 1 && answer <= 5) {
                    return answer;
                } else {
                    System.out.println("Please enter a number between 1 and 5.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
            }
        }
    }


    public Participant PersonalityClassifier(Participant p, Scanner sc) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<int[]> answersFuture = executor.submit(() -> {
            int[] answers = new int[5];
            answers[0] = askQuestion(sc, "I enjoy taking charge and leading others when working in a team.");
            answers[1] = askQuestion(sc, "I stay calm and think carefully before making decisions.");
            answers[2] = askQuestion(sc, "I communicate clearly and help my group stay organised.");
            answers[3] = askQuestion(sc, "I like solving complex problems and analysing situations deeply.");
            answers[4] = askQuestion(sc, "I adapt easily when plans change or when unexpected issues appear.");
            return answers;
        });
        Future<Participant> classifyFuture = executor.submit(() -> {
            int[] a = answersFuture.get();
            int score = (a[0] + a[1] + a[2] + a[3] + a[4]) * 4;
            p.setScore(score);


            PersonalityType type;
            if (score >= 90) {
                type = PersonalityType.leader;
            } else if (score >= 70) {
                type = PersonalityType.balanced;
            } else if (score >= 50) {
                type = PersonalityType.thinker;
            } else {
                type = PersonalityType.thinker;
            }
            p.setType(type);
            System.out.println("\n[participant added ]" + p);
            return p;
        });
        Participant result = classifyFuture.get();
        executor.shutdown();
        return result;
    }


    public Participant ParticipantInfo(Scanner sc) {
        String id;
        while (true) {
            System.out.print("Enter participant ID (e.g., P001): ");
            id = sc.nextLine().trim();
            if (id.matches("P\\d{3}")) break;
            System.out.println("Invalid ID format. Example: P001");
        }
        String name;
        while (true) {
            System.out.print("Enter participant name: ");
            name = sc.nextLine().trim();
            if(!name.isEmpty()) break;
            System.out.println("Name cannot be empty.");
        }
        String email;
        while (true) {
            System.out.print("Enter participant email: ");
            email = sc.nextLine().trim();
            if(email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))break;
            System.out.println("Invalid email");
        }
        String[] validGames = {"valorant", "dota 2", "chess", "basketball", "fifa", "cs:go"};
        String game;
        while (true) {
            System.out.print("Enter participants preferred game \n(Valorant, Dota 2, Chess, Basketball, FIFA, CS:GO): ");
            game = sc.nextLine().trim();
            if(Arrays.asList(validGames).contains(game)) break;
            System.out.println("Invalid game name.Choose from these games:");
        }
        int skillLevel = -1;
        while (true) {
            System.out.print("Enter participant skill level (1 - 10): ");
            if(sc.hasNextInt()){
                skillLevel = sc.nextInt();
                sc.nextLine();
                if(skillLevel >= 1 && skillLevel < 11) break;
                System.out.println("skill level must be between 1 and 10");
            }else {
                System.out.println("Please enter a number.");
                sc.next();
            }
        }
        Role role = null;
        while (true) {
            System.out.println("Enter participant's preferred role\n (strategist, attacker, defender, supporter, coordinator):");
            String roleName = sc.nextLine().trim().toLowerCase();
            try {
                role = Model.Role.valueOf(roleName);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(" Invalid role. Choose from the list.");
            }
        }

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
