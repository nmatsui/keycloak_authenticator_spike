package work.nmatsui.keycloak.authentication.authenticators.browser;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;

public class CheckAPICallUsernamePasswordForm extends UsernamePasswordForm {
  private static final Logger LOG = Logger.getLogger(CheckAPICallUsernamePasswordForm.class);

  @Override
  public void action(AuthenticationFlowContext context) {
    LOG.infof("action is called: context=%s", context);
    super.action(context);
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
