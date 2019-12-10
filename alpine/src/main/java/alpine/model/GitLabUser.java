package alpine.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GitLabUser implements Serializable, Principal, UserPrincipal {

  private static final long serialVersionUID = 2064922178362480872L;

  @Override
  public long getId() {
    return 0;
  }

  @Override
  public void setId(long id) {

  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public void setUsername(String username) {

  }

  @Override
  public String getEmail() {
    return null;
  }

  @Override
  public void setEmail(String email) {

  }

  @Override
  public List<Team> getTeams() {
    return null;
  }

  @Override
  public void setTeams(List<Team> teams) {

  }

  @Override
  public List<Permission> getPermissions() {
    return null;
  }

  @Override
  public void setPermissions(List<Permission> permissions) {

  }

  @Override
  public String getName() {
    return null;
  }
}
