package work.nmatsui.keycloak.authentication.authenticators.browser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;

public class CheckAPICallUsernamePasswordForm extends UsernamePasswordForm {
  private static final Logger LOG = Logger.getLogger(CheckAPICallUsernamePasswordForm.class);
  private enum CustomMessages {
    SECRET_WORD_DOES_NOT_MATCH
  }
  private static final String USERNAME_KEY = "username";
  private static final String SECRETWORD_KEY = "secretWord";

  private static final String CHECK_API_ENDPOINT = Optional.ofNullable(System.getenv("CHECK_API_ENDPOINT"))
      .orElse("http://localhost:3000/users/%s/check/");
  private static final String CHECK_API_SECRETWORD_KEY = Optional.ofNullable(System.getenv("CHECK_API_SECRETWORD_KEY"))
      .orElse("secretWord");

  private boolean checkSecretWord(AuthenticationFlowContext context, MultivaluedMap<String, String> formData) {
    String username = formData.getFirst(USERNAME_KEY);
    String secretWord = formData.getFirst(SECRETWORD_KEY);
    String msg = String.format("{\"%s\":\"%s\"}", CHECK_API_SECRETWORD_KEY, secretWord);
    LOG.infof("msfg: %s", msg);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(
        URI.create(String.format(CHECK_API_ENDPOINT, username)))
        .header("content-type", "application/json")
        .PUT(HttpRequest.BodyPublishers.ofString(msg))
        .build();
    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      int statusCode = response.statusCode();
      if (200 <= statusCode && statusCode < 300) {
        LOG.infof("check success: %s, %d", response.body(), statusCode);
        return true;
      } else {
        LOG.warnf("check failure: %s, %d", response.body(), statusCode);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      LOG.errorf("check api throws an eception: %s", e.getMessage());
    }

    Response challenge = context.form().setError(CustomMessages.SECRET_WORD_DOES_NOT_MATCH.name()).createLoginUsernamePassword();
    context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
    return false;
  }

  @Override
  public void action(AuthenticationFlowContext context) {
    LOG.infof("action is called: context=%s", context);
    MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
    if (formData.containsKey("cancel")) {
      context.cancelLogin();
      return;
    }
    if (!validateForm(context, formData)) {
      return;
    }
    if (!checkSecretWord(context, formData)) {
      return;
    }

    context.success();
  }

  @Override
  protected boolean validateForm(AuthenticationFlowContext context, MultivaluedMap<String, String> formData) {
    LOG.infof("validateForm is called: context=%s, formData=%s", context, formData);
    return super.validateForm(context, formData);
  }

  @Override
  public void authenticate(AuthenticationFlowContext context) {
    LOG.infof("authenticate is called: context=%s", context);
    super.authenticate(context);
  }

  @Override
  public boolean requiresUser() {
    return super.requiresUser();
  }

  @Override
  protected Response challenge(AuthenticationFlowContext context, MultivaluedMap<String, String> formData) {
    LOG.infof("challenge is called: context=%s, formData=%s", context, formData);
    return super.challenge(context, formData);
  }

  @Override
  public void close() {
    super.close();
  }
}
