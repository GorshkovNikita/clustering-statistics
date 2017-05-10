package diploma.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Никита
 */
public class BigramFrequencyDao extends BaseDao {
    public void saveBigramFrequency(String bigram, int frequency) throws Exception {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String insertBigramFrequency = "INSERT INTO bigramFrequencies (bigram, frequency) VALUES (?,?)";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(insertBigramFrequency);
                preparedStatement.setString(1, bigram);
                preparedStatement.setInt(2, frequency);
                preparedStatement.executeUpdate();
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

    public void updateBigramFrequency(String bigram, int frequency) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String updateBigramFrequency = "UPDATE bigramFrequencies SET frequency = ? where bigram = ?";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(updateBigramFrequency);
                preparedStatement.setInt(1, frequency);
                preparedStatement.setString(2, bigram);
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
}
