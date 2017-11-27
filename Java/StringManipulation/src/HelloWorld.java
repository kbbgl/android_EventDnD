import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloWorld {
    public static void main(String[] args) {

        String str = "<span class=\"image\"<img src=\"http://www.juventus.com/media/images/posati/stagione-16-17/prima-squadra/501x752/Buffon_501x752.png\"/> </span> <span class=\"text\"> Gianluigi</br> <b>Buffon</b> </span>";

        Pattern p = Pattern.compile("src=\"(.*?)\"");
        Matcher m = p.matcher(str);

        while(m.find()){

            System.out.println(m.group(1));

        }

        p = Pattern.compile("class=\"text\"> (.*?)</br>");
        m = p.matcher(str);

        while (m.find()){
            System.out.println(m.group(1));
        }


        p = Pattern.compile("<b>(.*?)</b>");
        m = p.matcher(str);

        while (m.find()){
            System.out.println(m.group(1));
        }


    }
}