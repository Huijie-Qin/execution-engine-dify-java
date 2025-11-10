package com.example.agentplatform.domain.execution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.agentplatform.domain.model.EdgeDefinition;
import com.example.agentplatform.domain.model.NodeDefinition;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests graph construction invariants.
 */
class WorkflowGraphTest {

    @Test
    void shouldBuildGraph() {
        final NodeDefinition start = new NodeDefinition();
        start.setId(1L);
        final NodeDefinition next = new NodeDefinition();
        next.setId(2L);
        final EdgeDefinition edge = new EdgeDefinition();
        edge.setFromNodeId(1L);
        edge.setToNodeId(2L);
        final WorkflowGraph graph = WorkflowGraph.build(List.of(start, next), List.of(edge));
        assertNotNull(graph.getStartNode());
        assertEquals(2L, graph.nextNodes(1L).getFirst().getId());
    }
}
