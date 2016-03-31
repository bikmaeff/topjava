package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {

        User reference = entityManager.getReference(User.class, userId);
        userMeal.setUser(reference);

        if(userMeal.isNew()){
            entityManager.persist(userMeal);
            return userMeal;
        } else {
            return get(userMeal.getId(), userId) == null ? null :entityManager.merge(userMeal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        //UserMeal userMeal = entityManager.getReference(UserMeal.class, userId);
        //entityManager.remove(userMeal);

        //Query<UserMeal> query = entityManager.createQuery("DELETE FROM UserMeal u WHERE u.id=:id AND u.user=:id");
        //return query.setParameter("id", id).executeUpdate() != 0;
        return entityManager.createNamedQuery(UserMeal.DELETE)
                .setParameter("id", id)
                .setParameter("userId",userId)
                .executeUpdate() !=0 ;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> userMeals = entityManager.createNamedQuery(UserMeal.GET, UserMeal.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(userMeals);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return entityManager.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return entityManager.createNamedQuery(UserMeal.GET_BETWEEN, UserMeal.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}