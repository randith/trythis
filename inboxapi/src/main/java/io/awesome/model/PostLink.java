package io.awesome.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import java.util.Objects;

public class PostLink {
  private String id;
  private Long timestamp;
  private String link;


  public static PostLink fromPost(Post post) {
    return new PostLink(post.getId(), post.getTimestamp(), "/v1/posts/" + post.getId());
  }

  @VisibleForTesting
  public PostLink(String id, Long timestamp, String link) {
    this.id = id;
    this.timestamp = timestamp;
    this.link = link;
  }

  public String getId() {
    return id;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public String getLink() {
    return link;
  }

  public static class Builder {
    private String id;
    private Long timestamp;
    private String link;

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withTimestamp(Long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder withLink(String link) {
      this.link = link;
      return this;
    }

    public PostLink build() {
      return new PostLink(id, timestamp, link);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostLink postLink = (PostLink) o;
    return Objects.equals(id, postLink.id) &&
        Objects.equals(timestamp, postLink.timestamp) &&
        Objects.equals(link, postLink.link);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timestamp, link);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("timestamp", timestamp)
        .add("link", link)
        .toString();
  }
}
