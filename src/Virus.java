public class Virus {
    private int infectionRadius = 30;
    private float infectionInterval = 0.5f;
    private float infectionProbability = 0.5f;
    private float detectionTime = 2;
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

    public float getDetectionTime() {
        return detectionTime;
    }

    public float getRecoveryTime() {
        return recoveryTime;
    }

    public void setInfectionRadius(int infectionRadius) {
        this.infectionRadius = infectionRadius;
    }

    public void setInfectionInterval(float infectionInterval) {
        this.infectionInterval = infectionInterval;
    }

    public void setInfectionProbability(float infectionProbability) {
        this.infectionProbability = infectionProbability;
    }

    public void setDetectionTime(float detectionTime) {
        this.detectionTime = detectionTime;
    }

    public void setRecoveryTime(float recoveryTime) {
        this.recoveryTime = recoveryTime;
    }
}
