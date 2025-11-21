package TeamMate;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import TeamMate.Model.*;


public class IO {

    public List<Participant> openCSV(){
        JFileChooser fileChooser = new JFileChooser();
        int option  = fileChooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            System.out.println("File Selected "+file.getAbsolutePath());
            return readCSV(file);
        }else {
            System.out.println("No file selected");
            return Collections.emptyList();
        }
    }

    public List<Participant> readCSV(File file) {
        List<Participant> participants;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            participants = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                String name = data[1].trim();
                String email = data[2].trim();
                String game = data[3].trim();
                int skillLevel = Integer.parseInt(data[4].trim());
                Role role = Role.valueOf(data[5].trim());
                int score = Integer.parseInt(data[6].trim());
                PersonalityType type = PersonalityType.valueOf(data[7].trim());

                Participant p = new Participant();
                p.setId(id);
                p.setName(name);
                p.setEmail(email);
                p.setGame(game);
                p.setSkillLevel(skillLevel);
                p.setRole(role);
                p.setScore(score);
                p.setType(type);
                participants.add(p);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return participants;
    }
}
