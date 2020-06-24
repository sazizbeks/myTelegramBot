package domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CurrencyParser {
    public static String converter(ExchangeRates exchangeRate){
        try {
            Document doc = Jsoup.connect("https://finance.rambler.ru/calculators/converter/"+exchangeRate).get();
            return "1 "+exchangeRate.toString().substring(2,5)+" = "
                    +doc.getElementsByClass("converter-display__value").get(1).text()
                    +' '+exchangeRate.toString().substring(6,9);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error!";
    }
}
