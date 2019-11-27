package alpine.auth;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.security.Principal;
import org.glassfish.jersey.server.ContainerRequest;
import org.junit.Before;
import org.junit.Test;

public class GitLabAuthenticationServiceTest {

  private GitLabAuthenticationService gitlabAuthService;
  private ContainerRequest requestContextMock;

  @Before
  public void setUp() {
    requestContextMock = mock(ContainerRequest.class);
  }


  @Test
  public void init() throws Exception {
    when(requestContextMock.getRequestUri()).thenReturn(new URI("http://blah/login/gitlab?code=123123123&state=234"));

    GitLabAuthenticationService as = new GitLabAuthenticationService("", "", requestContextMock);

  }

  @Test
  public void isSpecified() {
//    when(requestContextMock.....)
  }

  @Test
  public void authenticate() throws Exception {
    Principal authenticate = gitlabAuthService.authenticate();


  }
}