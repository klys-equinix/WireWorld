import java.awt.*;

/**
 * Created by Konrad on 20.04.2017.
 */
public class WireWorld {
    public static void main(String args[]){
        final Simulation sim = new Simulation(12,"./testFile");
        sim.start();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                basicGraphics one = new basicGraphics(sim.getMemory());
                one.setVisible(true);
            }
        });
    }
}
