package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.NodeDefinition;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for workflow node definitions.
 */
@Mapper
public interface NodeDefinitionMapper {

    /**
     * Inserts a node definition.
     *
     * @param node node entity
     * @return rows affected
     */
    int insert(NodeDefinition node);

    /**
     * Lists nodes by workflow version.
     *
     * @param workflowVersionId workflow version identifier
     * @return node list
     */
    List<NodeDefinition> findByWorkflowVersionId(@Param("workflowVersionId") Long workflowVersionId);
}
