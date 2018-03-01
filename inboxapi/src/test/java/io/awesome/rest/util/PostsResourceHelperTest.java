package io.awesome.rest.util;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awesome.model.CorId;
import io.awesome.model.InternalServerException;
import io.awesome.model.Post;
import io.dropwizard.setup.Environment;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PostsResourceHelperTest {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private PostsResourceHelper classUnderTest;

  @Mock
  private Environment mockEnvironment;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);

    when(mockEnvironment.getObjectMapper())
        .thenReturn(MAPPER);

    classUnderTest = new PostsResourceHelper(mockEnvironment);
  }

  @Test
  public void testParseExamplePostBodyHappy() {

    // given
    String input1Given = String.format("%016x", ThreadLocalRandom.current().nextLong());
    Long timestamp = ThreadLocalRandom.current().nextLong();

    String validInputBody = "{\n"
        + "\"id\": \""
        + input1Given
        + "\",\n\"timestamp\": \""
        + timestamp
        + "\"\n}";
    InputStream inputStream = new ByteArrayInputStream(
        validInputBody.getBytes(StandardCharsets.UTF_8));

    // when
    Optional<Post> result = classUnderTest.parsePostBody(
        new CorId(), inputStream);

    // then
    Assert.assertTrue(result.isPresent());
  }

  @Test
  public void testParseExamplePostBodyInvalid() {

    // given
    String givenId = String.format("%016x", ThreadLocalRandom.current().nextLong());
    Long givenTimeStamp = ThreadLocalRandom.current().nextLong();

    // when
    Post post = new Post(givenId, givenTimeStamp, null, null, null, null);

    InputStream inputStream = new ByteArrayInputStream("not a Post json".getBytes(StandardCharsets.UTF_8));

    // when
    Optional<Post> result = classUnderTest.parsePostBody(
        new CorId(), inputStream);

    // then
    Assert.assertFalse(result.isPresent());
  }


}
