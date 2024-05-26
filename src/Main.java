public class Main {
    public static void main(String[] args) {
        SimulationFrame frame = new SimulationFrame();
        SimulationPanel panel = new SimulationPanel();
        frame.add(panel);
        frame.setVisible(true);

        new SimulationPresenter(panel);
    }
}