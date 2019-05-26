/*
 * Copyright 2019 Ping Identity Corporation
 * All Rights Reserved
 */
package io.microcode.labs.sync;

import com.hubspot.slack.client.SlackClient;
import com.unboundid.directory.sdk.sync.api.SyncPipePlugin;

public class SlackNotifierSyncPipePlugin extends SyncPipePlugin {

  private final SlackClient slackClient;

  public SlackNotifierSyncPipePlugin() {
    slackClient = null;
  }

  @Override
  public String getExtensionName() {
    return null;
  }

  @Override
  public String[] getExtensionDescription() {
    return new String[0];
  }

  @Override
  public void toString(StringBuilder stringBuilder) {

  }
}
