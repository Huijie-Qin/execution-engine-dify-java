package com.example.agentplatform.application.service;

import com.example.agentplatform.domain.model.ModelConfig;
import com.example.agentplatform.infrastructure.mapper.ModelConfigMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service exposing CRUD operations for {@link ModelConfig} records.
 */
@Service
@RequiredArgsConstructor
public class ModelConfigService {

    private final ModelConfigMapper modelConfigMapper;

    /**
     * Creates configuration.
     *
     * @param config config to create
     * @return persisted config
     */
    @Transactional(timeout = 30)
    public ModelConfig create(final ModelConfig config) {
        modelConfigMapper.insert(config);
        return config;
    }

    /**
     * Updates configuration.
     *
     * @param config config entity
     */
    @Transactional(timeout = 30)
    public void update(final ModelConfig config) {
        modelConfigMapper.update(config);
    }

    /**
     * Deletes configuration.
     *
     * @param id identifier
     */
    @Transactional(timeout = 30)
    public void delete(final Long id) {
        modelConfigMapper.delete(id);
    }

    /**
     * Lists configurations.
     *
     * @return list of configs
     */
    @Transactional(readOnly = true)
    public List<ModelConfig> list() {
        return modelConfigMapper.findAll();
    }

    /**
     * Finds by identifier.
     *
     * @param id identifier
     * @return optional config
     */
    @Transactional(readOnly = true)
    public Optional<ModelConfig> findById(final Long id) {
        return modelConfigMapper.findById(id);
    }
}
