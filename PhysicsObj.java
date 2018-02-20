package Evolution;

import java.awt.*;
public class PhysicsObj
//calculates object physics
{
    private double Vx, Vy;
    public PhysicsObj(Obj obj, Point collisionPoint, double slope)
    //constructor
    {
        calculateMotion(obj, collisionPoint, centerOfMass(obj), slope);
    }
    public double getVx(){
        return Vx;}
    public double getVy(){
        return Vy;}
    public Point centerOfMass(Obj obj)
    //returns the center of mass in terms of Point
    {
        int sumX = 0, sumY = 0;
        for(int i=0;i<obj.getSize();i++)
        {
            sumX += obj.getX(i);
            sumY += obj.getY(i);
        }
        int Xcm = sumX / obj.getSize();
        int Ycm = sumY / obj.getSize();
        return new Point(Xcm,Ycm);
    }
    public void calculateMotion(Obj obj, Point Collision, Point COM, double slope)
    //calculates the new Vx, Vy, omega
    {
        double Vtot = Math.sqrt((obj.getDX()*obj.getDX()) + (obj.getDY()*obj.getDY()));
        double angleObj = Math.toDegrees(Math.atan(obj.getDY()/obj.getDX()));
        double angleSlope = Math.toDegrees(Math.atan(slope));
        double angleOfIncedince = angleSlope - angleObj;
        double angleOfReflection = 180 - angleOfIncedince;
        Vx = Vtot*Math.cos(Math.toRadians(angleOfReflection));
        Vy = -Math.abs(Vtot*Math.sin(Math.toRadians(angleOfReflection)));    
    }
}
