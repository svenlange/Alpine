package alpine.auth;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.glassfish.jersey.server.ContainerRequest;
import org.junit.Before;
import org.junit.Test;

public class GitLabAuthenticationServiceTest {

  private GitLabAuthenticationService gitlabAuthService;
  private ContainerRequest requestContextMock;

  @Before
  public void setUp() {
    gitlabAuthService = new GitLabAuthenticationService(requestContextMock);
    requestContextMock = mock(ContainerRequest.class);
  }

  @Test
  public void isSpecified() {
//    when(requestContextMock.....)
  }

  @Test
  public void authenticate()


  }
}