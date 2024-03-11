import java.util.HashMap;

public class Polynomial {
    private HashMap<Integer, Float> values;

    //Constructor
    Polynomial(){values=new HashMap<Integer, Float>();}
    ////////////////////////////////////////////////////////////////////////////////////////////

    //Functions that modify the map
    public void addValue(Integer key, Float value){
        if (values.containsKey(key))
            if(values.get(key) + value !=0 )
                    values.replace(key, values.get(key) + value);
            else
                values.remove(key);
        else
            values.put(key, value);
    }
    public void setMap(HashMap<Integer, Float> clone) {values = clone;}
    ////////////////////////////////////////////////////////////////////////////////////////////

    //Getter
    public HashMap<Integer, Float> getValues() {return new HashMap<>(values);}
    ////////////////////////////////////////////////////////////////////////////////////////////
}
