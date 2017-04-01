package diploma.statistics.dao;

import diploma.statistics.MysqlConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Никита
 */
public abstract class BaseDao {
    public Connection getConnection() {
        Connection connection;
        try {
            Class.forName(MysqlConfig.JDBC_DRIVER);
            connection = DriverManager.getConnection(MysqlConfig.DB_URL, MysqlConfig.USER, MysqlConfig.PASSWORD);
            return connection;
        }
        catch (ClassNotFoundException | SQLException ex) {
            return null;
        }
    }
}
