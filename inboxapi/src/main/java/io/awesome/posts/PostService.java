package io.awesome.posts;

import io.awesome.model.Post;
import io.awesome.model.PostLink;
import java.util.List;

public interface PostService {

  PostLink createPost(Post post);

  Post getPost(String id);

  List<PostLink> getPosts(String userId);

}
