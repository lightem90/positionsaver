package eu.cartsc.fermate.db.cfg;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AnnotationConfiguration;

import eu.cartsc.fermate.properties.ConnectionFactory;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	private static String CONFIG_FILE_LOCATION = "/eu/cartsc/fermate/db/cfg/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static Configuration configuration = new AnnotationConfiguration();
	private static org.hibernate.SessionFactory sessionFactory;
	private static String configFile = CONFIG_FILE_LOCATION;
	protected static Logger log = Logger.getLogger(HibernateSessionFactory.class);

	static {
		try {
			Class helper = HibernateSessionFactory.class;
			InputStream stream = helper
					.getResourceAsStream("/eu/cartsc/fermate/properties/log4j.properties");
			// if (null == stream) {
			// stream = helper
			// .getResourceAsStream("/eu/cartsc/carsharing/core/moi/db/properties/log4j.properties");
			// }
			if (null == stream) {
				System.err
						.println("non trovo il file di configurazione della log4j");
			} else {
				Properties prop = new Properties();
				prop.load(stream);
				LogManager.resetConfiguration();
				PropertyConfigurator.configure(prop);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			configuration.configure(configFile);
			getDataConnection();
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	private HibernateSessionFactory() {
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException {
		Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 * 
	 */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 * 
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * return session factory
	 * 
	 * session factory will be rebuilded in the next call
	 */
	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
	 * return hibernate configuration
	 * 
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}
	
	private static void getDataConnection() {

		Properties prop = ConnectionFactory.getConnection();

		configuration.setProperty("hibernate.connection.url",prop.getProperty("url"));
		configuration.setProperty("hibernate.connection.username",prop.getProperty("username"));
		configuration.setProperty("hibernate.connection.password",prop.getProperty("password"));

		if (prop.getProperty("dialect") != null)
			configuration.setProperty("hibernate.dialect",prop.getProperty("dialect"));

		if (prop.getProperty("driver") != null)
			configuration.setProperty("hibernate.connection.driver_class",prop.getProperty("driver"));

		log.info("configuration.getProperty('hibernate.connection.url'): "
				+ configuration.getProperty("hibernate.connection.url"));
		log.info("configuration.getProperty('hibernate.connection.username'): "
				+ configuration.getProperty("hibernate.connection.username"));
		log.info("configuration.getProperty('hibernate.connection.password'): "
				+ configuration.getProperty("hibernate.connection.password"));
		log.info("configuration.getProperty('hibernate.dialect'): "
				+ configuration.getProperty("hibernate.dialect"));
		log.info("configuration.getProperty('hibernate.connection.driver_class'): "
				+ configuration
						.getProperty("hibernate.connection.driver_class"));
	}

}