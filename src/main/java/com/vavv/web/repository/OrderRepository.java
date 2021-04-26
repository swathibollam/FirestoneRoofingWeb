package com.vavv.web.repository;

import com.vavv.web.model.Order;
import com.vavv.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    /*List<User> findByUsernameEquals(String username);
    User findFirstByUsernameEquals(String username);
    List<User> findTop5ByUsernameEquals(String username);
    List<User> findDistinctUserByUsernameEquals(String username);
    @Query(value = "select * from User user where BINARY user.username = :username and BINARY user.password = :password", nativeQuery = true)
    List<User> queryByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    List<User> getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    User findFirstByGuid(String guid);*/

    Order findFirstByGuidAndUserId(String guid, String userId);

    List<Order> findByOrderDateBetween(Date starDate, Date endDate);

    @Query("select o from Order o where o.pickupOrDeliverDate between ?1 and ?2")
    List<Order> findByPickupOrDeliverDateBetween(Date starDate, Date endDate);

    @Override
    Order saveAndFlush(Order o);
}
