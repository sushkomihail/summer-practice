import java.util.ArrayList;

public class Isolation {
    private final ArrayList<Unit> units = new ArrayList<>();

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void add(Unit infectedUnit) {
        units.add(infectedUnit);
    }
}
