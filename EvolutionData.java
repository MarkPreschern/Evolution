package Evolution;

public class EvolutionData
{
    Obj obj;
    int height;
    int mean;
    public EvolutionData(Obj obj, int height, int mean)
    {
        this.obj = obj;
        this.height = height;
        this.mean = mean;
    }
    public Obj getObj(){
        return obj;}
    public int getHeight(){
        return height;}
    public int getMean(){
        return mean;}
}
