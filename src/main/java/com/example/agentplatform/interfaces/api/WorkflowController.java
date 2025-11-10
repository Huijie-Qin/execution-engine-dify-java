package com.example.agentplatform.interfaces.api;

import com.example.agentplatform.application.service.ExecutionService;
import com.example.agentplatform.application.service.WorkflowService;
import com.example.agentplatform.domain.model.Workflow;
import com.example.agentplatform.domain.model.WorkflowVersion;
import com.example.agentplatform.interfaces.api.dto.CreateWorkflowRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * REST controller exposing workflow management and execution APIs.
 */
@RestController
@RequestMapping("/workflows")
@RequiredArgsConstructor
@Tag(name = "Workflows")
public class WorkflowController {

    private final WorkflowService workflowService;
    private final ExecutionService executionService;

    /**
     * Creates a workflow and draft version.
     *
     * @param request request payload
     * @return created version
     */
    @PostMapping
    @Operation(summary = "Create workflow")
    public WorkflowVersion create(@Validated @RequestBody final CreateWorkflowRequest request) {
        final Workflow workflow = new Workflow();
        workflow.setName(request.name());
        workflow.setDescription(request.description());
        workflow.setStatus("DRAFT");
        final WorkflowVersion version = new WorkflowVersion();
        version.setGraphJson(request.graphJson());
        version.setVariablesJson(request.variablesJson());
        version.setStatus("DRAFT");
        return workflowService.createWorkflow(workflow, version);
    }

    /**
     * Lists workflows.
     *
     * @return list of workflows
     */
    @GetMapping
    @Operation(summary = "List workflows")
    public List<Workflow> list() {
        return workflowService.listAll();
    }

    /**
     * Publishes workflow version.
     *
     * @param workflowId workflow identifier
     * @param versionId version identifier
     */
    @PostMapping("/{workflowId}/publish/{versionId}")
    @Operation(summary = "Publish workflow version")
    public void publish(@PathVariable final Long workflowId, @PathVariable final Long versionId) {
        workflowService.publish(workflowId, versionId);
    }

    /**
     * Executes workflow version.
     *
     * @param versionId version identifier
     * @param input context input
     * @return async result
     */
    @PostMapping(value = "/{versionId}/execute", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Execute workflow version")
    public Mono<Map<String, Object>> execute(
            @PathVariable final Long versionId, @RequestBody final Map<String, Object> input) {
        return executionService.execute(versionId, input);
    }
}
