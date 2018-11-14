package Evolution;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.util.ArrayList;
public class NewSimulation
{
    public static void create(int evolutionDepth, int trials)
    {
        JFrame screen = new JFrame();
        CreateObj create = new CreateObj(20);
        DisplayObj display = new DisplayObj(create.getObjects(), evolutionDepth, trials);
        screen.add(display);
        screen.setSize(1280,720);
        screen.setVisible(true);
        screen.setLocationRelativeTo(null);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}