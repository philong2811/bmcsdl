package hcmute.it.oracleDB.Repository;

import hcmute.it.oracleDB.Config.UserContext;
import hcmute.it.oracleDB.DTO.ProfileDTO;
import hcmute.it.oracleDB.DTO.ProfileInfoDTO;
import hcmute.it.oracleDB.Entity.User;
import java.util.List;

public interface ProfileRepository {

  Iterable<ProfileInfoDTO> findAllProfile();

  boolean checkProfileExist(String profileName);

  String createProfile(ProfileDTO request);

  String updateProfile(ProfileDTO request);
  String deleteProfile(String profileName);

  String assignProfileToUser(String profileName, String username);

  List<String> testConnect(String username, String password);
}
