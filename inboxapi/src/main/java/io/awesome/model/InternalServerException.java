package io.awesome.model;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InternalServerException extends WebApplicationException {

  /**
   * Log generic message with corId and throw Internal Server Exception.
   */
  public InternalServerException(CorId corId) {
    super(Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .type(MediaType.TEXT_PLAIN_TYPE)
        .entity(corId
            + " There was an error processing your request. It has been logged with the id")
        .build());
  }

}
