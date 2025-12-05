package TeamMate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import TeamMate.Model.*;


public class Main {
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int menu ;
        IO io = new IO();
        Register reg = new Register();
        TeamBuilder tm = new TeamBuilder();
        System.out.print("\nStarting System ......");
        delay(2000);
        System.out.print("\r           \r");
        System.out.println("            ████████╗███████╗ █████╗ ███╗   ███╗    ███╗   ███╗ █████╗ ████████╗███████╗");
        System.out.println("            ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║    ████╗ ████║██╔══██╗╚══██╔══╝██╔════╝");
        System.out.println("               ██║   █████╗  ███████║██╔████╔██║    ██╔████╔██║███████║   ██║   █████╗  ");
        System.out.println("               ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║    ██║╚██╔╝██║██╔══██║   ██║   ██╔══╝  ");
        System.out.println("               ██║   ███████╗██║  ██║██║ ╚═╝ ██║    ██║ ╚═╝ ██║██║  ██║   ██║   ███████╗");
        System.out.println("               ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝    ╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝");
        System.out.println("                    INTELLIGENT TEAM FORMATION SYSTEM FOR UNIVERSITY GAMING CLUB");
        List<Participant> participants = new ArrayList<>();
        List<Team> teams = new ArrayList<>();
        do {
            menu = displayMenu(sc);
            switch (menu) {
                case 1:
                    System.out.println("Enter Username: ");
                    String user = sc.nextLine();
                    System.out.println("Enter Password: ");
                    String password = sc.nextLine();
                    if (user.equals("admin") && password.equals("admin")) {
                        System.out.println("Access granted");
                        List<Participant> loaded = io.openCSV();
                        participants.addAll(loaded);
                        displayMenu(sc);
                    }else {
                        System.out.println("Access denied");
                        continue;
                    }

                case 2:
                    Participant NewP = reg.ParticipantInfo(sc);
                    participants.add(reg.PersonalityClassifier(NewP, sc));
                    break;
                case 3:
                    if(!participants.isEmpty()) {
                        System.out.println(participants);
                    }else {
                        System.out.println("\nParticipants are Empty.Please register a participant or load a CSV file\n");
                    }
                    break;
                case 4:
                    if(!participants.isEmpty()) {
                        System.out.print("Enter team size :");
                        int teamSize = sc.nextInt();
                        sc.nextLine();
                        tm.CreateTeams(participants, teams);
                        tm.addRestOfTheMembers(participants, teams, teamSize);
                        System.out.println("\nCreating Teams\n");
                        delay(2000);
                        System.out.println("Teams Successfully Created\n");
                    }else {
                        System.out.println("\nParticipants are Empty.Please register a participant or load a CSV file\n");
                    }
                    break;
                case 5:
                    if (!teams.isEmpty()) {
                        System.out.println(teams);
                    }else{
                        System.out.println("Team are Empty.Please form teams");
                    }
                    break;
                case 6:
                    System.out.println("Enter Username: ");
                    String user1 = sc.next();
                    System.out.println("Enter Password: ");
                    String password1 = sc.next();
                    if (user1.equals("admin") && password1.equals("admin")) {
                        System.out.println("Access granted");
                        io.writeCSV(teams, "C:/Users/chant/OneDrive/Documents/Viva/formed_teams.csv");
                    }else {
                        System.out.println("Access denied");
                        break;
                    }
                    break;
                default:
                    System.out.println("\n Invalid choice. Please choose a number between 1 - 7 \n");
            }
        } while (menu != 7);
        sc.close();
    }

    public static int displayMenu(Scanner sc) {
        System.out.println("-----------------------------------------------------------");
        System.out.println("""
                1. Load data from a CSV file.
                2. Register participant manually.\s
                3. See all participants.\s
                4. Form teams.\s
                5. See all teams\s
                6. Save formed teams to a CSV file.
                7. Exit\s""");
        System.out.println("-----------------------------------------------------------");
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 1 && choice <= 7) {
                    return choice;
                } else {
                    System.out.println("\nInvalid choice. Enter a number between 1–7.\n");
                }
            } else {
                System.out.println("\nInvalid input. Please enter a number.\n");
                sc.next();
            }
        }
    }

    public static void delay(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
