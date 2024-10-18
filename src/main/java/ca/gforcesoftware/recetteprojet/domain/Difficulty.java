package ca.gforcesoftware.recetteprojet.domain;

/**
 * @author gavinhashemi on 2024-10-10
 */
public enum Difficulty {
    EASIEST("super easy"), EASY("easy"),MEDIUM("medium"),SOMEWHAT_HARD("Some what hard"),HARD("hard");
    private String description;
    Difficulty(String s){
        this.description = s;
    }
    public String getDescription() {
        return description;
    }
}
