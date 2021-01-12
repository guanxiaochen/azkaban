package azkaban.executor;

import azkaban.flow.ConditionOnJobStatus;
import azkaban.utils.Props;

import java.util.Set;

public class HistoryFlow {
    private final int executionId;
    private final int scheduleId;
    private final int projectId;
    private final String projectName;
    private final String flowId;
    private final String lastModifiedUser;
    private final int version;
    private final long submitTime;
    private final long lastModifiedTimestamp;
    private final String submitUser;
    private final String executionPath;
    private final double azkabanFlowVersion;
    private final boolean isLocked;
    private final String flowLockErrorMessage;



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
    private final Props inputProps;
    private final Props outputProps;
    private final Props rampProps;
    private final long delayExecution;
    private final String condition;
    private final ConditionOnJobStatus conditionOnJobStatus;

    public HistoryFlow(ExecutableFlow flow) {
        this.executionId = flow.getExecutionId();
        this.scheduleId = flow.getScheduleId();
        this.projectId = flow.getProjectId();
        this.projectName = flow.getProjectName();
        this.flowId = flow.getFlowId();
        this.lastModifiedUser = flow.getLastModifiedByUser();
        this.version = flow.getVersion();
        this.submitTime = flow.getSubmitTime();
        this.lastModifiedTimestamp = flow.getLastModifiedTimestamp();
        this.submitUser = flow.getSubmitUser();
        this.executionPath = flow.getExecutionPath();
        this.azkabanFlowVersion = flow.getAzkabanFlowVersion();
        this.isLocked = flow.isLocked();
        this.flowLockErrorMessage = flow.getFlowLockErrorMessage();
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
        this.inputProps = flow.getInputProps();
        this.outputProps = flow.getOutputProps();
        this.rampProps = flow.getRampProps();
        this.delayExecution = flow.getDelayedExecution();
        this.condition = flow.getCondition();
        this.conditionOnJobStatus = flow.getConditionOnJobStatus();
    }

    public int getExecutionId() {
        return executionId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getFlowId() {
        return flowId;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public int getVersion() {
        return version;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public long getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public String getSubmitUser() {
        return submitUser;
    }

    public String getExecutionPath() {
        return executionPath;
    }

    public double getAzkabanFlowVersion() {
        return azkabanFlowVersion;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public String getFlowLockErrorMessage() {
        return flowLockErrorMessage;
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

    public Props getInputProps() {
        return inputProps;
    }

    public Props getOutputProps() {
        return outputProps;
    }

    public Props getRampProps() {
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
