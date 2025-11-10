package com.example.agentplatform.infrastructure.mapper;

import com.example.agentplatform.domain.model.Message;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper for conversation messages.
 */
@Mapper
public interface MessageMapper {

    /**
     * Inserts a message.
     *
     * @param message entity
     * @return rows affected
     */
    int insert(Message message);

    /**
     * Lists messages by conversation.
     *
     * @param conversationId conversation identifier
     * @return list of messages
     */
    List<Message> findByConversationId(@Param("conversationId") Long conversationId);
}
