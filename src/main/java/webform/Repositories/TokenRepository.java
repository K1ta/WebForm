package webform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webform.Entities.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = "select token from tokens where token = :token limit 1", nativeQuery = true)
    String findFirstToken(
            @Param("token") String token
    );

    @Query(value = "select id from tokens where token = :token limit 1", nativeQuery = true)
    Long findFirstId(
            @Param("token") String token
    );

    @Transactional
    void removeAllByToken(String token);
}
