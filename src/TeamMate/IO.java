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
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));
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
        List<Participant> participants = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) {
                    System.out.println("[SKIPPED] Empty line");
                    continue;
                }
                String[] data = line.split(",");
                if(data.length < 8) {
                    System.out.println("[SKIPPED] Missing columns: " + line);
                    continue;
                }
                try {
                    String id = safe(data[0].trim());
                    String name = safe(data[1].trim());
                    String email = safe(data[2].trim());
                    String game = safe(data[3].trim().toLowerCase());
                    int skillLevel = Integer.parseInt(safe(data[4].trim()));
                    Role role = Role.valueOf(safe(data[5].trim()).toLowerCase());
                    int score = Integer.parseInt(safe(data[6].trim()));
                    PersonalityType type = PersonalityType.valueOf(safe(data[7].trim()).toLowerCase());
                    if (id == null || name == null || email == null || game == null) {
                        System.out.println("[SKIPPED] Missing required field: " + line);
                        continue;
                    }

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
                }catch (Exception ex){
                    System.out.println("[SKIPPED] Invalid data: " + line);
                    System.out.println(ex.getMessage());
                    continue;
                }
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found \n"+ e.getMessage());
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error reading file \n"+ e.getMessage());
        }
        return participants;
    }


    public void writeCSV(List<Team> teams, String filepath) {
        try (PrintWriter writer = new PrintWriter(filepath)){
            writer.println("Team_ID,Participant_ID,Name,Email,Game,Skill_Level,Role,Personality_Score,Personality_Type");
            for (Team team : teams){
                for (Participant p : team.getMembers()){
                    writer.println(team.getId()+","+p.getId()+","+p.getName()+","+p.getEmail()+","+p.getGame()+","+p.getSkillLevel()+","+p.getRole()+","+p.getScore()+","+p.getType());
                }
            }
            System.out.println("file Created successfully.");
        }catch (FileNotFoundException e) {
            System.out.print(" ERROR: Could not write file: " + filepath);
        }
    }

    private String safe(String s){
        if (s == null)return null;
        s = s.trim();
        if(s.isEmpty() || s.equalsIgnoreCase("null"))return null;
        return s;
    }
}
