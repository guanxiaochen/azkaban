package azkaban.executor;

import azkaban.flow.ConditionOnJobStatus;
import azkaban.utils.Props;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HistoryNode {
    private final String id;
    private final String type;
    private final Status status;
    private final long startTime;
    private final long endTime;
    private final long updateTime;
    // Path to Job File
    private final String jobSource;
    // Path to top level props file
    private final String propsSource;
    private final Set<String> inNodes;
    private final Set<String> outNodes;
    private final Map<String, String> inputProps;
    private final Map<String, String> outputProps;
    private final Map<String, String> rampProps;
    private final long delayExecution;
    private final String condition;
    private final ConditionOnJobStatus conditionOnJobStatus;;

    public HistoryNode(ExecutableNode flow) {
        this.id = flow.getId();
        this.type = flow.getType();
        this.status = flow.getStatus();
        this.startTime = flow.getStartTime();
        this.endTime = flow.getEndTime();
        this.updateTime = flow.getUpdateTime();
        this.jobSource = flow.getJobSource();
        this.propsSource = flow.getPropsSource();
        this.inNodes = flow.getInNodes();
        this.outNodes = flow.getOutNodes();
        Props inputProps = flow.getInputProps();
        this.inputProps = inputProps == null ? Collections.emptyMap() : inputProps.getFlattened();
        Props outputProps = flow.getOutputProps();
        this.outputProps = outputProps == null ? Collections.emptyMap() : outputProps.getFlattened();
        Props rampProps = flow.getRampProps();
        this.rampProps = rampProps == null ? Collections.emptyMap() : rampProps.getFlattened();
        this.delayExecution = flow.getDelayedExecution();
        this.condition = flow.getCondition();
        this.conditionOnJobStatus = flow.getConditionOnJobStatus();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public String getJobSource() {
        return jobSource;
    }

    public String getPropsSource() {
        return propsSource;
    }

    public Set<String> getInNodes() {
        return inNodes;
    }

    public Set<String> getOutNodes() {
        return outNodes;
    }

    public Map<String, String> getInputProps() {
        return inputProps;
    }

    public Map<String, String> getOutputProps() {
        return outputProps;
    }

    public Map<String, String> getRampProps() {
        return rampProps;
    }

    public long getDelayExecution() {
        return delayExecution;
    }

    public String getCondition() {
        return condition;
    }

    public ConditionOnJobStatus getConditionOnJobStatus() {
        return conditionOnJobStatus;
    }
}
