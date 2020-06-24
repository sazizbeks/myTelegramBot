package domain;

public enum ExchangeRates {
    USD_KZT("1-USD-KZT"), EUR_KZT("1-EUR-KZT"), RUB_KZT("1-RUB-KZT");
    private String name;
    ExchangeRates(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
