package alpine.auth;

import java.security.Principal;
import javax.annotation.Nullable;
import javax.naming.AuthenticationException;
import org.glassfish.jersey.server.ContainerRequest;

public class GitLabAuthenticationService implements AuthenticationService {

  public GitLabAuthenticationService(ContainerRequest request) {

    // hole gitlab auth token aus request

  }

  @Override
  public boolean isSpecified() {
    // existiert ein gitlab auth token? wenn ja return true und mache anschließend authenticate
    return false;
  }

  @Nullable
  @Override
  public Principal authenticate() throws AuthenticationException {

    // ermittle principal und gebe in zurück


    return null;
  }
}
