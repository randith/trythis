package io.awesome.rest;

import com.google.inject.Inject;
import io.awesome.model.BadRequestException;
import io.awesome.model.CorId;
import io.awesome.model.Post;
import io.awesome.model.PostLink;
import io.awesome.posts.PostService;
import io.awesome.rest.util.PostsResourceHelper;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/v1/posts")
public class PostsResource {
  private static final Logger LOG = LoggerFactory.getLogger(PostsResource.class);

  private final PostsResourceHelper helper;
  private final PostService postService;

  @Inject
  public PostsResource(PostsResourceHelper helper, PostService postService) {
    this.helper = helper;
    this.postService = postService;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createPost(InputStream inputStream) {
    CorId corId = new CorId();
    Optional<Post> request = helper.parsePostBody(corId, inputStream);

    if (!request.isPresent()) {
      throw new BadRequestException(corId, "Request body invalid");
    }

    LOG.info("{}Processing request: {}", corId, request);

    PostLink postLink = postService.createPost(request.get());

    return Response.ok()
        .entity(postLink)
        .build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPosts(InputStream inputStream) {
    CorId corId = new CorId();
    List<PostLink> postLinks = postService.getPosts("pretendUserID");

    LOG.info("{} Returning {} postLinks", corId, postLinks.size());

    return Response.ok()
        .entity(postLinks)
        .build();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPosts(@PathParam("id") String id) {
    CorId corId = new CorId();
    Post post = postService.getPost(id);
    if (post == null) {
      return Response.status(404).build();
    }
    return Response.ok()
        .entity(post)
        .build();
  }


}
