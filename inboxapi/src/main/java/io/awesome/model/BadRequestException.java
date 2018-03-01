package io.awesome.model;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadRequestException extends WebApplicationException {
  private static final Logger LOG = LoggerFactory.getLogger(BadRequestException.class);

  /**
   * Log provided message as error and throw Bad Request.
   */
  public BadRequestException(CorId corId, String message) {
    super(Response
        .status(Status.BAD_REQUEST)
        .type(MediaType.TEXT_PLAIN_TYPE)
        .entity(corId + message)
        .build());

    LOG.info("{} {}", corId, message);
  }

}
