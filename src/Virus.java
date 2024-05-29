public class Virus {
    private int infectionRadius = 30;
    private float infectionInterval = 0.5f;
    private float infectionProbability = 0.5f;
    private float recoveryTime = 4;

    public int getInfectionRadius() {
        return infectionRadius;
    }

    public float getInfectionInterval() {
        return infectionInterval;
    }

    public float getInfectionProbability() {
        return infectionProbability;
    }

    public float getRecoveryTime() {
        return recoveryTime;
    }
}
