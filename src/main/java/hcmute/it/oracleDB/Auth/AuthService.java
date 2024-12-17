package hcmute.it.oracleDB.Auth;

import hcmute.it.oracleDB.Common.TypeUserEnum;
import hcmute.it.oracleDB.Config.JwtService;
import hcmute.it.oracleDB.DTO.UserRegister;
import hcmute.it.oracleDB.Entity.User;
import hcmute.it.oracleDB.Repository.AccountRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AccountRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthRes userRegister(UserRegister request){
    var user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .address(request.getAddress())
        .email(request.getEmail())
        .age(request.getAge())
        .sex(request.getSex())
        .createDate(new Date())
        .role(TypeUserEnum.USER)
        .status("unlock")
        .build();
    repository.save(user);
    repository.createUserSystem(request.getUsername(), request.getPassword());
    var jwtToken = jwtService.generateToken(user);
    return AuthRes.builder().token(jwtToken).build();
  }

  public AuthRes adminRegister(AuthReq request){
    var user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .createDate(new Date())
        .role(TypeUserEnum.ADMIN)
        .status("unlock")
        .build();
    repository.save(user);
    repository.createUserSystem(request.getUsername(), request.getPassword());
    var jwtToken = jwtService.generateToken(user);
    return AuthRes.builder().token(jwtToken).build();
  }


  public AuthRes resetPassword(String password,User newUser){
    newUser.setPassword(passwordEncoder.encode(password));
    repository.save(newUser);
    var jwtToken = jwtService.generateToken(newUser);
    return AuthRes.builder().token(jwtToken).build();
  }

  public AuthRes authenticate(AuthReq request){
    var user = repository.findByUsername(request.getUsername()).orElseThrow();
    var decodeP = repository.decryptPassword(user.getPassword());
    try{
      if(passwordEncoder.matches(request.getPassword(), decodeP)){
        var jwtToken = jwtService.generateToken(user);
        return AuthRes.builder().token(jwtToken).build();
      }
    }catch (Exception e){

    }

    return null;
  }

  public Boolean check(AuthReq request){
    try{
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getUsername(),
              request.getPassword()
          )
      );
      return true;
    }catch(Exception e){
      return false;
    }
  }
}
