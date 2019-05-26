/*
 * Copyright 2019 Ping Identity Corporation
 * All Rights Reserved
 */
package io.microcode.labs;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientModule;
import com.hubspot.slack.client.SlackClientRuntimeConfig;
import com.hubspot.slack.client.SlackWebClient;
import io.microcode.labs.slack.ApplicationSlackClient;
import io.microcode.labs.slack.SlackClientType;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static java.lang.Thread.currentThread;
import static org.mockito.Mockito.mock;


public class ApplicationClientModule extends AbstractModule {

  private static final String TOKEN_PROP = "token";

  @Override
  protected void configure() {
    install(new SlackClientModule());

    Properties properties = new Properties();
    try {
      properties.load(
              Objects.requireNonNull(
                      currentThread()
                              .getContextClassLoader()
                              .getResourceAsStream("slack.properties")
              )
      );
    } catch (IOException e) {
      // TODO log the exception
      e.printStackTrace();
    }

    Names.bindProperties(binder(), properties);
  }

  @Provides
  @ApplicationSlackClient(SlackClientType.BASIC)
  public SlackClient providesBasicClient(SlackWebClient.Factory factory,
                                         @Named(TOKEN_PROP) String token) {
    return factory.build(
            SlackClientRuntimeConfig.builder()
                    .setTokenSupplier(() -> token)
                    .build()
    );
  }

  @Provides
  @ApplicationSlackClient(SlackClientType.MOCK)
  public SlackClient providesMockClient() {
    return mock(SlackClient.class);
  }
}
