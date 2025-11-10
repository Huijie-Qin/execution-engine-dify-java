package com.example.agentplatform.infrastructure.llm;

import com.example.agentplatform.domain.llm.LlmProvider;
import com.example.agentplatform.domain.llm.LlmRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Basic HTTP client integration with the DeepSeek API compatible interface.
 */
@Component("deepseek")
@RequiredArgsConstructor
public class DeepSeekLlmProvider implements LlmProvider {

    private final WebClient.Builder webClientBuilder;

    @Override
    /** {@inheritDoc} */
    public String chat(final LlmRequest request) {
        return webClientBuilder
                .build()
                .post()
                .uri(request.model())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("messages", request.messages(), "max_tokens", request.maxTokens()))
                .retrieve()
                .bodyToMono(Map.class)
                .map(body -> body.getOrDefault("output", "").toString())
                .block();
    }

    @Override
    /** {@inheritDoc} */
    public Flux<String> streamChat(final LlmRequest request) {
        return Flux.just(chat(request));
    }
}
