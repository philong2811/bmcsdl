package hcmute.it.oracleDB.Service;

import hcmute.it.oracleDB.DTO.ProfileDTO;
import hcmute.it.oracleDB.DTO.ProfileInfoDTO;
import hcmute.it.oracleDB.DTO.RoleDTO;
import hcmute.it.oracleDB.DTO.UserDTO;
import hcmute.it.oracleDB.DTO.UserInfoDTO;
import hcmute.it.oracleDB.Entity.Role;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface AdminService {
  public Iterable<UserInfoDTO> getUserInfoSystem();
  public UserDTO getByUserName(String username);
  public Iterable<ProfileInfoDTO> getAllProfile();
  public String createProfile(ProfileDTO profile);
  public String updateProfile(ProfileDTO profile);
  public String deleteProfile(String profileName);
  public String assignProfile(String profileName, String username);
  public List<Role> getAll();
  public String createRole(String roleName, String password);
  public String deteleRole(String roleName);
  public String updateRole(String roleName, String password);
  public String assignRoleToUser(String role, String user);
}
