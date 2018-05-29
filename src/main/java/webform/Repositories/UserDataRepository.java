package webform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webform.Entities.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {

    UserData findFirstByUseridAndName(Long id, String name);

    boolean existsByName(String name);

    @Transactional
    @Modifying
    void removeByName(String name);

    @Transactional
    @Modifying
    void removeAllByUserid(Long userid);
}
