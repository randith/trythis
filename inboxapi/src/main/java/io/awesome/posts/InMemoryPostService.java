package io.awesome.posts;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import io.awesome.model.Post;
import io.awesome.model.PostLink;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryPostService implements PostService {

  private Map<String, Post> posts;

  public InMemoryPostService() {
    this.posts = Maps.newConcurrentMap();
  }

  @Override
  public PostLink createPost(Post post) {
    posts.put(post.getId(), post);
    return PostLink.fromPost(post);
  }

  @Override
  public Post getPost(String id) {
    return posts.get(id);
  }

  @Override
  public List<PostLink> getPosts(String userId) {
    return posts.entrySet().stream()
        .map(entry -> PostLink.fromPost(entry.getValue()))
        .collect(Collectors.toList());
  }
}
