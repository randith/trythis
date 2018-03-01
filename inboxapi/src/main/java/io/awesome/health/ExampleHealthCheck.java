package io.awesome.health;

import ru.vyarus.dropwizard.guice.module.installer.feature.health.NamedHealthCheck;

@SuppressWarnings("unused")
public class ExampleHealthCheck extends NamedHealthCheck {

  public String getName() {
    return "ExampleHealthCheck";
  }

  protected Result check() throws Exception {
    return Result.healthy("This is always healthy");
  }
}
