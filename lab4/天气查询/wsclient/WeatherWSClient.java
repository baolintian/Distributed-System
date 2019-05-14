
package wsclient;
import java.util.List;
 
import cn.com.webxml.ArrayOfString;
import cn.com.webxml.WeatherWS;
import cn.com.webxml.WeatherWSSoap;
 
public class WeatherWSClient {
   public static void main(String[] args) {
        WeatherWS factory = new WeatherWS();
        WeatherWSSoap weatherWSSoap = factory.getWeatherWSSoap();
        ArrayOfString weatherInfo = weatherWSSoap.getWeather("shanghai", "");
        List<String> lstWeatherInfo = weatherInfo.getString();
        for (String string : lstWeatherInfo) {
            System.out.println(string);
            System.out.println("------------------------");
        }
    }
}
