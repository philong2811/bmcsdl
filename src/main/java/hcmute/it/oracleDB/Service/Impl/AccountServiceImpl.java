package hcmute.it.oracleDB.Service.Impl;

import hcmute.it.oracleDB.Config.UserContext;
import hcmute.it.oracleDB.DTO.UserInfoDTO;
import hcmute.it.oracleDB.Entity.User;
import hcmute.it.oracleDB.Repository.AccountRepository;
import hcmute.it.oracleDB.Repository.ProfileRepository;
import hcmute.it.oracleDB.Repository.UserInfoRepository;
import hcmute.it.oracleDB.Service.AccountService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Autowired
  AccountRepository accountRepository;
  @Autowired
  UserInfoRepository userInfoRepository;

  private final ProfileRepository profileRepository;

  @Override
  public List<User> getAll() {
    return accountRepository.findAll();
  }

  @Override
  public Optional<User> getUser(String username) {
    return accountRepository.findByUsername(username);
  }

  @Override
  public List<User> getUserByRole(String role) {
    return accountRepository.findByRole(role);
  }

  @Override
  public Optional<UserInfoDTO> getUserENV() {
    return userInfoRepository.getUserENV();
  }

  @Override
  public List<String> testConnect(String username, String password) {
    return profileRepository.testConnect(username, password);
  }

}
