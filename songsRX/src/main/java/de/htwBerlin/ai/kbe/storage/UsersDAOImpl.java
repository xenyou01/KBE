package de.htwBerlin.ai.kbe.storage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.ws.rs.core.Response;

import de.htwBerlin.ai.kbe.bean.User;

public class UsersDAOImpl implements UsersDAO {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("songsDB-PU");
	
	@Override
	public User findUserByUserId(String user_id) throws PersistenceException {
		EntityManager em = factory.createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM User u where userId='" + user_id+"'");
			User user = (User) q.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		} 
		catch (Exception e) {
			throw new PersistenceException();
		}finally {
			em.close();
		}
	}

}
