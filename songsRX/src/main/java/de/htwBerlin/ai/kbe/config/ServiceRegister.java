package de.htwBerlin.ai.kbe.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import de.htwBerlin.ai.kbe.filter.AuthorizationFilter;

@ApplicationPath("/rest")
public class ServiceRegister extends ResourceConfig {

	public ServiceRegister(){
		packages("de.htwBerlin.ai.kbe.services");
		register(new AuthorizationFilter());
	}
}
