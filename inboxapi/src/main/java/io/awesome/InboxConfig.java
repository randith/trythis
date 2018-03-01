package io.awesome;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * Empty for now as there is nothing special to configure
 *
 * however, to me dropwizard Configuration should be a deserialized version of config.yaml
 * and nothing else
 */
public class InboxConfig extends Configuration {

}
