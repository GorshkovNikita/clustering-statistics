package diploma.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Никита
 */
public class TweetTermDao extends BaseDao {
    public void saveTweetTerm(String tweetId, String term) throws Exception {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String insertTermFrequency = "INSERT INTO tweetTerms (tweetId, term) VALUES (?,?)";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(insertTermFrequency);
                preparedStatement.setString(1, tweetId);
                preparedStatement.setString(2, term);
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
}
