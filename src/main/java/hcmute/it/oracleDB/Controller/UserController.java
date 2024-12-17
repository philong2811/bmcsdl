package hcmute.it.oracleDB.Controller;

import hcmute.it.oracleDB.Config.JwtService;
import hcmute.it.oracleDB.DTO.ResDTO;
import hcmute.it.oracleDB.DTO.TransDTO;
import hcmute.it.oracleDB.DTO.UserDTO;
import hcmute.it.oracleDB.Service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/user")
@CrossOrigin( origins = "*" , allowedHeaders = "*")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  JwtService jwtService;


  public String getToken(){
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .getRequest()
        .getHeader("Authorization")
        .replace("Bearer ","");
  }

  @RequestMapping("/user-detail")
  public ResDTO<UserDTO> getUserDetail(){
    UserDTO userDetail = userService.getByUserName(jwtService.extractUserName(getToken()));
    return new ResDTO<>(userDetail, "Success", "ok");
  }

}
