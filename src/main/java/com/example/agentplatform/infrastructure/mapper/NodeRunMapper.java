package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.NodeRun;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for node run records.
 */
@Mapper
public interface NodeRunMapper {

    /**
     * Inserts node run record.
     *
     * @param nodeRun entity to insert
     * @return rows affected
     */
    int insert(NodeRun nodeRun);

    /**
     * Updates node run record.
     *
     * @param nodeRun entity containing new status/logs
     * @return rows affected
     */
    int update(NodeRun nodeRun);

    /**
     * Lists runs by execution.
     *
     * @param executionId execution identifier
     * @return list of node runs
     */
    List<NodeRun> findByExecutionId(@Param("executionId") Long executionId);
}
