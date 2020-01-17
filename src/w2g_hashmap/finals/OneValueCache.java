package w2g_hashmap.finals;


/**
 * Created by W2G on 2018/1/16.
 */
public class OneValueCache {

    private final String lastNumber;
    private final String lastFactors;

    public OneValueCache(String lastNumber, String lastFactors) {
        this.lastNumber = lastNumber;
        this.lastFactors = lastFactors;
    }

    public String getFactors(String i){
        if (lastNumber==null||!lastNumber.equals(i)){
            return null;
        }else{
            return lastFactors;
        }
    }

}
