package diploma.statistics;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author Никита
 */
public class MacroClusteringStatistics implements Serializable {
    private int id;
    private Timestamp timestamp;
    private int timeFactor;
    private int clusterId;
    private Map<String, Integer> topTerms;
    private int numberOfDocuments;
    private List<Integer> absorbedClusterIds;
    private int totalProcessedPerTimeUnit;
    private String mostRelevantTweetId;
    private int totalProcessedTweets;
    private double rate;
    private int numberOfPotentialMicroClusters;
    private int numberOfFiltered;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getTimeFactor() {
        return timeFactor;
    }

    public void setTimeFactor(int timeFactor) {
        this.timeFactor = timeFactor;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public int getNumberOfDocuments() {
        return numberOfDocuments;
    }

    public void setNumberOfDocuments(int numberOfDocuments) {
        this.numberOfDocuments = numberOfDocuments;
    }

    public Map<String, Integer> getTopTerms() {
        return topTerms;
    }

    public void setTopTerms(Map<String, Integer> topTerms) {
        this.topTerms = topTerms;
    }

    public List<Integer> getAbsorbedClusterIds() {
        return absorbedClusterIds;
    }

    public void setAbsorbedClusterIds(List<Integer> absorbedClusterIds) {
        this.absorbedClusterIds = absorbedClusterIds;
    }

    public int getTotalProcessedPerTimeUnit() {
        return totalProcessedPerTimeUnit;
    }

    public void setTotalProcessedPerTimeUnit(int totalProcessedPerTimeUnit) {
        this.totalProcessedPerTimeUnit = totalProcessedPerTimeUnit;
    }

    public String getMostRelevantTweetId() {
        return mostRelevantTweetId;
    }

    public void setMostRelevantTweetId(String mostRelevantTweetId) {
        this.mostRelevantTweetId = mostRelevantTweetId;
    }

    public int getTotalProcessedTweets() {
        return totalProcessedTweets;
    }

    public void setTotalProcessedTweets(int totalProcessedTweets) {
        this.totalProcessedTweets = totalProcessedTweets;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getNumberOfPotentialMicroClusters() {
        return numberOfPotentialMicroClusters;
    }

    public void setNumberOfPotentialMicroClusters(int numberOfPotentialMicroClusters) {
        this.numberOfPotentialMicroClusters = numberOfPotentialMicroClusters;
    }

    public int getNumberOfFiltered() {
        return numberOfFiltered;
    }

    public void setNumberOfFiltered(int numberOfFiltered) {
        this.numberOfFiltered = numberOfFiltered;
    }
}
