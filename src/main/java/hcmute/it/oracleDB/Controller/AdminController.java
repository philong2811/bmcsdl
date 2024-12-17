package hcmute.it.oracleDB.Controller;

import hcmute.it.oracleDB.Auth.AuthReq;
import hcmute.it.oracleDB.Auth.AuthRes;
import hcmute.it.oracleDB.Auth.AuthService;
import hcmute.it.oracleDB.Config.JwtService;
import hcmute.it.oracleDB.Config.UserContext;
import hcmute.it.oracleDB.DTO.AssignProfileDTO;
import hcmute.it.oracleDB.DTO.CreateRoleDTO;
import hcmute.it.oracleDB.DTO.ProfileDTO;
import hcmute.it.oracleDB.DTO.ProfileInfoDTO;
import hcmute.it.oracleDB.DTO.ResDTO;
import hcmute.it.oracleDB.DTO.RoleDTO;
import hcmute.it.oracleDB.DTO.UserDTO;
import hcmute.it.oracleDB.DTO.UserInfoDTO;
import hcmute.it.oracleDB.Entity.Role;
import hcmute.it.oracleDB.Service.AccountService;
import hcmute.it.oracleDB.Service.AdminService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin( origins = "*" , allowedHeaders = "*")
public class AdminController {
  @Autowired
  AuthService authService;
  @Autowired
  JwtService jwtService;
  @Autowired
  AdminService adminService;
  @Autowired
  AccountService accountService;
  public String getToken(){
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .getRequest()
        .getHeader("Authorization")
        .replace("Bearer ","");
  }

  @RequestMapping("/user-detail")
  public ResDTO<UserDTO> getUserDetail(){
    UserDTO userDetail = adminService.getByUserName(jwtService.extractUserName(getToken()));
    return new ResDTO<>(userDetail, "Success", "ok");
  }

  @RequestMapping("/create-admin")
  public ResponseEntity<AuthRes> register(@RequestBody AuthReq request){
    if(accountService.getUser(request.getUsername()).isEmpty()){
      request.setUsername(request.getUsername());
      return ResponseEntity.ok(authService.adminRegister(request));
    }
    else{
      return null;
    }
  }

  @RequestMapping("/user-info-system")
  public ResDTO<Iterable<UserInfoDTO>> getAllUserInfoSystem(){
    return new ResDTO<>(adminService.getUserInfoSystem(), "Success", "Ok");
  }

  @RequestMapping("/get-env")
  public ResponseEntity<Optional<UserInfoDTO>> getUserENV(){
    return ResponseEntity.ok().body(accountService.getUserENV());
  }

  @RequestMapping("/profile")
  public ResDTO<Iterable<ProfileInfoDTO>> getProfile(){
    return new ResDTO<>(adminService.getAllProfile(), "Success", "Ok");
  }

  @RequestMapping("/create-profile")
  public ResponseEntity<String> createProfile(@RequestBody ProfileDTO request){
    try{
      return ResponseEntity.ok(adminService.createProfile(request));
    } catch (Exception e){
      return ResponseEntity.status(400).body("ERROR");
    }
  }

  @RequestMapping("/delete-profile/{profileName}")
  public ResponseEntity<String> deleteProfile(@PathVariable("profileName") String profileName){
    try{
      return ResponseEntity.ok(adminService.deleteProfile(profileName));
    } catch (Exception e){
      return ResponseEntity.status(400).body("profile does not exist");
    }
  }

  @RequestMapping("/update-profile")
  public ResponseEntity<String> updateProfile(@RequestBody ProfileDTO request){
    try{
      return ResponseEntity.ok(adminService.updateProfile(request));
    }catch (Exception e){
      return ResponseEntity.status(400).body("ERROR");
    }
  }

  @RequestMapping("/assign-profile")
  public ResponseEntity<String> assignProfile(@RequestBody AssignProfileDTO request){
    try{
      return ResponseEntity.ok(adminService.assignProfile(request.getProfileName(),
          request.getUsername()));
    }catch (Exception e){
      return ResponseEntity.status(400).body("ERROR");
    }
  }
  //

  @RequestMapping("/role")
  public ResDTO<List<Role>> getRole(){
    try{
      return new ResDTO<>(adminService.getAll(), "Success", "Ok");
    }catch (Exception e){
      return new ResDTO<>(null, e.getMessage(), "400");
    }
  }

  @RequestMapping("/role/create-role")
  public ResponseEntity<String> createRole(@RequestBody CreateRoleDTO role){
    return ResponseEntity.ok(adminService.createRole(role.getRoleName(), role.getPassword()));
  }

  @RequestMapping("/delete-role/{roleName}")
  public ResponseEntity<String> deleteRole(@PathVariable String roleName) {
    return ResponseEntity.ok(adminService.deteleRole(roleName));
  }

  @RequestMapping("role/update-role-pass")
  public ResponseEntity<String> updateRolePassword(@RequestBody RoleDTO role){
    return ResponseEntity.ok(adminService.updateRole(role.getRole(), role.getPasswordRequired()));
  }
  @RequestMapping("/test-connect")
  public List<String> testConnect(){
    return accountService.testConnect(UserContext.getCurrentUser().getUsername(), UserContext.getCurrentUser().getPassword());
  }
}
