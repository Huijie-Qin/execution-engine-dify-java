package com.example.agentplatform.application.service;

import com.example.agentplatform.domain.model.Workflow;
import com.example.agentplatform.domain.model.WorkflowVersion;
import com.example.agentplatform.infrastructure.mapper.WorkflowMapper;
import com.example.agentplatform.infrastructure.mapper.WorkflowVersionMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service encapsulating workflow CRUD and publishing operations.
 */
@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final WorkflowMapper workflowMapper;
    private final WorkflowVersionMapper versionMapper;

    /**
     * Creates a workflow and its initial draft version.
     *
     * @param workflow workflow metadata
     * @param version version payload
     * @return created version
     */
    @Transactional(timeout = 30)
    public WorkflowVersion createWorkflow(final Workflow workflow, final WorkflowVersion version) {
        workflowMapper.insert(workflow);
        version.setWorkflowId(workflow.getId());
        versionMapper.insert(version);
        return version;
    }

    /**
     * Publishes a workflow version by marking its status as active.
     *
     * @param workflowId workflow identifier
     * @param versionId version identifier
     */
    @Transactional(timeout = 30)
    public void publish(final Long workflowId, final Long versionId) {
        versionMapper.findById(versionId)
                .filter(v -> v.getWorkflowId().equals(workflowId))
                .orElseThrow(() -> new IllegalArgumentException("Version not found for workflow"));
        versionMapper.updateStatus(versionId, "PUBLISHED");
        workflowMapper.findById(workflowId)
                .ifPresent(entity -> {
                    entity.setStatus("PUBLISHED");
                    workflowMapper.update(entity);
                });
    }

    /**
     * Retrieves a workflow by identifier.
     *
     * @param workflowId workflow identifier
     * @return optional workflow
     */
    @Transactional(readOnly = true)
    public Optional<Workflow> findById(final Long workflowId) {
        return workflowMapper.findById(workflowId);
    }

    /**
     * Lists all workflows.
     *
     * @return list of workflows
     */
    @Transactional(readOnly = true)
    public List<Workflow> listAll() {
        return workflowMapper.findAll();
    }
}
