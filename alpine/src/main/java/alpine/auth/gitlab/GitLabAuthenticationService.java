package alpine.auth.gitlab;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.http.HttpStatus.SC_OK;

import alpine.auth.AuthenticationService;
import alpine.model.GitLabUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.naming.AuthenticationException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URLEncodedUtils;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * @author Sven Lange
 * @since 1.6.x todo
 */
public class GitLabAuthenticationService implements AuthenticationService {

  private String code;
  private GitLabServerWrapper gitLabServerWrapper = new GitLabServerWrapper();

  public GitLabAuthenticationService(ContainerRequest request) {
    URI requestUri = request.getRequestUri();
    if (!"/login/gitlab".equals(requestUri.getRawPath())) {
      return;
    }

    Optional<NameValuePair> codeParameter = URLEncodedUtils.parse(requestUri, UTF_8).stream()
        .filter(nameValuePair -> nameValuePair.getName().equals("code"))
        .findFirst();

    codeParameter.ifPresent(
        nameValuePair -> this.code = nameValuePair.getValue()
    );
  }

  @Override
  public boolean isSpecified() {
    return code != null && !code.isEmpty();
  }

  @Nullable
  @Override
  public Principal authenticate() throws AuthenticationException {
    CloseableHttpResponse response = gitLabServerWrapper.auth(code);

    if (response.getStatusLine().getStatusCode() != SC_OK) {
      throw new AuthenticationException();
    }

    GitLabResponse gitLabResponse = getGitLabResponse(response);
    System.out.println(gitLabResponse);

    CloseableHttpResponse response2 = gitLabServerWrapper.getUserInfo(gitLabResponse.getAccess_token());
    GitLabUserInfo gitLabUserInfo = getGitLabUserInfo(response2);

    GitLabUser user = new GitLabUser();
    user.setEmail(gitLabUserInfo.getEmail());
    user.setUsername(gitLabUserInfo.getNickname());
    return user;
  }

  private GitLabUserInfo getGitLabUserInfo(CloseableHttpResponse response) {
    GitLabUserInfo gitLabUserInfo = null;
    try {
      ObjectMapper mapper = new ObjectMapper();
      HttpEntity entity = response.getEntity();
      gitLabUserInfo = mapper.readValue(entity.getContent(), GitLabUserInfo.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return gitLabUserInfo;
  }

  private GitLabResponse getGitLabResponse(CloseableHttpResponse response) {
    ObjectMapper mapper = new ObjectMapper();

    GitLabResponse gr = null;
    try {
      gr = mapper.readValue(response.getEntity().getContent(), GitLabResponse.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return gr;
  }

  public void setGitLabServerWrapper(GitLabServerWrapper gitLabServerWrapper) {
    this.gitLabServerWrapper = gitLabServerWrapper;
  }
}
