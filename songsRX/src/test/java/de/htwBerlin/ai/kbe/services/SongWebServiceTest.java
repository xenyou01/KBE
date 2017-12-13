package de.htwBerlin.ai.kbe.services;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

public class SongWebServiceTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		return new ResourceConfig(SongWebService.class);
	}

}
