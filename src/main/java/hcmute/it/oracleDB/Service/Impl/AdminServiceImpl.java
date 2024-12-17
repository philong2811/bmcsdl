package hcmute.it.oracleDB.Service.Impl;

import hcmute.it.oracleDB.Auth.AuthService;
import hcmute.it.oracleDB.DTO.ProfileDTO;
import hcmute.it.oracleDB.DTO.ProfileInfoDTO;
import hcmute.it.oracleDB.DTO.RoleDTO;
import hcmute.it.oracleDB.DTO.UserDTO;
import hcmute.it.oracleDB.DTO.UserInfoDTO;
import hcmute.it.oracleDB.Entity.Role;
import hcmute.it.oracleDB.Entity.User;
import hcmute.it.oracleDB.Repository.ProfileRepository;
import hcmute.it.oracleDB.Repository.RoleRepository;
import hcmute.it.oracleDB.Repository.RoleUserRepository;
import hcmute.it.oracleDB.Repository.UserInfoRepository;
import hcmute.it.oracleDB.Service.AccountService;
import hcmute.it.oracleDB.Service.AdminService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminServiceImpl implements AdminService {

  @Autowired
  AuthService authService;
  @Autowired
  AccountService accountService;
  @Autowired
  ModelMapper modelMapper;
  @Autowired
  ProfileRepository profileRepository;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  RoleUserRepository roleUserRepository;
  @Autowired
  UserInfoRepository userInfoRepository;


  public Iterable<UserInfoDTO> getUserInfoSystem(){
    return userInfoRepository.getAll();
  }

  @Override
  public UserDTO getByUserName(String username) {
    UserDTO userDTO = modelMapper.map(accountService.getUser(username).get(), UserDTO.class);
    return userDTO;
  }

  //Profile
  @Override
  public Iterable<ProfileInfoDTO> getAllProfile() {
    return profileRepository.findAllProfile();
  }

  @Override
  public String createProfile(ProfileDTO request) {
    try{
      if (profileRepository.checkProfileExist(request.getProfileName())) {
        return "Profile already exists";
      }
      profileRepository.createProfile(request);
      return "success";
    }catch (Exception e){
      return e.getMessage();
    }
  }

  @Override
  public String updateProfile(ProfileDTO request) {
    if (profileRepository.checkProfileExist(request.getProfileName()))
      return profileRepository.updateProfile(request);
    return "Profile does not exists";
  }

  @Override
  public String deleteProfile(String profileName) {
    return profileRepository.deleteProfile(profileName);
  }

  @Override
  public String assignProfile(String profileName, String username) {
    return profileRepository.assignProfileToUser(profileName, username);
  }

  //Role
  @Override
  public List<Role> getAll() {
    return roleRepository.findAll();
  }

  @Override
  public String createRole(String roleName, String password) {
    try {
      var role = roleRepository.findRoleByRole(roleName);

      if (role.get().length > 0)
        return "Role already exists";

      Role roleEntity = Role.builder()
          .role(roleName)
          .passwordRequired(password.isEmpty() ? "NO" : "YES")
          .build();

      roleRepository.createRole(roleName, password);
      return "Success";
    } catch (Exception e) {
      return "Success";
    }
  }

  @Override
  public String deteleRole(String role) {
    try {
      var target = roleRepository.findRoleByRole(role);
      if (target.get().length == 0)
        return "Role does not exists";
      roleRepository.deleteByRole(role);
      roleRepository.deteleRole(role);
    } catch (Exception e) {

    }
    return "Success";
  }

  @Override
  public String updateRole(String roleName, String password) {
    try{
      roleRepository.updateRole(roleName, password);
    }catch (Exception e){

    }
    return "Success";
  }

  @Override
  public String assignRoleToUser(String role, String user) {
    try{
      return roleUserRepository.assignRoleToUser(role, user);
    }catch (Exception e){

    }
    return "Fail";
  }
}
