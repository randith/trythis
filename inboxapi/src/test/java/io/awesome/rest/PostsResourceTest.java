package io.awesome.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import io.awesome.model.BadRequestException;
import io.awesome.model.CorId;
import io.awesome.model.Post;
import io.awesome.model.PostLink;
import io.awesome.posts.PostService;
import io.awesome.rest.util.PostsResourceHelper;
import io.dropwizard.setup.Environment;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PostsResourceTest {

  private PostsResource classUnderTest;

  @Mock
  private Environment mockEnvironment;

  @Mock
  private PostsResourceHelper mockPostsResourceHelper;

  @Mock
  private PostService mockPostService;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testHappyPath() {
    // given
    String id = String.format("%016x", ThreadLocalRandom.current().nextLong());
    Long timestamp = ThreadLocalRandom.current().nextLong();

    when(mockPostsResourceHelper.parsePostBody(
        any(CorId.class), any(InputStream.class)))
        .thenReturn(Optional.of(new Post(id, timestamp, null, null, null, null)));

    when(mockPostService.createPost(any(Post.class)))
        .thenReturn(new PostLink(id, timestamp, "link"));

    classUnderTest = new PostsResource(mockPostsResourceHelper, mockPostService);

    // when
    Response response = classUnderTest.createPost(
        new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)));

    PostLink actualResponse = (PostLink) response.getEntity();

    // then
    Assert.assertEquals(id, actualResponse.getId());
    Assert.assertEquals(timestamp, actualResponse.getTimestamp());
  }

  @Test(expected = BadRequestException.class)
  public void testInvalidBody() {
    // given
    when(mockPostsResourceHelper.parsePostBody(
        any(CorId.class), any(InputStream.class)))
        .thenReturn(Optional.<Post>empty());

    classUnderTest = new PostsResource(mockPostsResourceHelper, mockPostService);

    // when
    Response response = classUnderTest.createPost(
        new ByteArrayInputStream("does not matter".getBytes(StandardCharsets.UTF_8)));
  }

}
