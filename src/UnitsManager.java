import java.util.ArrayList;

public class UnitsManager {
    private final ArrayList<Unit> units = new ArrayList<>();

    private static UnitsManager instance;

    public UnitsManager() {
        instance = this;
    }

    public static UnitsManager getInstance() {
        if (instance == null) {
            instance = new UnitsManager();
        }

        return instance;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }
}
