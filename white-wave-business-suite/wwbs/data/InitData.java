package data;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * @author Austin
 * This class sets up hibernate and manages the connection though the life of the program
 */
public class InitData {

	private Configuration config;
	private ServiceRegistry serviceRegistry;
	private SessionFactory sessionFactory;

	/**
	 * Hibernate black magic thar be witchcraft here.
	 */
	public InitData() {
		this.config = new Configuration().configure();
		this.serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		this.sessionFactory = config.buildSessionFactory(serviceRegistry);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Close the session factory. Must be done before terminating the application.
	 */
	public void closeSessionFactory() throws Exception {
		if(this.sessionFactory == null) {
			throw new Exception("The Session Factory has not been intialized!");
		}
		
		else {
			try {
				this.sessionFactory.close();
			}
			catch (Exception e){
				throw new Exception("The session factory could not be closed!");
			}
		}
	}
}
