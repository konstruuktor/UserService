//package ru.john;
//
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
//@Slf4j
//public class UserDAO {
//
//    public void save(User user) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.persist(user);
//            transaction.commit();
//            log.info("Пользователь создан: {}", user);
//
//        }
//    }
//
//    public User getById(Long id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.find(User.class, id);
//        }
//    }
//
//    public List<User> getAll() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("FROM User", User.class).list();
//        }
//    }
//
//    public void update(User user) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.merge(user);
//            transaction.commit();
//            log.info("Пользователь обновлён: {}", user);
//        }
//    }
//
//    public void delete(User user) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.remove(user);
//            transaction.commit();
//            log.info("Пользователь удалён: {}", user);
//        }
//    }
//}