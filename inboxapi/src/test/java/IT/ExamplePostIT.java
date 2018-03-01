package IT;

import static io.restassured.RestAssured.expect;

import io.awesome.model.PostLink;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExamplePostIT {
  private static final Logger LOG = LoggerFactory.getLogger(ExamplePostIT.class);

  @Test
  public void testPostExample() {
    String id = String.format("%016x", ThreadLocalRandom.current().nextLong());
    Long timestamp = ThreadLocalRandom.current().nextLong();

    String postBody = "{\n"
        + "\"id\": \""
        + id
        + "\",\n\"timestamp\": \""
        + timestamp
        + "\"\n}";

    Response response = expect()
        .given()
        .contentType(ContentType.JSON)
        .body(postBody)

        .when()
        .post("http://localhost:8080/v1/posts")

        .then()
        .statusCode(200)

        .extract()
        .response();

    LOG.info("POST complete with expected response code, deserializing response to assert");
    PostLink postLink = response.body().as(PostLink.class);

    Assert.assertEquals(id, postLink.getId());
    Assert.assertEquals(timestamp, postLink.getTimestamp());
  }

}
