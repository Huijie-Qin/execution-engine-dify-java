package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.Workflow;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for CRUD operations on the {@link Workflow} table.
 */
@Mapper
public interface WorkflowMapper {

    /**
     * Inserts a new workflow row.
     *
     * @param workflow workflow entity to insert
     * @return number of rows affected
     */
    int insert(Workflow workflow);

    /**
     * Updates the workflow row by identifier.
     *
     * @param workflow workflow entity containing changes
     * @return number of rows affected
     */
    int update(Workflow workflow);

    /**
     * Retrieves a workflow by identifier.
     *
     * @param id primary key
     * @return optional workflow
     */
    Optional<Workflow> findById(@Param("id") Long id);

    /**
     * Lists all workflows.
     *
     * @return list of workflows
     */
    List<Workflow> findAll();
}
