package diploma.statistics.dao;

import twitter4j.Status;

import java.sql.*;
import java.util.Date;

/**
 * @author Никита
 */
public class TermFrequencyDao extends BaseDao {
    public void saveTermFrequency(String term, int frequency) throws Exception {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String insertTermFrequency = "INSERT INTO termFrequencies (term, frequency) VALUES (?,?)";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(insertTermFrequency);
                preparedStatement.setString(1, term);
                preparedStatement.setInt(2, frequency);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
            } catch (Exception e) {
//                e.printStackTrace();
                throw e;
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
//                    se.printStackTrace();
                    throw se;
                }
            }
        }
    }

    public void updateTermFrequency(String term, int frequency) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String updateTermFrequency = "UPDATE termFrequencies SET frequency = ? where term = ?";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(updateTermFrequency);
                preparedStatement.setInt(1, frequency);
                preparedStatement.setString(2, term);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
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
}
