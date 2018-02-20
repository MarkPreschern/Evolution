package Evolution;

import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.*;
public class EvolveObj
{
    private ArrayList<Obj> objects = new ArrayList<Obj>();
    public EvolveObj(ArrayList<Obj> old)
    {
        evolve(old);
    }
    public ArrayList<Obj> getObjects(){ //returns objects
        return objects;}
    public void evolve(ArrayList<Obj> old)
    {
       for(int i=0;i<2;i++)
       {
           for(int j=0;j<old.size()/2;j++)
           {
               create(old, j, i);
           }
       }
    }
    public void create(ArrayList<Obj> old, int location, int nameNumber)
    //creates n new objects
    {
        Random rand = new Random();
        int timeCount = 0;
        int nodes = old.get(location).getSize();
        double[] x = new double[nodes];
        double[] y = new double[nodes];
        double[] w = new double[nodes];
        double[] t = new double[nodes];
        for(int j=0;j<nodes;j++)
        {
            int tempX;
            int tempY; 
            if(nameNumber == 0)
            {
                tempX = (int)old.get(location).getX(j);
                tempY = (int)old.get(location).getY(j);
            }
            else
            {
                tempX = (int)old.get(location).getX(j) - 2 + rand.nextInt(5);
                tempY = (int)old.get(location).getY(j) - 2 + rand.nextInt(5);
            }
            x[j] = tempX;
            y[j] = tempY;
            timeCount = 0;  
        }
            
        Obj temp;
        if(nameNumber == 0)
            temp = new Obj(old.get(location).getName() + "",x,y,w,t,0,0,0,old.get(location).getEvolution() + 1);
        else
            temp = new Obj(old.get(location).getName() + "'",x,y,w,t,0,0,0,old.get(location).getEvolution() + 1);
        objects.add(temp);
    }   
}
