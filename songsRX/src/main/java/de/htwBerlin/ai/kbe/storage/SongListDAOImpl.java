package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.htwBerlin.ai.kbe.bean.SongList;

public class SongListDAOImpl implements SongListDAO {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("songsDB-PU");

	@Override
	public SongList getSongListById(Integer id) {
		EntityManager em = factory.createEntityManager();
		SongList songList = null;
		try {
			songList = em.find(SongList.class, id);
			if((songList == null))
				return null;
			return songList;
		} finally {
			em.close();
		}
	}

	@Override
	public Collection<SongList> getAllSongLists(Integer userId, String access) {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<SongList> list;
			if(access.equals("private"))
				list = em.createQuery("select sl from SongList sl where owner = " + userId, SongList.class);
			else if(access.equals("public"))
				list = em.createQuery("select sl from SongList sl where owner = " + userId + " and access = '" + access + "'", SongList.class);
			else
				return null;
			return list.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Integer addSongList(SongList list) throws PersistenceException {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(list);
			em.getTransaction().commit();
			return list.getId();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}finally {
			em.close();
		}
	}

	@Override
	public SongList deleteSongList(Integer id, String userId) throws PersistenceException {
		EntityManager em = factory.createEntityManager();
		SongList list = null;
		try {
			list = em.find(SongList.class, id);
			if(!list.getUserId().equals(userId))
				throw new IllegalAccessError();
			em.getTransaction().begin();
			em.remove(list);
			em.getTransaction().commit();
			return list;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new PersistenceException();
		}finally {
			em.close();
		}

	}
	//<property name="javax.persistence.sql-load-script-source" value="META-INF/sql/data.sql" /> 
	//<property name="javax.persistence.schema-generation.create-script-source" value="META-INF/sql/create.sql" />

}
