package diploma.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Никита
 */
public class TweetBigramDao extends BaseDao {
    public void saveTweetBigram(String tweetId, String bigram) throws Exception {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String insertTermFrequency = "INSERT INTO tweetBigrams (tweetId, bigram) VALUES (?,?)";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(insertTermFrequency);
                preparedStatement.setString(1, tweetId);
                preparedStatement.setString(2, bigram);
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
}
