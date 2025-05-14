package br.com.aval.testes.product;

import Request.ProductRequest;
import Utils.AuthHooks;
import Utils.Environment;
import Utils.ProductClient;
import Utils.ProductFactory;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ProductTest {

    Faker faker = new Faker();

    // Esse teste está com erro pois alguns produtos não contém a propriedade "brand"
    @Test
    public void validateProductsSchema() {
        String token = AuthHooks.getAuthToken("emilys", "emilyspass");

        given()
                .baseUri(Environment.dummy.getBaseUrl())
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("auth/products")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/products-schema.json"));
    }

    @Test
    public void getListProducts() {
        String token = AuthHooks.getAuthToken("emilys", "emilyspass");

        given()
                .baseUri(Environment.dummy.getBaseUrl())
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("auth/products")
                .then()
                .statusCode(200)
                .body("products", notNullValue());
    }

    @Test
    public void getListProductsInvalidToken() {
        given()
                .baseUri(Environment.dummy.getBaseUrl())
                .header("Authorization", "Bearer invalid-token")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("auth/products")
                .then()
                .statusCode(401)
                .body("message", equalTo("Invalid/Expired Token!"));
    }

    @Test
    public void addProductSuccess() {
        ProductRequest product = new ProductFactory().build(); // ou ProductBuilder().build() se estiver usando Builder

        given()
                .baseUri(Environment.dummy.getBaseUrl())
                .contentType(ContentType.JSON)
                .body(product)
                .log().all()
                .when()
                .post("/products/add")
                .then()
                .log().all()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/product-add-schema.json"))
                .body("title", equalTo(product.getTitle()))
                .body("brand", equalTo(product.getBrand()));
    }

    @Test
    public void addProductInvalidTitle() {
        ProductRequest product = new ProductFactory()
                .withTitle("") // só funciona se você estiver usando o padrão Builder
                .build();

        given()
                .baseUri(Environment.dummy.getBaseUrl())
                .contentType(ContentType.JSON)
                .body(product)
                .log().all()
                .when()
                .post("/products/add")
                .then()
                .log().all()
                .statusCode(400)
                .body("menssage", equalTo("title is required")); // ajuste se a chave for "message"
    }

    @Test
    public void getProductListNoToken() {
        given()
                .baseUri(Environment.dummy.getBaseUrl())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/products-schema.json"));
    }

    @Test
    public void getProductById() {
        given()
                .baseUri(Environment.dummy.getBaseUrl())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/products/1")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/product-by-id-schema.json"));
    }

    @Test
    public void getProductByInvalidId() {
        Integer id = 324321212;

        given()
                .baseUri(Environment.dummy.getBaseUrl())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(String.format("/products/%d", id))
                .then()
                .statusCode(404)
                .body("message", equalTo(String.format("Product with id '%d' not found", id)));
    }

    // A API não faz o delete lógico o que impacta diretamente o resultado do teste !
    @Test
    public void deleteProductFromList() {
        // 1. Obter um ID de produto existente
        int id = ProductClient.getFirstProductId();

        System.out.println("ID selecionado para deletar: " + id);

        // 2. Deletar o produto
        given()
            .baseUri(Environment.dummy.getBaseUrl())
            .contentType(ContentType.JSON)
            .log().all()
        .when()
            .delete("/products/" + id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("isDeleted", equalTo(true));

        // 3. Consultar se o produto ainda existe (a API pode retornar mesmo após deletar)
        ProductClient.getProductById(id)
            .then()
                .statusCode(404)
                .body("message", equalTo(String.format("Product with id '%d' not found", id)));
    }

    // Cenário com falha pois a API não está efetuando persistencia de dados
    @Test
    public void updateProductData() {
        // 1. Obter ID de produto existente
        int id = ProductClient.getFirstProductId();

        // 2. Obter os dados originais do produto
        String originalTitle = ProductClient.getProductById(id).path("title");
        Float originalPrice = ProductClient.getProductById(id).path("price");

        // 3. Criar payload com alteração (sem criar novo produto)
        ProductRequest updatedProduct = new ProductFactory()
                .withTitle("Título Atualizado")
                .withPrice(99.99)
                .build();

        // 4. Atualizar o produto via PUT
        ProductClient.updateProductById(id, updatedProduct)
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("title", equalTo(updatedProduct.getTitle()))
                .body("price", equalTo(updatedProduct.getPrice().floatValue()));

        // 5. Buscar novamente e confirmar persistência da alteração
        ProductClient.getProductById(id)
                .then()
                .statusCode(200)
                .body("title", equalTo(updatedProduct.getTitle()))
                .body("price", equalTo(updatedProduct.getPrice().floatValue()));
    }
}
