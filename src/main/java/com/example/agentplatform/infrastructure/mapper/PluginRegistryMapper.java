package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.PluginRegistryEntry;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for plugin registry records.
 */
@Mapper
public interface PluginRegistryMapper {

    /**
     * Inserts plugin record.
     *
     * @param entry entity
     * @return rows affected
     */
    int insert(PluginRegistryEntry entry);

    /**
     * Updates plugin entry.
     *
     * @param entry entity
     * @return rows affected
     */
    int update(PluginRegistryEntry entry);

    /**
     * Lists entries.
     *
     * @return list of plugins
     */
    List<PluginRegistryEntry> findAll();

    /**
     * Finds entry by name and version.
     *
     * @param name plugin name
     * @param version plugin version
     * @return optional entry
     */
    Optional<PluginRegistryEntry> findByNameAndVersion(@Param("name") String name, @Param("version") String version);

    /**
     * Finds by id.
     *
     * @param id identifier
     * @return optional entry
     */
    Optional<PluginRegistryEntry> findById(@Param("id") Long id);
}
