package diploma.statistics.dao;

import diploma.statistics.MacroClusteringStatistics;

import java.sql.*;
import java.util.*;

/**
 * @author Никита
 */
public class MacroClusteringStatisticsDao extends BaseDao {
    public void saveStatistics(MacroClusteringStatistics statistics) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String insertTweet = "INSERT INTO statistics (timestamp, clusterId, numberOfDocuments," +
                " absorbedClusters, timeFactor, totalProcessedPerTimeUnit, mostRelevantTweetId) VALUES (?,?,?,?,?,?,?)";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(insertTweet, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setTimestamp(1, statistics.getTimestamp());
                preparedStatement.setInt(2, statistics.getClusterId());
                preparedStatement.setInt(3, statistics.getNumberOfDocuments());
                preparedStatement.setString(4, statistics.getAbsorbedClusterIds().toString());
                preparedStatement.setInt(5, statistics.getTimeFactor());
                preparedStatement.setInt(6, statistics.getTotalProcessedPerTimeUnit());
                preparedStatement.setString(7, statistics.getMostRelevantTweetId());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0)
                    throw new SQLException("Statistics insertion fails, no rows affected.");

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next())
                        saveTopTerms(generatedKeys.getLong(1), statistics.getTopTerms());
                    else throw new SQLException("Statistics insertion fails, no id obtained");
                }

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

    private void saveTopTerms(long statisticId, Map<String, Integer> topTerms) {
        Connection connection = getConnection();
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            try {
                String insertTopTerm = "INSERT INTO topterms (statisticId, term, numberOfOccurences) VALUES (?,?,?)";
                preparedStatement = connection.prepareStatement(insertTopTerm);

                for (Map.Entry<String, Integer> term: topTerms.entrySet()) {
                    preparedStatement.clearParameters();
                    preparedStatement.setLong(1, statisticId);
                    preparedStatement.setString(2, term.getKey());
                    preparedStatement.setInt(3, term.getValue());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
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

    public Map<Integer, List<MacroClusteringStatistics>> getMacroClusteringStatistics() {
        Connection connection = getConnection();
        Map<Integer, List<MacroClusteringStatistics>> macroClusteringStatisticsMap = new HashMap<>();
        Statement stmt = null;
        try {
            stmt =  connection.createStatement();
            String query = "SELECT clusterId, numberOfDocuments, timestamp, totalProcessedPerTimeUnit, mostRelevantTweetId FROM statistics ";
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                MacroClusteringStatistics macroClusteringStatistics = new MacroClusteringStatistics();
                macroClusteringStatistics.setClusterId(resultSet.getInt(1));
                macroClusteringStatistics.setNumberOfDocuments(resultSet.getInt(2));
                macroClusteringStatistics.setTimestamp(resultSet.getTimestamp(3));
                macroClusteringStatistics.setTotalProcessedPerTimeUnit(resultSet.getInt(4));
                macroClusteringStatistics.setMostRelevantTweetId(resultSet.getString(5));
                if (macroClusteringStatisticsMap.containsKey(macroClusteringStatistics.getClusterId()))
                    macroClusteringStatisticsMap.get(macroClusteringStatistics.getClusterId()).add(macroClusteringStatistics);
                else macroClusteringStatisticsMap.put(macroClusteringStatistics.getClusterId(), new ArrayList<>(Arrays.asList(macroClusteringStatistics)));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (stmt != null)
                    stmt.close();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return macroClusteringStatisticsMap;
    }

    public static void main(String[] args) {
        MacroClusteringStatisticsDao dao = new MacroClusteringStatisticsDao();
        Map<Integer, List<MacroClusteringStatistics>> list = dao.getMacroClusteringStatistics();
        System.out.println(list);
    }
}
