package alpine.filters;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import alpine.model.ApiKey;
import java.net.URI;
import java.net.URISyntaxException;
import org.glassfish.jersey.server.ContainerRequest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AuthenticationFilterTest {

  private ContainerRequest requestContextMock;
  private AuthenticationFilter filter;

  @Before
  public void setUp() {
    filter = new AuthenticationFilter();
    requestContextMock = mock(ContainerRequest.class);
  }

  @Test
  public void authenticationForSwaggerIsBypassed() throws Exception {
    when(requestContextMock.getRequestUri()).thenReturn(new URI("/api/swagger"));
    filter.filter(requestContextMock);
    verify(requestContextMock, never()).abortWith(any());
  }

  @Test
  @Ignore
  public void apiKeyIsWorking() throws URISyntaxException {
    java.security.Principal expectedPrincipal = new ApiKey();

    when(requestContextMock.getRequestUri()).thenReturn(new URI("/moin"));
    when(requestContextMock.getHeaders()).thenReturn(null);

    filter.filter(requestContextMock);

    verify(requestContextMock).setProperty("Principal", expectedPrincipal);
  }

  @Test
  public void gitlabAuthentification() {

  }
}