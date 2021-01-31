package jp.co.miraino_katachi.todo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.entity.Item;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

public class ItemDAOImpl implements ItemDAO {
	private static final Logger logger = LoggerFactory.getLogger(ItemDAOImpl.class);

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
	private EntityManager em;

	public Item getItem(int id) throws DAOException {
		Item item = null;
		em = emf.createEntityManager();
		try {
			item = em.find(Item.class, id);
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			em.close();
		}
		return item;
	}

	public List<Item> getItemList() throws DAOException {
		List<Item> list = null;
		em = emf.createEntityManager();
		try {
			list = em.createQuery("from Item order by expireDate", Item.class).getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			em.close();
		}
		return list;
	}

	public List<Item> getItemListByKeyword(String keyword) throws DAOException {
		List<Item> list = null;
		em = emf.createEntityManager();

		StringBuffer queryString = new StringBuffer();
		queryString.append("from Item where name like :keyword");
		queryString.append(" or expireDate like :keyword");
		queryString.append(" or finishedDate like :keyword");
		queryString.append(" or user.familyName like :keyword");
		queryString.append(" or user.firstName like :keyword");
		queryString.append(" order by expireDate");
		try {
			list = em.createQuery(queryString.toString(), Item.class)
					.setParameter("keyword", "%" + keyword + "%")
					.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			em.close();
		}
		return list;
	}

	public boolean add(Item item) throws DAOException {
		boolean isSuccess = false;
		em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			em.persist(item);
			tx.commit();;
			isSuccess = true;
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			em.close();
		}
		return isSuccess;
	}

	public boolean update(Item item) throws DAOException {
		boolean isSuccess = false;
		em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			em.merge(item);
			tx.commit();
			isSuccess = true;
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			em.close();
		}
		return isSuccess;
	}

	public boolean delete(int id) throws DAOException {
		boolean isSuccess = false;
		em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Item item = em.find(Item.class, id);
			em.remove(item);
			tx.commit();
			isSuccess = true;
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			em.close();
		}
		return isSuccess;
	}
}
