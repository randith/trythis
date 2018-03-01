package io.awesome.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awesome.model.CorId;
import io.awesome.model.InternalServerException;
import io.awesome.model.Post;
import io.awesome.model.PostLink;
import io.dropwizard.setup.Environment;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostsResourceHelper {
  private static final Logger LOG = LoggerFactory.getLogger(PostsResourceHelper.class);

  private final ObjectMapper mapper;

  @Inject
  public PostsResourceHelper(Environment environment) {
    this.mapper = environment.getObjectMapper();
  }

  /**
   * parse input stream to request body.
   * @return Optional PostRequest that is empty if invalid
   */
  public Optional<Post> parsePostBody(CorId corId, InputStream inputStream) {

    try {
      return Optional.of(mapper.readValue(inputStream, Post.class));
    } catch (IOException e) {
      LOG.error("{} Unable serialize request into {}",
          corId, Post.class.getSimpleName(), e);
      return Optional.empty();
    }
  }
}
