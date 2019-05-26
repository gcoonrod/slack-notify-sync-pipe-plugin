package io.microcode.labs;

import com.google.inject.Inject;
import com.hubspot.algebra.Result;
import com.hubspot.algebra.Results;
import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.methods.params.users.UserEmailParams;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.users.UsersInfoResponse;
import com.hubspot.slack.client.models.users.SlackUser;
import io.microcode.labs.slack.ApplicationSlackClient;
import io.microcode.labs.slack.SlackClientType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@Guice(modules = ApplicationClientModule.class)
public class ApplicationClientModuleTest {

  private final SlackClient slackClient;

  String emailAddress = "gregcoonrod@pingidentity.com";
  UserEmailParams.Builder builder = UserEmailParams.builder();

  String testId = "tesetId";


  @Inject
  public ApplicationClientModuleTest(
          @ApplicationSlackClient(SlackClientType.MOCK) SlackClient slackClient
  ) {
    this.slackClient = slackClient;
  }

  @BeforeMethod
  public void setUp() {
    // Setup test dependencies for getting user by email
    builder.setEmail(emailAddress);

    SlackUser.Builder userBuilder = SlackUser.builder();
    userBuilder.setUsername("testUser");
    userBuilder.setId(testId);

    UsersInfoResponse.Builder responseBuilder =
            UsersInfoResponse.builder();
    responseBuilder.setUser(userBuilder.build());
    responseBuilder.setOk(true);

    CompletableFuture<Result<UsersInfoResponse, SlackError>> mockResponse
            = new CompletableFuture<>();
    mockResponse.complete(Results.ok(responseBuilder.build()));

    when(slackClient.lookupUserByEmail(builder.build()))
            .thenReturn(mockResponse);

  }

  @Test
  public void listUserTest() {
    CompletableFuture<Result<UsersInfoResponse, SlackError>> userInfoFuture =
            slackClient.lookupUserByEmail(builder.build());

    UsersInfoResponse usersInfoResponse =
            userInfoFuture.join().unwrapOrElseThrow();

    assertEquals(usersInfoResponse.getUser().getId(), testId);
  }
}