import java.awt.*;

public class Virus {
    private int infectionRadius;
    private int sneezingInterval;
    private float infectionProbability;

    public Virus(int infectionRadius, int sneezingInterval, float infectionProbability) {
        this.infectionRadius = infectionRadius;
        this.sneezingInterval = sneezingInterval;
        this.infectionProbability = infectionProbability;
    }

    public int getInfectionRadius() {
        return infectionRadius;
    }

    public int getSneezingInterval() {
        return sneezingInterval;
    }

    public float getInfectionProbability() {
        return infectionProbability;
    }
}
