package io.awesome.model;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.Assert;
import org.junit.Test;

/**
 * I normally wouldn't bother testing a bean, but wanted to show given, when, then
 */
public class PostTest {

  @Test
  public void testPostRequestHappyPath() {
    // given
    String givenId = String.format("%016x", ThreadLocalRandom.current().nextLong());
    Long givenTimeStamp = ThreadLocalRandom.current().nextLong();

    // when
    Post post = new Post(givenId, givenTimeStamp, null, null, null, null);

    // then
    Assert.assertEquals(givenId, post.getId());
    Assert.assertEquals(givenTimeStamp, post.getTimestamp());
  }

}
