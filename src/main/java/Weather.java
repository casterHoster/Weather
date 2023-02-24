import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weather {
    private static Document getPageOfWeatherInNizhniyNivgorod() throws IOException {
        String urlOfPage = "https://www.gismeteo.ru/weather-nizhny-novgorod-4355/";
        Document page = Jsoup.parse(new URL(urlOfPage), 10000);
        return page;
    }
    private static Pattern pattern = Pattern.compile("\\d+");
    private static String getTemperatureFromString (String allStringWithTemperature) throws Exception {
        Matcher matcher = pattern.matcher(allStringWithTemperature);
        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new Exception("Can't extract date from string");
        }
    }


    public static void main(String[] args) throws Exception {
        Document page = getPageOfWeatherInNizhniyNivgorod(); // парсим страничку гисметео для Нижнего
        Element allStringWithTemperature = page.select("span[class = unit unit_temperature_c]").first(); // первую строку с обозначением температуры (это как раз строка с актуальной температурой на данный момент)
        Element signOfTemperature = allStringWithTemperature.selectFirst("span[class = sign]"); // чтение символа плюс или минус
        String sign = signOfTemperature.text(); // перевод символа в стрнг
        String temperature = getTemperatureFromString(allStringWithTemperature.text());
        if (sign.equals("−")) { // символ взят напрямую с сайта, его не удалять, это не минус и как минус машина его не читает
            System.out.print("-");
        } else {
            System.out.print("+");
        }
        System.out.println(temperature);
// Element quantityOfTemperature = allStringWithTemperature.selectFirst();a

    }
}
