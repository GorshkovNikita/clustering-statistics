package diploma.statistics.dao;

import twitter4j.Status;

import java.sql.*;
import java.util.Date;

/**
 * @author Никита
 */
public class TweetDao extends BaseDao {
    public void saveTweet(Status status) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String insertTweet = "INSERT INTO tweets (tweetId, tweetText, creationTime) VALUES (?,?,?)";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(insertTweet, Statement.RETURN_GENERATED_KEYS);
                preparedStatement = connection.prepareStatement(insertTweet);
                preparedStatement.setString(1, String.valueOf(status.getId()));
                preparedStatement.setString(2, status.getText());
                preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
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

    public void updateTweetClusteredTime(Status status) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String insertTweet = "UPDATE tweets SET clusteredTime = ? WHERE tweetId = ?";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(insertTweet);
                preparedStatement.setTimestamp(1, new Timestamp(new Date().getTime()));
                preparedStatement.setString(2, String.valueOf(status.getId()));
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
