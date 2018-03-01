package io.awesome;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.installer.feature.ManagedInstaller;
import ru.vyarus.dropwizard.guice.module.installer.feature.health.HealthCheckInstaller;
import ru.vyarus.dropwizard.guice.module.installer.feature.jersey.ResourceInstaller;

public class InboxApplication extends Application<InboxConfig> {

  @Override
  public void initialize(Bootstrap<InboxConfig> bootstrap) {
    super.initialize(bootstrap);

    bootstrap.setConfigurationSourceProvider(
        new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
            new EnvironmentVariableSubstitutor(false)));

    GuiceBundle<InboxConfig> guiceBundle = GuiceBundle.<InboxConfig>builder()
        .modules(new InboxModule())
        .installers(ManagedInstaller.class, HealthCheckInstaller.class, ResourceInstaller.class)
        .enableAutoConfig("io.awesome")
        .build();
    bootstrap.addBundle(guiceBundle);
  }

  @Override
  public void run(InboxConfig configuration, Environment environment) throws Exception {
    // purposely empty as Installers handle everything
  }

  /**
   * Mainline to allow a run configuration to be easily used to spin up the rest application
   * service locally.
   *
   * @param args use "server" to start the server
   */
  public static void main(String ... args) throws Exception {
    // Ensure the JVM will refresh the cached dns resolved IP values
    java.security.Security.setProperty("networkaddress.cache.ttl", "60");

    new InboxApplication().run(args);
  }
}
