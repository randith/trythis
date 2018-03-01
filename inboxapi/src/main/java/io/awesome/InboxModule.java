package io.awesome;

import com.google.inject.Provides;
import io.awesome.posts.InMemoryPostService;
import io.awesome.posts.PostService;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;
import org.apache.http.client.HttpClient;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

/**
 * Empty as there is nothing needing special construction behavior.
 * to me the goal should be to minimize module usage
 *
 * <p>modules should be used for things such as
 *   - lazy singletons
 *   - sub configurations (guicey gives us Configuration for free)
 */
public class InboxModule extends DropwizardAwareModule<InboxConfig> {

  @Override
  protected void configure() {
    bind(PostService.class).to(InMemoryPostService.class);
  }

}
