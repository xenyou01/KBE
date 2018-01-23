package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htwBerlin.ai.kbe.bean.Song;

public class SongsDAOImpl implements SongsDAO {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("songsDB-PU");

	@Override
	public Song getSong(Integer id) {
		EntityManager em = factory.createEntityManager();
		Song song = null;
		try {
			song = em.find(Song.class, id);
		} finally {
			em.close();
		}
		return song;
	}

	@Override
	public Collection<Song> getAllSong() {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Song> songList = em.createQuery("select s from Song s", Song.class);
			return songList.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Integer addSong(Song song) throws PersistenceException {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(song);
			em.getTransaction().commit();
			return song.getId();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new PersistenceException();
		}finally {
			em.close();
		}
	}

	@Override
	public boolean updateSong(Integer id, Song song) {
		EntityManager em = factory.createEntityManager();
		try {
			Song existingSong = em.find(Song.class, id);
			if(existingSong == null)
				return false;
			song.setId(id);
			existingSong = song;
			em.getTransaction().begin();
			em.merge(existingSong);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new PersistenceException();
		}finally {
			em.close();
		}
	}

	@Override
	public Song deleteSong(Integer id) {
		EntityManager em = factory.createEntityManager();
		Song song = null;
		try {
			song = em.find(Song.class, id);
			if(song == null)
				return null;
			em.getTransaction().begin();
			em.remove(song);
			em.getTransaction().commit();
			return song;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new PersistenceException();
		}finally {
			em.close();
		}
	}

}
