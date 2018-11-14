package Evolution;

import java.awt.*;
public class PhysicsObj
//calculates object physics
{
    private double Vx, Vy;
    public PhysicsObj(Obj obj, Point collisionPoint, double slope)
    //constructor
    {
        calculateMotion(obj, slope);
    }
    public double getVx(){
        return Vx;}
    public double getVy(){
        return Vy;}
    public void calculateMotion(Obj obj, double slope)
    //calculates the new Vx and Vy
    {
        double Vtot = Math.sqrt((obj.getDX()*obj.getDX()) + (obj.getDY()*obj.getDY()));
        double angleObj = Math.toDegrees(Math.atan(obj.getDY()/obj.getDX()));
        double angleSlope = Math.toDegrees(Math.atan(slope));
        double angleOfIncedince = angleSlope - angleObj;
        double angleOfReflection = 180 - angleOfIncedince;
        Vx = .9 * Vtot*Math.cos(Math.toRadians(angleOfReflection));
        Vy = .9 * -Math.abs(Vtot*Math.sin(Math.toRadians(angleOfReflection)));    
    }
}
