
public class Experiment {

    public static void main(String[] args) {
        for(int nodes = 10; nodes < 100; nodes += 10) {
            for(int colors = 3; colors < 12; colors++){
                Main.createAlgorithmViewAndGUI(new int[]{nodes, colors});
            }
        }
        for(int nodes = 100; nodes < 1000; nodes+= 100){
            for(int colors = 3; colors < 12; colors++){
                Main.createAlgorithmViewAndGUI(new int[]{nodes, colors});
            }
        }
        for(int nodes = 1000; nodes < 10000; nodes+= 1000){
            for(int colors = 3; colors < 12; colors++){
                Main.createAlgorithmViewAndGUI(new int[]{nodes, colors});
            }
        }
    }


}