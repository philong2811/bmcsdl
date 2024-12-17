package hcmute.it.oracleDB.Repository;

import hcmute.it.oracleDB.Entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  @Override
  List<Role> findAll();

  @Override
  <S extends Role> S save(S entity);

  void deleteByRole(String roleName);

  @Query(value = "SELECT role, password_required FROM DBA_ROLES WHERE ROLE = UPPER(:roleName)", nativeQuery = true)
  Optional<Object[]> findRoleByRole(@Param("roleName") String roleName);

  @Query(value = "CALL star_CreateRole(?, ?)", nativeQuery = true)
  void createRole(@Param("roleName") String roleName, @Param("password") String password);

  @Query(value = "CALL star_DropRole(:roleName)", nativeQuery = true)
  void deteleRole(@Param("roleName") String roleName);

  @Query(value = "CALL star_AlterRole(:roleName, :password)", nativeQuery = true)
  void updateRole(@Param("roleName") String roleName, @Param("password") String password);

}
