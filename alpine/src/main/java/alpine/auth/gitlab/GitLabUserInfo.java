package alpine.auth.gitlab;

import java.util.List;

public class GitLabUserInfo {

  private String email;
  private String email_verified;
  private List<String> groups;
  private String name;
  private String nickname;
  private String picture;
  private String profile;
  private String sub;
  private String sub_legacy;

  public String getEmail() {
    return email;
  }

  public String getEmail_verified() {
    return email_verified;
  }

  public List<String> getGroups() {
    return groups;
  }

  public String getName() {
    return name;
  }

  public String getNickname() {
    return nickname;
  }

  public String getPicture() {
    return picture;
  }

  public String getProfile() {
    return profile;
  }

  public String getSub() {
    return sub;
  }

  public String getSub_legacy() {
    return sub_legacy;
  }

  @Override
  public String toString() {
    return "GitLabUserInfo{" +
        "email='" + email + '\'' +
        ", email_verified='" + email_verified + '\'' +
        ", groups=" + groups +
        ", name='" + name + '\'' +
        ", nickname='" + nickname + '\'' +
        ", picture='" + picture + '\'' +
        ", profile='" + profile + '\'' +
        ", sub='" + sub + '\'' +
        ", sub_legacy='" + sub_legacy + '\'' +
        '}';
  }
}
