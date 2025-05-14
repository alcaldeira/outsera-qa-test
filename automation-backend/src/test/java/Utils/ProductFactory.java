package Utils;

import Request.ProductRequest;
import com.github.javafaker.Faker;

public class ProductFactory {

    private static final Faker faker = new Faker();

    private String title = faker.commerce().productName();
    private String description = faker.lorem().sentence();
    private double price = faker.number().randomDouble(2, 10, 500);
    private double discountPercentage = faker.number().randomDouble(1, 5, 30);
    private double rating = faker.number().randomDouble(2, 1, 5);
    private int stock = faker.number().numberBetween(1, 1000);
    private String brand = faker.company().name();
    private String category = faker.commerce().department();
    private String thumbnail = "https://i.dummyjson.com/data/products/" + faker.number().numberBetween(1, 100) + "/thumbnail.jpg";

    public ProductFactory withTitle(String title) {
        this.title = title;
        return this;
    }

    public ProductFactory withPrice(double price) {
        this.price = price;
        return this;
    }

    // Adicione outros `with` conforme necessidade (brand, stock etc.)

    public ProductRequest build() {
        return new ProductRequest(
                title,
                description,
                price,
                discountPercentage,
                rating,
                stock,
                brand,
                category,
                thumbnail
        );
    }
}