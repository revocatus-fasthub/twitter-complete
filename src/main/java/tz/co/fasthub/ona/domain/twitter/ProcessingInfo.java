package tz.co.fasthub.ona.domain.twitter;

/**
 * Created by daniel on 5/7/17.
 */
public class ProcessingInfo {
    private String state;
    private String check_after_secs;
    private String progress_percent;


    public ProcessingInfo() {
    }

    public ProcessingInfo(String state, String check_after_secs, String progress_percent) {
        this.state = state;
        this.check_after_secs = check_after_secs;
        this.progress_percent = progress_percent;
    }

    @Override
    public String toString() {
        return "ProcessingInfo{" +
                "state='" + state + '\'' +
                ", check_after_secs='" + check_after_secs + '\'' +
                ", progress_percent='" + progress_percent + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCheck_after_secs() {
        return check_after_secs;
    }

    public void setCheck_after_secs(String check_after_secs) {
        this.check_after_secs = check_after_secs;
    }

    public String getProgress_percent() {
        return progress_percent;
    }

    public void setProgress_percent(String progress_percent) {
        this.progress_percent = progress_percent;
    }
}
