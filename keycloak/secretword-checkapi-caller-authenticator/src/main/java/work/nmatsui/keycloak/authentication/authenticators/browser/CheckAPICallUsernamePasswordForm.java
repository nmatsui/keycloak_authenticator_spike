package work.nmatsui.keycloak.authentication.authenticators.browser;

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

    String secretWord = formData.getFirst("secretWord");
    LOG.infof("secretWord: %s", secretWord);
    if (!"checkapi".equals(secretWord)) {
      Response challenge =  context.form().setError(CustomMessages.SECRET_WORD_DOES_NOT_MATCH.name()).createLoginUsernamePassword();
      context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
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
