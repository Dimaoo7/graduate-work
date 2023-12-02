package ru.skypro.homework.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    UserEntity findUserEntityByPassword(String oldPass);

    UserEntity findUserEntityByUserNameAndPassword(String userName, String password);


    UserEntity findUserEntityByUserName(String userName);

    @Query(value = "delete from users where id notnull;", nativeQuery = true)
    void cleanAll();
}
