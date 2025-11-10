package com.example.agentplatform.interfaces.api;

import com.example.agentplatform.application.service.ModelConfigService;
import com.example.agentplatform.domain.model.ModelConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin controller managing LLM model configurations.
 */
@RestController
@RequestMapping("/admin/llm/configs")
@RequiredArgsConstructor
@Tag(name = "LLM Config")
public class ModelConfigController {

    private final ModelConfigService modelConfigService;

    /**
     * Creates model configuration.
     *
     * @param request request payload
     * @return persisted config
     */
    @PostMapping
    @Operation(summary = "Create model config")
    public ModelConfig create(@Validated @RequestBody final ModelConfig request) {
        return modelConfigService.create(request);
    }

    /**
     * Updates config.
     *
     * @param id identifier
     * @param request request payload
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update model config")
    public void update(@PathVariable final Long id, @Validated @RequestBody final ModelConfig request) {
        request.setId(id);
        modelConfigService.update(request);
    }

    /**
     * Lists configs.
     *
     * @return config list
     */
    @GetMapping
    @Operation(summary = "List model configs")
    public List<ModelConfig> list() {
        return modelConfigService.list();
    }

    /**
     * Deletes config.
     *
     * @param id identifier
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete model config")
    public void delete(@PathVariable final Long id) {
        modelConfigService.delete(id);
    }
}
