public class Model {
    public static class participant{
        private String id;
        private String name;
        private String game;
        private Role role;
        private int skill;
        private int score ;
        private PersonalityType type;

    }

    public enum Role{
        STRATEGIST,
        ATTACKER,
        DEFENDER,
        SUPPORTER,
        COORDINATOR,
    }

    public enum PersonalityType{
        LEADER,
        BALANCED,
        THINKER
    }


}
