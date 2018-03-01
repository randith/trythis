package io.awesome.managed;

import com.codahale.metrics.Counter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.awesome.model.CorId;
import io.dropwizard.setup.Environment;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class InitRunnable implements Runnable {
  private static final Logger LOG = LoggerFactory.getLogger(InitRunnable.class);

  private AtomicBoolean shouldRun;
  private Counter counter;

  @Inject
  public InitRunnable(Environment environment) {
    shouldRun = new AtomicBoolean(true);
    this.counter = environment.metrics().counter(this.getClass().getSimpleName());
  }

  public void setShouldRun(boolean shouldRun) {
    LOG.info("Setting shouldRun to {}", shouldRun);
    this.shouldRun.set(shouldRun);
  }

  public AtomicBoolean getShouldRun() {
    return shouldRun;
  }

  /**
   * do the thing.
   */
  public void run() {
    CorId corId = new CorId();
    counter.inc();
    if (shouldRun.get()) {
      LOG.info("{} Running because I should", corId);
    } else {
      LOG.info("{} Not Running because I should not", corId);
    }
  }

}
