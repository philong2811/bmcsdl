package hcmute.it.oracleDB.Repository;

import hcmute.it.oracleDB.DTO.ProfileInfoDTO;
import hcmute.it.oracleDB.Entity.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<User, Integer> {
  @Override
  @Transactional
  List<User> findAll();

  Optional<User> findByUsername(String username);

  @Override
  <S extends User> S save(S entity);

  Optional<User> findById(Integer integer);

  @Query(value = "SELECT decrypt_user_password(:password) from DUAL", nativeQuery = true)
  String decryptPassword(@Param("password") String password);

  List<User> findByRole(String role);

  @Transactional
  @Modifying
  @Query(value = "CALL create_user(:username, :password)", nativeQuery = true)
  void createUserSystem(@Param("username") String username, @Param("password") String password);

}
