package Utils;

public enum Environment {
    dummy("https://dummyjson.com"),
    telegram("https://api.telegram.org");


    private final String baseUrl;

    Environment(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
