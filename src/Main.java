public class Main {
    public static void main(String[] args) {
        SimulationFrame window = new SimulationFrame();
        SimulationPanel panel = new SimulationPanel();
        window.add(panel);
        window.setVisible(true);

        new SimulationPresenter(panel);
    }
}