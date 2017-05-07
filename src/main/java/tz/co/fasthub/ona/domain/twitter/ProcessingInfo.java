package tz.co.fasthub.ona.domain.twitter;

/**
 * Created by daniel on 5/7/17.
 */
public class ProcessingInfo {
    private String state;
    private String check_after_secs;


    public ProcessingInfo() {
    }


    @Override
    public String toString() {
        return "ProcessingInfo{" +
                "state='" + state + '\'' +
                ", check_after_secs='" + check_after_secs + '\'' +
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
}
