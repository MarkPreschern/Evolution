package Evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class DisplayObj extends JPanel implements ActionListener
{
    private Timer t = new Timer(1,this);
    private long start = System.nanoTime();
    private long[] rotTime;
    private ArrayList<Integer> location = new ArrayList<Integer>();
    private ArrayList<Obj> obj;
    private GeneralPath ground;
    private ArrayList<String[][]> database = new ArrayList<String[][]>();
    
    private ArrayList<double[]> initialX;
    private ArrayList<double[]> initialY;
    
    private int evolutionDepth;
    private int trials, trialsCounter;
    private ArrayList<EvolutionData> EI = new ArrayList<EvolutionData>();
    
    private boolean endSimulation = false;
    public DisplayObj(ArrayList<Obj> objects, int evolutionDepth, int trials)
    //constructor
    {
        t.start();
        obj = objects;
        this.evolutionDepth = evolutionDepth;
        this.trials = trials;
        trialsCounter = trials;
        rotTime = new long[objects.size()];
        createPath();
        getInitialCords();
    }
    public void createPath()
    //creates the ground
    {
        GeneralPath path = new GeneralPath();
        path.moveTo(0,150);
        path.quadTo(100,800,300,500);
        path.lineTo(300,700);
        path.lineTo(0,700);
        path.closePath();
        ground = path;
    }
    public void getInitialCords()
    //sets the initial cords of the objs
    {
        ArrayList<double[]> x = new ArrayList<double[]>();
        ArrayList<double[]> y = new ArrayList<double[]>();
        ArrayList<Obj> temp = new ArrayList<Obj>(obj);
        for(int i=0;i<temp.size();i++)
        {
            x.add(Arrays.copyOf(temp.get(i).getX(), temp.get(i).getSize()));
            y.add(Arrays.copyOf(temp.get(i).getY(), temp.get(i).getSize()));
        }
        initialX = x;
        initialY = y;
    }
    public void paintComponent(Graphics g)
    //displays images
    {
        super.paintComponent(g);
                        
        Graphics2D layout = (Graphics2D) g;
        layout.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layout.setColor(Color.lightGray);
        layout.fill(ground);
        layout.setColor(Color.black);        
        layout.draw(ground);
        layout.setColor(Color.lightGray);
        layout.fillRect(getWidth()-480,0,getWidth(),getHeight());
        layout.setColor(Color.black);
        layout.drawLine(getWidth()-480,0,getWidth()-480,getHeight());
        layout.drawLine(getWidth()-479,0,getWidth()-479,getHeight());
        layout.fillRect(0,0,1280,2);
        layout.fillRect(0,getHeight()-2,1280,2);
        layout.fillRect(0,0,2,getHeight());
        layout.fillRect(1278,0,2,getHeight());
        
        for(int i=0;i<=300;i++)
        {
            layout.setColor(Color.red);
            int x = i - 182;
            if(x >= 0)
                layout.fill(new Ellipse2D.Double(i,594 - .007*x*x,1,1));
            else
                layout.fill(new Ellipse2D.Double(i,594 - (.013 - .00003*i)*x*x,1,1));
        }
        
        Graphics2D leaderboard = (Graphics2D) g;
        leaderboard.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        leaderboard.setColor(Color.black);
        leaderboard.drawLine(800,50,1280,50);
        leaderboard.drawLine(850,0,850,400);
        leaderboard.drawLine(1050,0,1050,400);
        Font header = new Font("Verdana",Font.BOLD,40);
        leaderboard.setFont(header);
        int hashheaderwidth = g.getFontMetrics(header).stringWidth("#");
        int nameheaderwidth = g.getFontMetrics(header).stringWidth("NAME");
        int massheaderwidth = g.getFontMetrics(header).stringWidth("DIST");
        leaderboard.drawString("#",825 - hashheaderwidth/2,40);
        leaderboard.drawString("NAME",950 - nameheaderwidth/2,40);
        leaderboard.drawString("DIST",1165 - massheaderwidth/2,40);
        if(database.size() != 0){
            for(int i=0;i<10;i++)
            {           
                Font lists = new Font("Verdana",Font.PLAIN,30);
                leaderboard.setFont(lists);
                int numwidth = g.getFontMetrics(lists).stringWidth(Integer.toString(i+1));
                int namewidth = g.getFontMetrics(lists).stringWidth(database.get(database.size()-1)[i][1]);
                int masswidth = g.getFontMetrics(lists).stringWidth(database.get(database.size()-1)[i][1]);
                leaderboard.drawString(Integer.toString(i+1),825 - numwidth/2,80 + 35*i);
                leaderboard.drawString(database.get(database.size()-1)[i][1],950 - namewidth/2,80 + 35*i);
                leaderboard.drawString(database.get(database.size()-1)[i][0],1165 - masswidth/2,80 + 35*i);
            }
        }
        
        Graphics2D graph = (Graphics2D) g;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setColor(Color.black);
        graph.drawLine(850,630,1200,630);
        graph.drawLine(850,450,850,630);
        Font gen = new Font("Verdana",Font.BOLD,30);
        graph.setFont(gen);
        String gens = "Generation: " + (obj.get(0).getEvolution());
        int genswidth = g.getFontMetrics(gen).stringWidth(gens);
        graph.drawString(gens,1040 - genswidth/2,680);
        Font num = new Font("Verdana",Font.PLAIN,10);
        graph.setFont(num);
        int sizewidth = g.getFontMetrics(num).stringWidth("Size");
        int genwidth = g.getFontMetrics(num).stringWidth("Generation");
        graph.rotate(Math.toRadians(-90),845,540 + sizewidth/2);
        graph.drawString("Size",845,540 + sizewidth/2);
        graph.rotate(Math.toRadians(90),845,540 + sizewidth/2);
        graph.drawString("Generation",1025 - genwidth/2,645);
        if(database.size() != 0)
        {
            int[] highestHeights = getHighestHeights();
            int[] meanHeights = getMeanHeights();
            int low = getLowest(meanHeights);
            int high = getHighest(highestHeights);
            String smallest = Integer.toString(low);
            String largest = Integer.toString(high);
            int lowwidth = g.getFontMetrics(num).stringWidth(smallest);
            int highwidth = g.getFontMetrics(num).stringWidth(largest);
            if(database.size() > 1 && low != high)
                graph.drawString(smallest,840 - lowwidth/2,625);
            graph.drawString(largest,840 - highwidth/2,460);
            if(database.size() > 1)
                graph.drawString("1",855,645);
            String lg = Integer.toString((obj.get(0).getEvolution()));
            int lgw = g.getFontMetrics(num).stringWidth(lg);
            graph.drawString(lg,1200 - lgw/2,645);
            graph.setColor(Color.red);
            if(database.size() == 1)
                graph.fill(new Ellipse2D.Double(1200,460,2,2));
            else if(database.size() > 1)
            {
                double[][] cords = new double[obj.get(0).getEvolution()][2];
                double[][] cords2 = new double[obj.get(0).getEvolution()][2];
                for(int i=0;i<obj.get(0).getEvolution();i++)
                {
                    double width = 855 + (345/(obj.get(0).getEvolution()-1))*(i);
                    double height;
                    if(low == high)
                        height = 625;
                    else
                        height = 625 - (165*((double)(highestHeights[i] - low) / (double)(high - low)));
                    cords[i][0] = width; cords[i][1] = height;
                }
                for(int i=0;i<obj.get(0).getEvolution();i++)
                {
                    double width = 855 + (345/(obj.get(0).getEvolution()-1))*(i);
                    double height;
                    if(low == high)
                        height = 625;
                    else
                        height = 625 - (165*((double)(meanHeights[i] - low) / (double)(high - low)));
                    cords2[i][0] = width; cords2[i][1] = height;
                }

                for(int i=0;i<obj.get(0).getEvolution()-1;i++)
                {
                    graph.setColor(Color.black);
                    graph.drawLine((int)cords[i][0] + 1,(int)cords[i][1] + 1,(int)cords[i+1][0] + 1,(int)cords[i+1][1] + 1);
                    graph.drawLine((int)cords2[i][0] + 1,(int)cords2[i][1] + 1,(int)cords2[i+1][0] + 1,(int)cords2[i+1][1] + 1);
                    graph.setColor(Color.red);
                    graph.fill(new Ellipse2D.Double((int)cords[i][0],cords[i][1],2,2));
                    graph.fill(new Ellipse2D.Double((int)cords2[i][0],cords2[i][1],2,2));
                    if(i == obj.get(0).getEvolution()-2)
                    {
                        graph.fill(new Ellipse2D.Double((int)cords[i+1][0],cords[i+1][1],2,2));
                        graph.fill(new Ellipse2D.Double((int)cords2[i+1][0],cords2[i+1][1],2,2));
                    }
                }
            }
        }
        
        Graphics2D main = (Graphics2D) g;
        main.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        boolean show = true;
        for(int i=0;i<obj.size();i++)
        {
            for(int j=0;j<location.size();j++)
            {
                if(i == location.get(j))
                    show = false;
            }
            if(show == true)
            {
                int[] xCords = new int[obj.get(i).getSize()];
                int[] yCords = new int[obj.get(i).getSize()];
                for(int j=0;j<obj.get(i).getSize();j++)
                {
                    xCords[j] = (int)obj.get(i).getX(j);
                    yCords[j] = (int)obj.get(i).getY(j);
                }
                Polygon poly = new Polygon(xCords, yCords, obj.get(i).getSize());
                main.setColor(Color.green);
                main.fillPolygon(poly);
                main.setColor(Color.black);
                main.drawPolygon(poly);
            }
            show = true;
        }
    }
    public int[] getHighestHeights()
    //gets the highest distances in the database
    {
        int[] high = new int[database.size()];
        for(int i=0;i<database.size();i++)
        {
            high[i] = getHigh(i);
        }
        return high;
    }
    public int[] getMeanHeights()
    //gets the mean distances in the database
    {
        int[] mean = new int[database.size()];
        for(int i=0;i<database.size();i++)
        {
            mean[i] = getMean(i);
        }
        return mean;
    }
    public int getHigh(int loc)
    //gets largest distance in a database location
    {
        int highest = 0;
        for(int i=0;i<database.get(loc).length;i++)
        {
             if((int)Double.parseDouble(database.get(loc)[i][0]) > highest)
                highest = (int)Double.parseDouble(database.get(loc)[i][0]);
        }
        return highest;
    }
    public int getMean(int loc)
    //gets mean distance in a database location
    {
        int sum = 0;
        for(int i=0;i<database.get(loc).length/2;i++)
            sum += (int)Double.parseDouble(database.get(loc)[i][0]);
        if(sum!=0)
            return sum / (database.get(loc).length/2);
        return 0;
    }
    public int getLow(int loc)
    //gets smallest distance in a database location
    {
        int lowest = 99999999;
        for(int i=0;i<database.get(loc).length;i++)
        {
            if((int)Double.parseDouble(database.get(loc)[i][0]) < lowest)
                lowest = (int)Double.parseDouble(database.get(loc)[i][0]);
        }
        return lowest;
    }
    public int getHighest(int[] high)
    //gets the highest distance in the database
    {
        int highest = 0;
        for(int i=0;i<high.length;i++)
        {
            if(high[i] > highest)
                highest = high[i];
        }
        return highest;
    }
    public int getLowest(int[] low)
    //
    {
        int lowest = 99999999;
        for(int i=0;i<low.length;i++)
        {
            if(low[i] < lowest)
                lowest = low[i];
        }
        return lowest;
    }
    public int timer()
    //timer allows functions to execute occording to time
    {
        long end = System.nanoTime();
        long elapsedTime = end - start;
        double tempSeconds = (double)elapsedTime / 1000000000;
        int seconds = (int) tempSeconds;
        return seconds;
    }
    public double rotTimer(int location)
    //returns the elapsed time between the start of rotational motion
    {
        long end = System.nanoTime();
        long elapsedTime = end - rotTime[location];
        double Seconds = (double)elapsedTime / 1000000000;
        return Seconds;
    }
    public void actionPerformed(ActionEvent e)
    //checks for updated actions according to timer
    {
        if(endSimulation == false)
        {
            repaint();
            updateVelocity();
            actions();
            gravity();
            contains();
            checkGeneration();
            checkHeight();
        }
    }
    public void updateVelocity()
    //updates velocity 
    {
        for(int i=0;i<obj.size();i++)
        {
            if(rotTime[i] != 0)
            {
                for(int j=0;j<obj.get(i).getSize();j++)
                {                
                    double theta = (rotTimer(i) % obj.get(i).getT(j))*360;
                    double addX = obj.get(i).getW(j) * Math.cos(Math.toRadians(theta));
                    double addY = obj.get(i).getW(j) * Math.sin(Math.toRadians(theta));
                    obj.get(i).addDX(addX);
                    obj.get(i).addDY(addY);
                }
            }
        }
    }
    public void actions()
    //updates position
    {
        for(int i=0;i<obj.size();i++)
        {
            obj.get(i).addX(obj.get(i).getDX());
            obj.get(i).addY(obj.get(i).getDY());
        }
    }
    public void gravity()
    //allows the object to fall
    {
        for(int i=0;i<obj.size();i++)
        {
            obj.get(i).addDY(.04);
        }
    }
    public void contains()
    //check if ground contains a point
    {
        for(int i=0;i<obj.size();i++)
        {
            for(int j=0;j<obj.get(i).getSize();j++)
            {
                double x = obj.get(i).getX(j);
                double y = obj.get(i).getY(j);
                if(ground.contains(x,y)){
                    calculateMotion(i,j);
                    obj.get(i).setDY(-Math.abs(obj.get(i).getDY()));
                    obj.get(i).addDX(.01);}
            }
        }
    }
    public void calculateMotion(int thisObject, int point)
    //calculates the motion of the obj
    {
        Point p = new Point((int)obj.get(thisObject).getX(point), (int)obj.get(thisObject).getY(point));
        PhysicsObj phy = new PhysicsObj(obj.get(thisObject), p, getSlope(obj.get(thisObject).getX(point)));
        obj.get(thisObject).setDX(phy.getVx());
        obj.get(thisObject).setDY(phy.getVy());
    }
    public double getSlope(double x)
    //returns the instantaneous angle of ground at x
    {
        x -= 182;
        if(x >= 0)
            return .014*x;
        else
            return (0.01508*x) - (0.00009*x*x);
    }
    public void checkHeight()
    //checks if an object fell off the cliff
    {
        boolean set = true;
        for(int i=0;i<obj.size();i++)
        {
            for(int j=0;j<obj.get(i).getSize();j++)
            {
                for(int k=0;k<location.size();k++)
                {
                    if(i == location.get(k))
                        set = false;
                }
                if(obj.get(i).getY(j) >= 700 && set == true)
                {
                    obj.get(i).setDistance(obj.get(i).getX(j));
                    location.add(i);
                }
                set = true;
            }
        }
    }
    public void checkGeneration()
    //checks if a generation all fell off or if time elapsed over 10 seconds
    {
        if(location.size() >= obj.size() || timer() >= 10)
            newGeneration();
    }
    public void newGeneration()
    //makes a new generation of obj's and adds distance & name to database
    {
        System.out.println("Evolution Depth: " + evolutionDepth);
        System.out.println("Trial: " + trialsCounter);
        
        trialsCounter--;
        
        boolean add = true;
        for(int i=0;i<obj.size();i++)
        {
            for(int j=0;j<location.size();j++)
            {
                if(i == location.get(j))
                    add = false;
            }
            if(add == true)
                obj.get(i).setDistance(obj.get(i).getX(0));
            add = true;
        }
        
        for(int i=0;i<obj.size();i++)
        {
            obj.get(i).setX(initialX.get(i));
            obj.get(i).setY(initialY.get(i));
        }
        
        Sort sort = new Sort(obj);
        ArrayList<Obj> temp1 = sort.getObj();
        ArrayList<Obj> temp = new ArrayList<Obj>();
        
        String[][] str = new String[obj.size()][2];
        int count = 0;
        for(int i=temp1.size()-1;i>=0;i--)
        {
            str[count][0] = Double.toString(temp1.get(i).getDistance());
            str[count][1] = temp1.get(i).getName();
            temp.add(temp1.get(i));
            count++;
        }
        database.add(str);
        
        if(trialsCounter == 0)
        {
            EvolutionData e = new EvolutionData(temp.get(0), (int)Double.parseDouble(str[0][0]), getMean(database.size()-1));
            EI.add(e);
            
            trialsCounter = trials;                
            CreateObj create = new CreateObj(20);
            obj = create.getObjects();                
            database = new ArrayList<String[][]>();                
            start = System.nanoTime();
            ArrayList<Integer> tempLocation = new ArrayList<Integer>();
            location = tempLocation;
            getInitialCords();
            
            evolutionDepth--;
            if(evolutionDepth == 0){
                endSimulation = true;
                JFrame screen = new JFrame();
                Results r = new Results(EI);
                screen.add(r);
                screen.setSize(1280,720);
                screen.setVisible(true);
                screen.setLocationRelativeTo(null);
                screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }
        else
        {      
            EvolveObj e = new EvolveObj(temp);
            obj = e.getObjects();
            start = System.nanoTime();
            ArrayList<Integer> tempLocation = new ArrayList<Integer>();
            location = tempLocation;
            getInitialCords();
        }
    }
}