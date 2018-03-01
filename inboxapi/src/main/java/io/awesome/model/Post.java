package io.awesome.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import java.util.Objects;

public class Post {

  private String id;
  private Long timestamp;
  private String content;
  private String authorId;
  private String destinationType;
  private String destinationId;

  /**
   * for jackson.
   */
  public Post() {
  }

  @VisibleForTesting
  public Post(String id, Long timestamp, String content, String authorId, String destinationType, String destinationId) {
    this.id = id;
    this.timestamp = timestamp;
    this.content = content;
    this.authorId = authorId;
    this.destinationType = destinationType;
    this.destinationId = destinationId;
  }

  public String getId() {
    return id;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public String getContent() {
    return content;
  }

  public String getAuthorId() {
    return authorId;
  }

  public String getDestinationType() {
    return destinationType;
  }

  public String getDestinationId() {
    return destinationId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Post that = (Post) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(timestamp, that.timestamp) &&
        Objects.equals(content, that.content) &&
        Objects.equals(authorId, that.authorId) &&
        Objects.equals(destinationType, that.destinationType) &&
        Objects.equals(destinationId, that.destinationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timestamp, content, authorId, destinationType, destinationId);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("timestamp", timestamp)
        .add("content", content)
        .add("authorId", authorId)
        .add("destinationType", destinationType)
        .add("destinationId", destinationId)
        .toString();
  }
}
