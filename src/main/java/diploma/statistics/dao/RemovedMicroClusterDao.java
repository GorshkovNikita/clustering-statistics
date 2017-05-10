package diploma.statistics.dao;

import diploma.statistics.RemovedMicroClusterStatistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Никита
 */
public class RemovedMicroClusterDao extends BaseDao {
    public void saveStatistics(RemovedMicroClusterStatistics statistics) {
        Connection connection = getConnection();
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            try {
                String insertRemovedStatistics = "INSERT INTO removedmicroclusters (clusterId, isPotential, topWords, numberOfDocuments, creationTime, lastUpdateTime) VALUES (?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(insertRemovedStatistics);
                preparedStatement.setInt(1, statistics.getClusterId());
                preparedStatement.setByte(2, statistics.getIsPotential());
                preparedStatement.setString(3, statistics.getTopWords());
                preparedStatement.setInt(4, statistics.getNumberOfDocuments());
                preparedStatement.setTimestamp(5, new Timestamp(statistics.getCreationTime()));
                preparedStatement.setTimestamp(6, new Timestamp(statistics.getLastUpdateTime()));
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null)
                        preparedStatement.close();
                }
                catch (SQLException ignored) {}
                try {
                    connection.close();
                }
                catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        RemovedMicroClusterDao dao = new RemovedMicroClusterDao();
        RemovedMicroClusterStatistics statistics = new RemovedMicroClusterStatistics();
        statistics.setCreationTime(100000000);
        statistics.setLastUpdateTIme(100050000);
        statistics.setTopWords("car,wall,qwe");
        statistics.setIsPotential((byte) 1);
        statistics.setNumberOfDocuments(200);
        dao.saveStatistics(statistics);
    }
}
