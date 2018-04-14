package webform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*@Query(value = "SELECT COUNT(id) FROM users WHERE login = :login", nativeQuery = true)
    int countAllUsersByLogin(
            @Param("login") String login
    );

    @Query(value = "SELECT * FROM users WHERE login = :login AND password = :password", nativeQuery = true)
    User getUserByLoginAndPassword(
            @Param("login") String login,
            @Param("password") String password
    );

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users(login, password) VALUES(:login, :password)", nativeQuery = true)
    void addUser(
            @Param("login") String login,
            @Param("password") String password
    );

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    User getUserById(
            @Param("id") Long id
    );*/

}
