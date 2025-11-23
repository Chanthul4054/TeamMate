package TeamMate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import TeamMate.Model.*;


public class Main {
    public static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int menu = 0;
        IO io = new IO();
        Register reg = new Register();
        TeamBuilder tm = new TeamBuilder();
        System.out.println("            ████████╗███████╗ █████╗ ███╗   ███╗    ███╗   ███╗ █████╗ ████████╗███████╗");
        System.out.println("            ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║    ████╗ ████║██╔══██╗╚══██╔══╝██╔════╝");
        System.out.println("               ██║   █████╗  ███████║██╔████╔██║    ██╔████╔██║███████║   ██║   █████╗  ");
        System.out.println("               ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║    ██║╚██╔╝██║██╔══██║   ██║   ██╔══╝  ");
        System.out.println("               ██║   ███████╗██║  ██║██║ ╚═╝ ██║    ██║ ╚═╝ ██║██║  ██║   ██║   ███████╗");
        System.out.println("               ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝    ╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝");
        System.out.println("                    INTELLIGENT TEAM FORMATION SYSTEM FOR UNIVERSITY GAMING CLUB");
        List<Participant> participants = new  ArrayList<>();
        List<Team> teams = new  ArrayList<>();
        do {
            menu = displayMenu(sc);
            switch (menu) {
                case 1:
                    List<Participant> loaded = io.openCSV();
                    participants.addAll(loaded);
                    break;
                case 2:
                    Participant NewP = reg.ParticipantInfo(sc);
                    participants.add(reg.PersonalityClassifier(NewP,sc));
                    break;
                case 3:
                    System.out.println(participants);
                    break;
                case 4:
                    tm.CreateTeams(participants, teams);
                    tm.addRestOfTheMembers(participants, teams);
                    break;
                case 5:
                    System.out.println(teams);
                    break;
                case 6:
                    io.writeCSV(teams,"C:/Users/chant/OneDrive/Documents/Viva/formed_teams.csv");
                    break;
                default:
                    System.out.println("\n Invalid choice. Please choose a number between 1 - 7 \n");
            }
        }while(menu!=7);
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
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice ;
    }
}
