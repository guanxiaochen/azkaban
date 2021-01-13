package azkaban.executor;

import java.util.List;
import java.util.stream.Collectors;

public class HistoryFlow extends HistoryNode{
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

    private final List<HistoryNode> nodes;

    public HistoryFlow(ExecutableFlow flow) {
        super(flow);
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
        this.nodes = flow.getExecutableNodes().stream().map(HistoryNode::new).collect(Collectors.toList());
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

    public List<HistoryNode> getNodes() {
        return nodes;
    }
}
