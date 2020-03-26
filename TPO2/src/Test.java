import com.sun.xml.internal.ws.util.StringUtils;

public class Test {


    public static void main(String[] args) {
        //double number = 0.3332238836462;
        //System.out.println((double)Math.round(number*10000d)/10000);

        String word = "dupa";
        //String newWord = word.substring(0, 1).toUpperCase() + word.substring(1);
        String newWord = StringUtils.capitalize(word);
        System.out.println(newWord);
    }
}
