package hcmute.it.oracleDB.Service.Impl;

import hcmute.it.oracleDB.DTO.TransDTO;
import hcmute.it.oracleDB.DTO.UserDTO;
import hcmute.it.oracleDB.DTO.UserInfoDTO;
import hcmute.it.oracleDB.Entity.User;
import hcmute.it.oracleDB.Service.AccountService;
import hcmute.it.oracleDB.Service.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  @Autowired
  AccountService accountService;
  @Autowired
  ModelMapper modelMapper;

  @Override
  public UserDTO getByUserName(String username) {
    Optional<User> user = accountService.getUser(username);
    return user.map(value -> UserDTO.builder()
        .username(value.getUsername())
        .firstName(value.getFirstName())
        .lastName(value.getLastName())
        .address(value.getAddress())
        .email(value.getEmail())
        .sex(value.getSex())
        .age(value.getAge())
        .role(String.valueOf(value.getRole()))
        .status(value.getStatus())
        .build()).orElse(null);
  }
}
