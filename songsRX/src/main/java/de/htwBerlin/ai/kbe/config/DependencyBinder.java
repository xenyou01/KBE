package de.htwBerlin.ai.kbe.config;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htwBerlin.ai.kbe.storage.SongListDAO;
import de.htwBerlin.ai.kbe.storage.SongListDAOImpl;
import de.htwBerlin.ai.kbe.storage.SongsDAO;
import de.htwBerlin.ai.kbe.storage.SongsDAOImpl;
import de.htwBerlin.ai.kbe.storage.UsersDAO;
import de.htwBerlin.ai.kbe.storage.UsersDAOImpl;

public class DependencyBinder extends AbstractBinder {
	
	@Override
	protected void configure() {
		bind(UsersDAOImpl.class).to(UsersDAO.class).in(Singleton.class);
		bind(SongListDAOImpl.class).to(SongListDAO.class).in(Singleton.class);
		bind(SongsDAOImpl.class).to(SongsDAO.class).in(Singleton.class);
		bind(SongsDAOImpl.class).to(SongsDAO.class).in(Singleton.class);
	}

}
