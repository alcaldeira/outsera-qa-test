package br.com.aval.testes.telegram;

import Request.WebhookRequest;
import Utils.AuthHooks;
import Utils.Environment;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class WebhookTest {
    private static final String CHAT_ID = "839391957";
    private static final String BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");

    @Test
    public void verifyWebhookConfigurationSuccess() {
        WebhookRequest payload = new WebhookRequest("teste");

        given().baseUri(Environment.telegram.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
            .log().all()
        .when()
            .post("/bot" + BOT_TOKEN + "/setWebhook?url=https://andyfinancialcontrol.app.n8n.cloud/webhook-test/telegram-controle")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/webhook-response-schema.json"));
    }

    @Test
    public void verifyMessageToTelegram() {
        given().baseUri(Environment.telegram.getBaseUrl())
            .contentType(ContentType.URLENC)
            .formParam("chat_id", CHAT_ID)
            .formParam("text", "🚀 Compra registrada: R$ 25,00 - Padaria - 07/04/2025")
            .log().all()
        .when()
            .post("/bot" + BOT_TOKEN + "/sendMessage")
        .then()
            .statusCode(200)
            .body("ok", equalTo(true));
    }
}
