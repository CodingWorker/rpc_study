import java.text.DecimalFormat;

/**
 * Created by daiya on 2017/8/15.
 */
public class Test {
    public static void main(String[] args){
        DecimalFormat df=new DecimalFormat("#0.00");
        Double f=1.22323232;
        f=0.0;
        System.out.println(String.format("%.2f",f));
        System.out.println(df.format(f));
    }
}
