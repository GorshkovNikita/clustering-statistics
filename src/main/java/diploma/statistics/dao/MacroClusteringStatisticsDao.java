package diploma.statistics.dao;

import diploma.statistics.GeneralStatistics;
import diploma.statistics.MacroClusteringStatistics;
import diploma.statistics.TimeAndProcessedPerUnit;

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
                " absorbedClusters, timeFactor, totalProcessedPerTimeUnit, mostRelevantTweetId, totalProcessedTweets) VALUES (?,?,?,?,?,?,?,?)";
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
                preparedStatement.setInt(8, statistics.getTotalProcessedTweets());

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
                String insertTopTerm = "INSERT INTO topterms (statisticId, term, numberOfOccurrences) VALUES (?,?,?)";
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

    public Map<Integer, MacroClusteringStatistics> getMacroClusteringStatistics() {
        Connection connection = getConnection();
        Map<Integer, MacroClusteringStatistics> macroClusteringStatisticsMap = new HashMap<>();
        Statement stmt = null;
        try {
            stmt =  connection.createStatement();
            String query = "SELECT id, clusterId, numberOfDocuments, timestamp, totalProcessedPerTimeUnit, mostRelevantTweetId, totalProcessedTweets " +
                    "FROM statistics ORDER BY timeFactor DESC";
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                MacroClusteringStatistics macroClusteringStatistics = new MacroClusteringStatistics();
//                macroClusteringStatistics.setTopTerms(getTopTerms(resultSet.getInt(1)));
                macroClusteringStatistics.setId(resultSet.getInt(1));
                macroClusteringStatistics.setClusterId(resultSet.getInt(2));
                macroClusteringStatistics.setNumberOfDocuments(resultSet.getInt(3));
                macroClusteringStatistics.setTimestamp(resultSet.getTimestamp(4));
                macroClusteringStatistics.setTotalProcessedPerTimeUnit(resultSet.getInt(5));
                macroClusteringStatistics.setMostRelevantTweetId(resultSet.getString(6));
                macroClusteringStatistics.setTotalProcessedTweets(resultSet.getInt(7));
                if (!macroClusteringStatisticsMap.containsKey(macroClusteringStatistics.getClusterId()))
                    macroClusteringStatisticsMap.put(macroClusteringStatistics.getClusterId(), macroClusteringStatistics);
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

    public MacroClusteringStatistics getMacroClusteringStatistics(int id) {
        Connection connection = getConnection();
        MacroClusteringStatistics macroClusteringStatistics = new MacroClusteringStatistics();
        PreparedStatement stmt = null;
        try {
            String query = "SELECT id, clusterId, numberOfDocuments, timestamp, totalProcessedPerTimeUnit, mostRelevantTweetId, totalProcessedTweets FROM statistics WHERE id = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                macroClusteringStatistics.setId(resultSet.getInt(1));
                macroClusteringStatistics.setClusterId(resultSet.getInt(2));
                macroClusteringStatistics.setNumberOfDocuments(resultSet.getInt(3));
                macroClusteringStatistics.setTimestamp(resultSet.getTimestamp(4));
                macroClusteringStatistics.setTotalProcessedPerTimeUnit(resultSet.getInt(5));
                macroClusteringStatistics.setMostRelevantTweetId(resultSet.getString(6));
                macroClusteringStatistics.setTotalProcessedTweets(resultSet.getInt(7));
            }
            macroClusteringStatistics.setTopTerms(getTopTerms(id));
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
        return macroClusteringStatistics;
    }


    public Map<String, Integer> getTopTerms(int statisticId) {
        Connection connection = getConnection();
        Map<String, Integer> topTerms = new HashMap<>();
        PreparedStatement stmt = null;
        try {
            String query = "SELECT term, numberOfOccurrences FROM topTerms where statisticId = ?";
            stmt =  connection.prepareStatement(query);
            stmt.setInt(1, statisticId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
                topTerms.put(resultSet.getString(1), resultSet.getInt(2));
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
        return topTerms;
    }

    public List<TimeAndProcessedPerUnit> getTimeAndProcessedPerUnitList(int clusterId) {
        Connection connection = getConnection();
        List<TimeAndProcessedPerUnit> timeAndProcessedPerUnitList = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            String query = "SELECT timestamp, totalProcessedPerTimeUnit FROM statistics where clusterId = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, clusterId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                TimeAndProcessedPerUnit timeAndProcessedPerUnit = new TimeAndProcessedPerUnit();
                timeAndProcessedPerUnit.setTimestamp(resultSet.getTimestamp(1));
                timeAndProcessedPerUnit.setTotalProcessedPerTimeUnit(resultSet.getInt(2));
                timeAndProcessedPerUnitList.add(timeAndProcessedPerUnit);
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
        return timeAndProcessedPerUnitList;
    }

    public GeneralStatistics getGeneralStatistics() {
        Connection connection = getConnection();
        GeneralStatistics generalStatistics = new GeneralStatistics();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String query = "SELECT timestamp, totalProcessedTweets FROM statistics ORDER BY timeFactor DESC";
            ResultSet resultSet = stmt.executeQuery(query);
            resultSet.next();
            generalStatistics.setEndTime(resultSet.getTimestamp(1));
            generalStatistics.setNumberOfTweets(resultSet.getInt(2));
            query = "SELECT timestamp FROM statistics WHERE clusterId = 0";
            ResultSet resultSet2 = stmt.executeQuery(query);
            resultSet2.next();
            generalStatistics.setStartTime(resultSet2.getTimestamp(1));
            generalStatistics.setDuration(generalStatistics.getEndTime().getTime() - generalStatistics.getStartTime().getTime());
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
        return generalStatistics;
    }
}
