package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
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
        if(userMeal.isNew()){
            entityManager.persist(userMeal);
            return userMeal;
        } else {
            return entityManager.merge(userMeal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        //UserMeal userMeal = entityManager.getReference(UserMeal.class, userId);
        //entityManager.remove(userMeal);

        //Query<UserMeal> query = entityManager.createQuery("DELETE FROM UserMeal u WHERE u.id=:id AND u.user=:id");
        //return query.setParameter("id", id).executeUpdate() != 0;
        return entityManager.createNamedQuery(UserMeal.DELETE).setParameter(id,userId).executeUpdate() !=0 ;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return entityManager.find(UserMeal.class,id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return null;//entityManager.createNamedQuery(userId, UserMeal.class).getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}