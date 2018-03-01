package io.awesome.managed;

import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.Duration;
import java.util.concurrent.TimeUnit;

public class ExampleManaged implements Managed {

  private final Environment environment;
  private final InitRunnable initRunnable;

  @Inject
  public ExampleManaged(Environment environment, InitRunnable initRunnable) {
    this.environment = environment;
    this.initRunnable = initRunnable;
  }

  @Override
  public void start() throws Exception {
    environment.lifecycle().scheduledExecutorService("initializeScheduledService")
        .threads(1)
        .shutdownTime(Duration.milliseconds(1000L))
        .build()
        .scheduleWithFixedDelay(initRunnable, 0L, 10L, TimeUnit.SECONDS);
  }

  @Override
  public void stop() throws Exception {
  }

}
