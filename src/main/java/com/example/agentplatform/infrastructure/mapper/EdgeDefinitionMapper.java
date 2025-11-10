package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.EdgeDefinition;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for workflow edges.
 */
@Mapper
public interface EdgeDefinitionMapper {

    /**
     * Inserts an edge definition.
     *
     * @param edge edge entity
     * @return rows affected
     */
    int insert(EdgeDefinition edge);

    /**
     * Lists edges by workflow version.
     *
     * @param workflowVersionId workflow version identifier
     * @return edge list
     */
    List<EdgeDefinition> findByWorkflowVersionId(@Param("workflowVersionId") Long workflowVersionId);
}
