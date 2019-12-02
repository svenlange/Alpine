package alpine.auth.gitlab;

public class GitLabResponse {

  private String refresh_token;
  private String created_at;
  private String token_type;
  private String scope;
  private String access_token;
  private String id_token;

  public String getRefresh_token() {
    return refresh_token;
  }

  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getToken_type() {
    return token_type;
  }

  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public String getId_token() {
    return id_token;
  }

  public void setId_token(String id_token) {
    this.id_token = id_token;
  }

  @Override
  public String toString() {
    return "GitLabResponse{" +
        "refresh_token='" + refresh_token + '\'' +
        ", created_at='" + created_at + '\'' +
        ", token_type='" + token_type + '\'' +
        ", scope='" + scope + '\'' +
        ", access_token='" + access_token + '\'' +
        ", id_token='" + id_token + '\'' +
        '}';
  }
}
