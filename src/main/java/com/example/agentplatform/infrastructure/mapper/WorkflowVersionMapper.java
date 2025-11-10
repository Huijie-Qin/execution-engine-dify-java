package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.WorkflowVersion;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for persisting workflow versions.
 */
@Mapper
public interface WorkflowVersionMapper {

    /**
     * Inserts a workflow version.
     *
     * @param version entity to insert
     * @return rows affected
     */
    int insert(WorkflowVersion version);

    /**
     * Updates workflow version status.
     *
     * @param id identifier
     * @param status new status
     * @return rows affected
     */
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * Finds latest version by workflow id.
     *
     * @param workflowId workflow identifier
     * @return optional latest version
     */
    Optional<WorkflowVersion> findLatestByWorkflowId(@Param("workflowId") Long workflowId);

    /**
     * Finds by identifier.
     *
     * @param id identifier
     * @return optional version
     */
    Optional<WorkflowVersion> findById(@Param("id") Long id);

    /**
     * Lists versions for workflow.
     *
     * @param workflowId workflow identifier
     * @return list of versions
     */
    List<WorkflowVersion> findByWorkflowId(@Param("workflowId") Long workflowId);
}
