package Evolution;

public class Obj
{
    private String name;
    private double[] x, y;
    private double dx, dy;
    private double distance;
    private int evolution;
    public Obj(String name, double[] x, double[] y, double dx, double dy, double distance, int evolution)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.distance = distance;
        this.evolution = evolution;
    }
    public String getName(){
        return name;}
    public double[] getX(){
        return x;}
    public double[] getY(){
        return y;}
    public double getDX(){
        return dx;}
    public double getDY(){
        return dy;}
    public double getDistance(){
        return distance;}
    public int getEvolution(){
        return evolution;}
    public double getX(int value){
        return x[value];}
    public double getY(int value){
        return y[value];}
    public int getSize(){
        return x.length;}
    public void addX(double value){
        for(int i=0;i<getSize();i++)
            x[i] += value;}
    public void addY(double value){
        for(int i=0;i<getSize();i++)
            y[i] += value;}
    public void setX(double[] value){
        x = value;}
    public void setY(double[] value){
        y = value;}
    public void setDX(double value){
        dx = value;}
    public void setDY(double value){
        dy = value;}
    public void addDX(double value){
        dx += value;}
    public void addDY(double value){
        dy += value;}
    public void setDistance(double value){
        distance = value;}
    public void addEvolution(){
        evolution++;}
}
