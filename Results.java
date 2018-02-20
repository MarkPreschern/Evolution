package Evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.RenderingHints;
import java.util.ArrayList;
public class Results extends JPanel implements ActionListener
{
    private Timer t = new Timer(1,this);
    private final ArrayList<EvolutionData> EI;
    public Results(ArrayList<EvolutionData> EI)
    {
        t.start();
        this.EI = EI;
    }    
    public void paintComponent(Graphics g)
    //displays images
    {
        super.paintComponent(g);
                        
        Graphics2D main = (Graphics2D) g;
        main.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int i=0;i<EI.size();i++)
        {
            int[] xCords = new int[EI.get(i).getObj().getSize()];
            int[] yCords = new int[EI.get(i).getObj().getSize()];
            for(int j=0;j<EI.get(i).getObj().getSize();j++)
            {
                xCords[j] = (int)EI.get(i).getObj().getX(j) + (i%10)*120 + 50;
                yCords[j] = (int)EI.get(i).getObj().getY(j) + ((int)i/10)*120 + 50 - 100;
            }
            Polygon poly = new Polygon(xCords, yCords, EI.get(i).getObj().getSize());
            main.setColor(Color.green);
            main.fillPolygon(poly);
            main.setColor(Color.black);
            main.drawPolygon(poly);
            
            String str1 = EI.get(i).getObj().getName().substring(0,4);
            String str2 = Integer.toString(EI.get(i).getHeight());
            String str3 = Integer.toString(EI.get(i).getMean());
            main.setColor(Color.black);
            Font font = new Font("Verdana", Font.BOLD, 15);
            main.setFont(font);
            main.drawString(str1, (i%10)*120 + 50, ((int)i/10)*120 + 40);
            main.drawString(str2, (i%10)*120 + 50, ((int)i/10)*120 + 120);
            main.drawString(str3, (i%10)*120 + 50, ((int)i/10)*120 + 135);
        }
    }
    public void actionPerformed(ActionEvent e)
    //checks for updated actions according to timer
    {
        repaint();
    }
}
