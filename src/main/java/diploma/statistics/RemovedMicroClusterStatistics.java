package diploma.statistics;

/**
 * @author Никита
 */
public class RemovedMicroClusterStatistics {
    private byte isPotential;
    private int numberOfDocuments;
    private long creationTime;
    private long lastUpdateTIme;
    private String topWords;

    public byte getIsPotential() {
        return isPotential;
    }

    public void setIsPotential(byte isPotential) {
        this.isPotential = isPotential;
    }

    public int getNumberOfDocuments() {
        return numberOfDocuments;
    }

    public void setNumberOfDocuments(int numberOfDocuments) {
        this.numberOfDocuments = numberOfDocuments;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTIme;
    }

    public void setLastUpdateTIme(long lastUpdateTIme) {
        this.lastUpdateTIme = lastUpdateTIme;
    }

    public String getTopWords() {
        return topWords;
    }

    public void setTopWords(String topWords) {
        this.topWords = topWords;
    }
}
