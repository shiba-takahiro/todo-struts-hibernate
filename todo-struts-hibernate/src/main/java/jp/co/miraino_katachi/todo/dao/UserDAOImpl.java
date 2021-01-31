package jp.co.miraino_katachi.todo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.entity.User;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

public class UserDAOImpl implements UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	private EntityManagerFactory emf;
	private EntityManager em;

	public UserDAOImpl() {
		emf = Persistence.createEntityManagerFactory("manager1");
	}

	@Override
	public User getUser(String name, String pass) throws DAOException {
		User user = null;
		em = emf.createEntityManager();

		// パスワードをハッシュ化
		pass = PasswordUtil.getSafetyPassword(pass, name);

		try {
			// ユーザ名とパスワードでユーザテーブルを検索する
			user = em.createQuery("from User where user = :user and pass = :pass", User.class)
					.setParameter("user", name)
					.setParameter("pass", pass)
					.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			em.close();
		}

		return user;
	}

	@Override
	public List<User> getUsers() throws DAOException {
		List<User> list = null;
		em = emf.createEntityManager();

		try {
			list = em.createQuery("from User", User.class).getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			em.close();
		}

		return list;
	}

	@Override
	public User getUser(int id) throws DAOException {
		User user = null;

		em = emf.createEntityManager();
		try {
			user = em.find(User.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			em.close();
		}
		return user;
	}

}
