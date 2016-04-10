package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ramil on 07.04.16.
 */
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal u WHERE u.id=:id and u.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    UserMeal save(UserMeal item);

    @Query("SELECT u FROM UserMeal u WHERE u.id=:id and u.user.id=:userId")
    UserMeal get(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT u FROM UserMeal u WHERE u.user.id=:userId ORDER BY u.dateTime DESC")
    List<UserMeal> getAll(@Param("userId") int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserMeal u WHERE u.user.id=:userId")
    void deleteAll(@Param("userId") int userId);

    @Query("SELECT u FROM UserMeal u WHERE u.user.id=:userId AND u.dateTime>=:after AND u.dateTime <=:before")
    List<UserMeal> getBetween(@Param("after") LocalDateTime startDate,
                              @Param("before") LocalDateTime endDate,
                              @Param("userId") int userId);
}
