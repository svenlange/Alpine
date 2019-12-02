package alpine.auth.gitlab;

import static org.apache.http.impl.client.HttpClients.createDefault;

import alpine.Config;
import alpine.Config.AlpineKey;
import java.net.URI;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

public class GitLabServerWrapper {

  private String GITLAB_SERVER_URL = Config.getInstance().getProperty(Config.AlpineKey.GITLAB_SERVER_URL);
  private String GITLAB_APPLICATION_ID = Config.getInstance().getProperty(AlpineKey.GITLAB_APPLICATION_ID);
  private String GITLAB_APPLICATION_SECRET = Config.getInstance().getProperty(AlpineKey.GITLAB_APPLICATION_SECRET);


  public CloseableHttpResponse auth(String code) {
    try (CloseableHttpClient httpclient = createDefault();) {
      URI uri = new URIBuilder(GITLAB_SERVER_URL)
          .setPath("/oauth/token")
          .setParameter("grant_type", "authorization_code")
          .setParameter("client_id", GITLAB_APPLICATION_ID)
          .setParameter("client_secret", GITLAB_APPLICATION_SECRET)
          .setParameter("code", code)
          .setParameter("redirect_uri", "http://localhost:8080/login/gitlab")
          .build();

      HttpPost authRequest = new HttpPost(uri);
      return httpclient.execute(authRequest);


    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  public void setGitlabServerUrl(String gitlabServerUrl) {
    this.GITLAB_SERVER_URL = gitlabServerUrl;
  }

  public void setGitlabApplicationId(String gitlabApplicationId) {
    this.GITLAB_APPLICATION_ID = gitlabApplicationId;
  }

  public void setGitlabApplicationSecret(String gitlabApplicationSecret) {
    this.GITLAB_APPLICATION_SECRET = gitlabApplicationSecret;
  }

}
