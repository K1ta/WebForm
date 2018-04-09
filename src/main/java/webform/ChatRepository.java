package webform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT COUNT(login) FROM users WHERE login = :login", nativeQuery = true)
    int checkByLogin(
            @Param("login") String login
    );

}
