package diploma.statistics;

import java.sql.Timestamp;

/**
 * @author Никита
 */
public class TimeAndProcessedPerUnit {
    private Timestamp timestamp;
    private int totalProcessedPerTimeUnit;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotalProcessedPerTimeUnit() {
        return totalProcessedPerTimeUnit;
    }

    public void setTotalProcessedPerTimeUnit(int totalProcessedPerTimeUnit) {
        this.totalProcessedPerTimeUnit = totalProcessedPerTimeUnit;
    }
}
