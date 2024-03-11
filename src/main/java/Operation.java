import java.util.Map;

public class Operation {
    public static Polynomial addition(Polynomial P1, Polynomial P2){
        Polynomial pCopy = new Polynomial();
        pCopy.setMap(P1.getValues());//Copy P1.values in P3.values
        for(Map.Entry<Integer, Float> entry : P2.getValues().entrySet()){
            pCopy.addValue(entry.getKey(), entry.getValue());
        }
        return pCopy;
    }
    public static Polynomial subtraction(Polynomial P1, Polynomial P2){
        Polynomial pCopy = new Polynomial();
        pCopy.setMap(P1.getValues());//Copy P1.values in P3.values
        for(Map.Entry<Integer, Float> entry : P2.getValues().entrySet()){
            pCopy.addValue(entry.getKey(), (-1) * entry.getValue());
        }
        return pCopy;
    }
    public static Polynomial multiplication(Polynomial P1, Polynomial P2){
        Polynomial pCopy = new Polynomial();
        for(Map.Entry<Integer, Float> entryP1 : P1.getValues().entrySet())
            for(Map.Entry<Integer, Float> entryP2 : P2.getValues().entrySet())
                pCopy.addValue(entryP1.getKey() + entryP2.getKey(), entryP1.getValue() * entryP2.getValue());
        return pCopy;
    }
    private static int getHighestDegree(Polynomial pCopy) {
        int highestDegree = 0;
        for(Map.Entry<Integer, Float> entry : pCopy.getValues().entrySet())
            if(highestDegree < entry.getKey())
                highestDegree = entry.getKey();
        return highestDegree;
    }
    private static float getHighestDegreeValue(Polynomial pCopy, int degree) {
        float highestDegreeValue = 0;
        for(Map.Entry<Integer, Float> entry : pCopy.getValues().entrySet())
            if(entry.getKey() == degree)
            {
             highestDegreeValue = entry.getValue();
             break;
            }
        return highestDegreeValue;
    }
    public static Polynomial division(Polynomial P1, Polynomial P2, Polynomial result) {
        Polynomial hPol = new Polynomial();
        Polynomial lPol = new Polynomial();
        int highDegP1, highDegP2;
        float highValP1, highValP2;
        if(getHighestDegree(P1) >= getHighestDegree(P2))
        {
            hPol.setMap(P1.getValues());
            lPol.setMap(P2.getValues());
        }
        else
        {
            hPol.setMap(P2.getValues());
            lPol.setMap(P1.getValues());
        }
        while(getHighestDegree(hPol) >= getHighestDegree(lPol))
        {
            highDegP1 = getHighestDegree(hPol);
            highDegP2 = getHighestDegree(lPol);
            highValP1 = getHighestDegreeValue(hPol, highDegP1);
            highValP2 = getHighestDegreeValue(lPol, highDegP2);
            result.addValue(highDegP1 - highDegP2, highValP1/highValP2);
            Polynomial calPol = new Polynomial();
            calPol.addValue(highDegP1 - highDegP2, highValP1/highValP2);
            hPol.setMap(Operation.subtraction(hPol, Operation.multiplication(lPol, calPol)).getValues());
        }
        return hPol;
    }
    public static Polynomial derivative(Polynomial P){
        Polynomial pCopy = new Polynomial();
        for(Map.Entry<Integer, Float> entry : P.getValues().entrySet())
            if(entry.getKey() > 0)
                pCopy.addValue(entry.getKey() - 1, entry.getValue() * entry.getKey());
        return pCopy;
    }
    public static Polynomial integration(Polynomial P){
        Polynomial pCopy = new Polynomial();
        for(Map.Entry<Integer, Float> entry : P.getValues().entrySet()){
            pCopy.addValue(entry.getKey() + 1, entry.getValue()/(entry.getKey() + 1));
        }
        return pCopy;
    }
}
