package diploma.statistics.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Никита
 */
public class MacroClusteringTaskDao extends BaseDao implements Serializable {
    public void saveMacroClusteringTaskId(int taskId, int numberOfTuples) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO macroClusteringTasks (taskId, numberOfTuples, timestamp) VALUES (?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, taskId);
            statement.setInt(2, numberOfTuples);
            statement.setTimestamp(3, new Timestamp(new Date().getTime()));
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
}
