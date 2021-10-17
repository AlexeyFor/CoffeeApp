package by.training.coffeeproject.dao.pool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.dao.DaoException;

/**
 * @author AlexeySupruniuk
 *
 *         Class for creating and holding some connections to database.
 * @see resources\\database.properties
 */
public final class ConnectionPool {

	private static final Logger LOG = LogManager.getLogger(ConnectionPool.class);

	private String url;
	private int maxSize;
	private int checkConnectionTimeout;
	private Properties properties = new Properties();
	private ReentrantLock lock = new ReentrantLock();

	// блокируещая очередь first in first out
	private BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
	// потокобезопасный список, который не хранит повторяющиеся эл-ты
	// хранит используемые connections
	private Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

	private ConnectionPool() {
	}

	private static ConnectionPool instance = new ConnectionPool();

	public static ConnectionPool getInstance() {
		return instance;
	}

	/**
	 * return pooledConnection from LinkedBlockingQueue freeConnections
	 * 
	 * @return PooledConnection
	 * @throws DaoException
	 */
	public Connection getConnection() throws DaoException {
		LOG.info("start getConnection");
		lock.lock();
		try {
			PooledConnection connection = null;
			while (connection == null) {
				try {
					// если очередь не пустая, забираем первый её эл-т
					if (!freeConnections.isEmpty()) {
						connection = freeConnections.take();
						// если соединение не работает, пробуем закрыть
						if (!connection.isValid(checkConnectionTimeout)) {
							try {
								connection.getConnection().close();
							} catch (SQLException e) {
								LOG.debug("wrong SQL " + e.getMessage());
							}
							connection = null;
						}
						// если очередь не пустая, проверяем не превышено ли число соединений с БД
					} else if (usedConnections.size() < maxSize) {
						connection = createConnection();
						// если превышено, то вот
					} else {
						LOG.debug("The limit of number of database connections is exceeded");
						throw new DaoException();
					}
				} catch (InterruptedException e) {
					LOG.debug("thread was interrupted ", e);
					throw new DaoException(e);
				} catch (SQLException e) {
					LOG.debug("wrong SQL " + e.getMessage());
				}
			}
			// если все в порядке, то добавляем connection в usedConnections
			usedConnections.add(connection);
			LOG.debug(String.format(
					"Connection was received from pool. Current pool size: %d used connections; %d free connection",
					usedConnections.size(), freeConnections.size()));
			return connection;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Return connection from usedConnection to freeConnections
	 * 
	 * @param connection
	 */
	public void freeConnection(PooledConnection connection) {
		LOG.info("start freeConnection");
		lock.lock();
		try {
			try {
				if (connection.isValid(checkConnectionTimeout)) {
					connection.clearWarnings();
					// все запросы в SQL - автоматически в одну группу/коммит
					connection.setAutoCommit(true);
					usedConnections.remove(connection);
					freeConnections.put(connection);
					LOG.debug(String.format(
							"Connection was returned into pool. Current pool size: %d used connections; %d free connection",
							usedConnections.size(), freeConnections.size()));
				}
			} catch (InterruptedException e) {
				LOG.debug("thread was interrupted ", e);
				try {
					connection.getConnection().close();
				} catch (SQLException e2) {
					LOG.debug("wrong SQL " + e2.getMessage());
				}
			} catch (SQLException e) {
				LOG.debug("wrong SQL " + e.getMessage());
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * initialization of ConnectionPool (LinkedBlockingQueue<>() freeConections)
	 * 
	 * @param url
	 * @param propertiesPath
	 * @param startSize
	 * @param maxSize
	 * @param checkConnectionTimeout
	 * @throws DaoException
	 * @throws
	 */
	public void init(String url, String propertiesPath, int startSize, int maxSize, int checkConnectionTimeout)
			throws DaoException {
		LOG.info("start init");
		lock.lock();
		try {
			try {
				destroy();
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.url = url;
				setProperties(propertiesPath);
				this.maxSize = maxSize;
				this.checkConnectionTimeout = checkConnectionTimeout;
				for (int counter = 0; counter < startSize; counter++) {
					freeConnections.put(createConnection());
				}
			} catch (SQLException | InterruptedException e) {
				LOG.fatal("It is impossible to initialize connection pool", e);
				throw new DaoException(e);
			}
		} finally {
			lock.unlock();
		}
	}

	// исключительно для init()
	private PooledConnection createConnection() throws SQLException {
		return new PooledConnection(DriverManager.getConnection(url, properties));
	}

	/**
	 * set properties file
	 * 
	 * @param path
	 * @throws DaoException
	 */
	private void setProperties(String path) throws DaoException {
		try {
			properties.load(new FileReader(path));
		} catch (FileNotFoundException e) {
			LOG.fatal("It is impossible to initialize connection pool ", e);
			throw new DaoException(e);
		} catch (IOException e) {
			LOG.fatal("It is impossible to initialize connection pool ", e);
			throw new DaoException(e);
		}
	}

	/**
	 * destroy all connections from usedConnections and freeConnections
	 */
	public void destroy() {
		LOG.info("start destroy");
		lock.lock();
		try {
			usedConnections.addAll(freeConnections);
			freeConnections.clear();
			for (PooledConnection connection : usedConnections) {
				try {
					connection.getConnection().close();
				} catch (SQLException e) {
					LOG.debug(e.getMessage());
				}
			}
			usedConnections.clear();
		} finally {
			lock.unlock();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
	}
}
