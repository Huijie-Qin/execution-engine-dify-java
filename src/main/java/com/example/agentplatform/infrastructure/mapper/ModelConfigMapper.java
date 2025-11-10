package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.ModelConfig;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for LLM model configurations.
 */
@Mapper
public interface ModelConfigMapper {

    /**
     * Inserts model config.
     *
     * @param config entity
     * @return rows affected
     */
    int insert(ModelConfig config);

    /**
     * Updates model config.
     *
     * @param config entity
     * @return rows affected
     */
    int update(ModelConfig config);

    /**
     * Deletes config.
     *
     * @param id identifier
     * @return rows affected
     */
    int delete(@Param("id") Long id);

    /**
     * Lists configs.
     *
     * @return list of configs
     */
    List<ModelConfig> findAll();

    /**
     * Finds by id.
     *
     * @param id identifier
     * @return optional config
     */
    Optional<ModelConfig> findById(@Param("id") Long id);
}
