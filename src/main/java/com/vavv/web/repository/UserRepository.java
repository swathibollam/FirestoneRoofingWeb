package com.vavv.web.repository;

import com.vavv.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByUsernameEquals(String username);
    User findFirstByUsernameEquals(String username);
    List<User> findTop5ByUsernameEquals(String username);
    List<User> findDistinctUserByUsernameEquals(String username);
    @Query(value = "select * from User user where BINARY user.username = :username and BINARY user.password = :password and BINARY user.active = 1", nativeQuery = true)
    List<User> queryByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    List<User> getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    User findFirstByGuid(String guid);
    @Query(value = "select new User(user.guid, user.firstName, user.lastName) " +
            " from User user where user.active = 1")
    List<User> findAllActiveUsers();
}
