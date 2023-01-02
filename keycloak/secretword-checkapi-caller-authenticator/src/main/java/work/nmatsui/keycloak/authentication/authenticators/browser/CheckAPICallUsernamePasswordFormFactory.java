package work.nmatsui.keycloak.authentication.authenticators.browser;

import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class CheckAPICallUsernamePasswordFormFactory implements AuthenticatorFactory {
  private static final String PROVIDER_ID = "checkapi-call-username-password-form";
  private static final String DISPLAY_TYPE = "CheckAPI Call Username Password Form";
  private static final String HELP_TEXT = "Validates a username and password from login form, and calls CheckAPI with a secret word gotten from login form to validate it.";
  private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
    AuthenticationExecutionModel.Requirement.REQUIRED
  };
  private static final CheckAPICallUsernamePasswordForm SINGLETON = new CheckAPICallUsernamePasswordForm();

  @Override
  public void close() {
    // Nothing to do
  }

  @Override
  public Authenticator create(KeycloakSession session) {
    return SINGLETON;
  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public void init(Scope scope) {
    // Nothing to do
  }

  @Override
  public void postInit(KeycloakSessionFactory factory) {
    // Nothing to do
  }

  @Override
  public String getDisplayType() {
    return DISPLAY_TYPE;
  }

  @Override
  public String getReferenceCategory() {
    return PasswordCredentialModel.TYPE;
  }

  @Override
  public Requirement[] getRequirementChoices() {
    return REQUIREMENT_CHOICES;
  }

  @Override
  public boolean isConfigurable() {
    return false;
  }

  @Override
  public boolean isUserSetupAllowed() {
    return false;
  }

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {
    return null;
  }

  @Override
  public String getHelpText() {
    return HELP_TEXT;
  }
}
