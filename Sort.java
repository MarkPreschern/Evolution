package Evolution;

import java.util.Arrays;
import java.util.ArrayList;
public class Sort
//creates class quickSort
{
    private ArrayList<Obj> obj;
    //initializes variable
    public Sort(ArrayList<Obj> obj)
    //constructor sets array and runs method sort
    {
        this.obj = obj;
        sort(0, obj.size() - 1);
    }
    public void sort(int left, int right) 
    //sets placeHolder through method sortArray for every recursive call of sort
    {
        if (left < right) 
        {
            int placeHolder = sortArray(left, right);
            sort(left, placeHolder - 1);
            sort(placeHolder, right);
        }
    }
    public int sortArray(int left, int right) 
    //Divides array from pivot with values less than pivot on the left and values greater than pivot on the right
    {
        double pivot = obj.get((left + right) / 2).getDistance(); //sets pivot to middle element in array
        while (left <= right) 
        {
            while (obj.get(left).getDistance() < pivot) 
                left++;
            while (obj.get(right).getDistance() > pivot) 
                right--;
            if (left <= right) 
            //swaps values of array[left] and array[right]
            {
                Obj temp = obj.get(left);
                obj.set(left, obj.get(right));
                obj.set(right, temp);
                left++;
                right--;
            }
        }
        return left;
    }
    public ArrayList<Obj> getObj()
    //returns the sorted array
    {
        return obj;
    }
}
