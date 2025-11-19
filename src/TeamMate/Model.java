package TeamMate;

import javax.xml.namespace.QName;

public class Model {
    public static class participant{
        private String id;
        private String name;
        private String email;
        private String game;
        private int skillLevel;
        private Role role;
        private int score ;
        private PersonalityType type;

        @Override
        public String toString() {
            return "participant [id=" + id + ", name=" + name + ", email=" + email + ", game=" + game + ", skillLevel=" + skillLevel + ", role=" + role + ", score=" + score + "]";
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getGame() {
            return game;
        }
        public void setGame(String game) {
            this.game = game;
        }
        public int getSkillLevel() {
            return skillLevel;
        }
        public void setSkillLevel(int skillLevel) {
            this.skillLevel = skillLevel;
        }
        public Role getRole() {
            return role;
        }
        public void setRole(Role role) {
            this.role = role;
        }
        public int getScore() {
            return score;
        }
        public void setScore(int score) {
            this.score = score;
        }
        public PersonalityType getType() {
            return type;
        }
        public void setType(PersonalityType type) {
            this.type = type;
        }
    }

    public enum Role{
        Strategist,
        Attacker,
        Defender,
        Supporter,
        Coordinator,
    }

    public enum PersonalityType{
        Leader,
        Balanced,
        Thinker
    }

}
