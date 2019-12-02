package alpine.auth.gitlab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import alpine.auth.gitlab.GitLabAuthenticationService;
import alpine.auth.gitlab.GitLabServerWrapper;
import java.io.InputStream;
import java.net.URI;
import java.security.Principal;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicStatusLine;
import org.glassfish.jersey.server.ContainerRequest;
import org.junit.Before;
import org.junit.Test;

public class GitLabAuthenticationServiceTest {

  private ContainerRequest requestContextMock;
  private GitLabServerWrapper gitLabServerWrapperMock;

  @Before
  public void setUp() {
    requestContextMock = mock(ContainerRequest.class);
    gitLabServerWrapperMock = mock(GitLabServerWrapper.class);
  }

  @Test
  public void isSpecified_validUrlWithCode_true() throws Exception {
    when(requestContextMock.getRequestUri()).thenReturn(
        new URI("https://git.example.com/login/gitlab?code=545392af&state=qwertz"));

    GitLabAuthenticationService gitlabAuthService = new GitLabAuthenticationService(requestContextMock);

    assertEquals(true, gitlabAuthService.isSpecified());
  }

  @Test
  public void isSpecified_wrongCallbackUrlWithCode_false() throws Exception {
    when(requestContextMock.getRequestUri()).thenReturn(
        new URI("https://git.example.com/loooogin/gitlab?code=545392af&state=qwertz"));

    GitLabAuthenticationService gitlabAuthService = new GitLabAuthenticationService(requestContextMock);

    assertEquals(false, gitlabAuthService.isSpecified());
  }

  @Test
  public void isSpecified_validUrlButEmptyCodeParameter_false() throws Exception {
    when(requestContextMock.getRequestUri()).thenReturn(new URI("https://git.example.com/login/gitlab?code=&state=qwertz"));

    GitLabAuthenticationService gitlabAuthService = new GitLabAuthenticationService(requestContextMock);

    assertEquals(false, gitlabAuthService.isSpecified());
  }

  @Test
  public void isSpecified_validUrlButNonCodeParameter_false() throws Exception {
    when(requestContextMock.getRequestUri()).thenReturn(new URI("https://git.example.com/login/gitlab?blah=qwertz"));

    GitLabAuthenticationService gitlabAuthService = new GitLabAuthenticationService(requestContextMock);

    assertEquals(false, gitlabAuthService.isSpecified());
  }

  @Test
  public void authenticate_validOauthCode_returnsSuccessfullyAuthenticatedPrincipal() throws Exception {
    when(requestContextMock.getRequestUri()).thenReturn(new URI("https://git.example.com/login/gitlab?code=a2f0a7f6a735f2bd2911fde5c95b9600290899eb5914ad53f14bf4cacba8dacd&state=qwertz"));

    CloseableHttpResponse closeableHttpResponseMock = mock(CloseableHttpResponse.class);
    when(closeableHttpResponseMock.getStatusLine()).thenReturn(new BasicStatusLine(new ProtocolVersion("http", 1, 1), 200, "yeah"));
    BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
    basicHttpEntity.setContent(GitLabAuthenticationServiceTest.class.getResourceAsStream("success.json"));
    when(closeableHttpResponseMock.getEntity()).thenReturn(basicHttpEntity);

    when(gitLabServerWrapperMock.auth(anyString())).thenReturn(closeableHttpResponseMock);

    GitLabAuthenticationService gas = new GitLabAuthenticationService(requestContextMock);
    gas.setGitLabServerWrapper(gitLabServerWrapperMock);

    Principal principal = gas.authenticate();

    assertNotNull(principal);
  }
}