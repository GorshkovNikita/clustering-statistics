package diploma.statistics.dao;

import java.io.Serializable;
import java.sql.*;
import java.util.Date;

/**
 * @author Никита
 */
public class PendingDao extends BaseDao implements Serializable {
    public void savePending(long pendingMessages, int spout) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO pending (pendingMessages, timestamp, spout) VALUES (?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setLong(1, pendingMessages);
            statement.setTimestamp(2, new Timestamp(new Date().getTime()));
            statement.setInt(3, spout);
            statement.executeUpdate();
        }
        catch (SQLException ignored) {}
        finally {
            try {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException ignored) {}
            try {
                connection.close();
            }
            catch (SQLException ignored) {}
        }
    }

    public long getLasPending() {
        Connection connection = getConnection();
        Statement statement = null;
        long pendingMessages = 0;
        try {
            String query = "SELECT pendingMessages FROM pending ORDER BY timestamp DESC";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            pendingMessages = resultSet.getLong(1);
        }
        catch (SQLException ignored) {}
        finally {
            try {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException ignored) {}
            try {
                connection.close();
            }
            catch (SQLException ignored) {}
        }
        return pendingMessages;
    }
}
