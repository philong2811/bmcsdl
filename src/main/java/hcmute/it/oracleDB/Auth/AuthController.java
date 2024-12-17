package hcmute.it.oracleDB.Auth;

import hcmute.it.oracleDB.Config.Datasource;
import hcmute.it.oracleDB.Config.JwtService;
import hcmute.it.oracleDB.Config.UserContext;
import hcmute.it.oracleDB.DTO.UserRegister;
import hcmute.it.oracleDB.Entity.User;
import hcmute.it.oracleDB.Service.AccountService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin( origins = "*" , allowedHeaders = "*")
public class AuthController {
  public String getToken(){
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .getRequest()
        .getHeader("Authorization")
        .replace("Bearer ","");
  }
  @Autowired
  AccountService accountService;
  @Autowired
  JwtService jwtService;
  private final AuthService authService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<AuthRes> authenticate(@RequestBody AuthReq request){
    var check = authService.authenticate(request);
    if(check == null){
      return ResponseEntity.status(403).body(check);
    }

    UserContext test = new UserContext();
    test.setCurrentUser(request.getUsername(), request.getPassword());
    return ResponseEntity.ok(authService.authenticate(request));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthRes> register(@RequestBody UserRegister request){
    if(accountService.getUser(request.getUsername()).isEmpty()){
      request.setUsername(request.getUsername());
      UserContext.setCurrentUser(request.getUsername(), request.getPassword());
      return ResponseEntity.ok(authService.userRegister(request));
    }
    else{
      return null;
    }
  }

  @GetMapping("/check-user")
  public ResponseEntity<List<User>> checkUser(){
    return ResponseEntity.ok(accountService.getAll());
  }

  @GetMapping("/user-info")
  public ResponseEntity<Optional<User>> getUserInfo(){
    return ResponseEntity.ok(accountService.getUser(jwtService.extractUserName(getToken())));
  }

  @PostMapping("/check")
  public Boolean check(@RequestBody AuthReq request){
    return authService.check(request);
  }
}
