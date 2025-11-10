package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.Conversation;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for conversations.
 */
@Mapper
public interface ConversationMapper {

    /**
     * Inserts conversation row.
     *
     * @param conversation entity
     * @return rows affected
     */
    int insert(Conversation conversation);

    /**
     * Finds conversation by id.
     *
     * @param id identifier
     * @return optional conversation
     */
    Optional<Conversation> findById(@Param("id") Long id);
}
