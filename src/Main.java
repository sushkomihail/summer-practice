import javax.swing.*;

public class Main {
    private static void trySetSystemTheme() {
        try {
            String systemThemeName = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(systemThemeName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        trySetSystemTheme();
        new SimulationFrame();
        new Simulation();
    }
}