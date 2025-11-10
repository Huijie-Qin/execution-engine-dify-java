package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.Execution;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for workflow executions.
 */
@Mapper
public interface ExecutionMapper {

    /**
     * Inserts execution row.
     *
     * @param execution entity to insert
     * @return rows affected
     */
    int insert(Execution execution);

    /**
     * Updates execution status and context.
     *
     * @param execution entity
     * @return rows affected
     */
    int update(Execution execution);

    /**
     * Finds by identifier.
     *
     * @param id identifier
     * @return optional execution
     */
    Optional<Execution> findById(@Param("id") Long id);
}
