CREATE TABLE IF NOT EXISTS workflow (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(32) NOT NULL,
    description TEXT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS workflow_version (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    workflow_id BIGINT NOT NULL,
    graph_json JSON NOT NULL,
    variables_json JSON NOT NULL,
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (workflow_id) REFERENCES workflow(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS node (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    workflow_version_id BIGINT NOT NULL,
    type VARCHAR(128) NOT NULL,
    config_json JSON NOT NULL,
    in_schema JSON NULL,
    out_schema JSON NULL,
    position_x INT NULL,
    position_y INT NULL,
    FOREIGN KEY (workflow_version_id) REFERENCES workflow_version(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS edge (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    workflow_version_id BIGINT NOT NULL,
    from_node_id BIGINT NOT NULL,
    to_node_id BIGINT NOT NULL,
    condition_json JSON NULL,
    FOREIGN KEY (workflow_version_id) REFERENCES workflow_version(id),
    FOREIGN KEY (from_node_id) REFERENCES node(id),
    FOREIGN KEY (to_node_id) REFERENCES node(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS execution (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    workflow_version_id BIGINT NOT NULL,
    status VARCHAR(32) NOT NULL,
    started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ended_at TIMESTAMP NULL,
    context_json JSON NOT NULL,
    FOREIGN KEY (workflow_version_id) REFERENCES workflow_version(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS node_run (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    execution_id BIGINT NOT NULL,
    node_id BIGINT NOT NULL,
    status VARCHAR(32) NOT NULL,
    logs TEXT NULL,
    output_json JSON NULL,
    started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ended_at TIMESTAMP NULL,
    FOREIGN KEY (execution_id) REFERENCES execution(id),
    FOREIGN KEY (node_id) REFERENCES node(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS conversation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    workflow_id BIGINT NULL,
    workflow_version_id BIGINT NULL,
    memory_policy VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (workflow_id) REFERENCES workflow(id),
    FOREIGN KEY (workflow_version_id) REFERENCES workflow_version(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    conversation_id BIGINT NOT NULL,
    role VARCHAR(32) NOT NULL,
    content TEXT NOT NULL,
    tokens INT NULL,
    trace_id VARCHAR(64) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (conversation_id) REFERENCES conversation(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS model_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    provider VARCHAR(128) NOT NULL,
    model_name VARCHAR(128) NOT NULL,
    base_url VARCHAR(255) NOT NULL,
    api_key_alias VARCHAR(128) NOT NULL,
    timeout_ms INT NOT NULL,
    max_tokens INT NULL,
    extra_json JSON NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX uq_model_provider_name ON model_config(provider, model_name);

CREATE TABLE IF NOT EXISTS plugin_registry (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    version VARCHAR(64) NOT NULL,
    type VARCHAR(64) NOT NULL,
    enabled TINYINT(1) NOT NULL,
    manifest_json JSON NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
