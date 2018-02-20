package Evolution;

import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.*;
public class CreateObj
{
    private ArrayList<Obj> objects = new ArrayList<Obj>();
    public CreateObj(int n)
    //constuctor
    {
        System.out.print('\u000C');
        double timeD = (n/715);
        int timeI = (int)(n/715) + 1;
        if(timeD % timeI >= .5)
            timeI += 1;
        System.out.println("Creating " + n + " objects\nEstimated Time: " + timeI + " Seconds");
        create(n);
    }
    public ArrayList<Obj> getObjects(){ //returns objects
        return objects;}
    public void create(int n)
    //creates n new objects
    {
        Random rand = new Random();
        int timeCount = 0;
        for(int i=0;i<n;i++)
        {
            int nodes = rand.nextInt(17) + 4;
            double[] x = new double[nodes];
            double[] y = new double[nodes];
            double[] w = new double[nodes];
            double[] t = new double[nodes];
            for(int j=0;j<nodes;j++)
            {
                int tempX = rand.nextInt(51);
                int tempY = rand.nextInt(51);
                if(timeCount > 10000)
                    j = 0;
                if(intersects(tempX, tempY, x, y, j, nodes) == true)
                {
                    j--;
                    timeCount++;
                }
                else
                {
                    x[j] = tempX;
                    y[j] = tempY;
                    timeCount = 0;
                }                
            }
            for(int z=0;z<nodes;z++)
                y[z] += 99;
            Obj temp = new Obj(generateName(),x,y,w,t,0,0,0,0);
            objects.add(temp);
        }
    }   
    public boolean intersects(int thisX, int thisY, double[] x, double[] y, int thisNode, int nodes)
    //checks if the new point being generated makes an intersection with already existing lines
    {
        for(int i=0;i<thisNode-1;i++)
        {
            if(i != 0)
            {
                if(thisNode == nodes - 1)
                {
                    Line2D line11 = new Line2D.Float(thisX, thisY, (int)x[0], (int)y[0]);
                    if(i != 1)
                    {
                        Line2D line22 = new Line2D.Float((int)x[i], (int)y[i], (int)x[i-1], (int)y[i-1]);
                        if(line22.intersectsLine(line11) == true)
                        return true;
                    }
                    Line2D line33 = new Line2D.Float((int)x[i], (int)y[i], (int)x[i+1], (int)y[i+1]);
                    if(line33.intersectsLine(line11) == true)
                        return true;
                }
                Line2D line1 = new Line2D.Float(thisX, thisY, (int)x[thisNode - 1], (int)y[thisNode - 1]);
                Line2D line2 = new Line2D.Float((int)x[i], (int)y[i], (int)x[i-1], (int)y[i-1]);
                if(line2.intersectsLine(line1) == true)
                    return true;
            }
        }
        return false;
    }
    public String generateName()
    //generates a random string
    {
        Random rand = new Random();
        String str = "";
        String Upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Lower = "abcdefghijklmnopqrstuvwxyz";
        str = Character.toString(Upper.charAt(rand.nextInt(Upper.length()))) + Character.toString(Lower.charAt(rand.nextInt(Lower.length())))
        + Character.toString(Lower.charAt(rand.nextInt(Lower.length()))) + Integer.toString(rand.nextInt(10));
        return str;
    }
}
