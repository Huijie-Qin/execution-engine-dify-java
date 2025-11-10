package com.example.agentplatform.domain.execution;

import com.example.agentplatform.domain.model.EdgeDefinition;
import com.example.agentplatform.domain.model.NodeDefinition;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

/**
 * In-memory representation of a workflow DAG built from node and edge definitions.
 */
public final class WorkflowGraph {

    private final Map<Long, NodeDefinition> nodes;
    private final Map<Long, List<NodeDefinition>> adjacency;
    private final NodeDefinition startNode;

    private WorkflowGraph(
            final Map<Long, NodeDefinition> nodes,
            final Map<Long, List<NodeDefinition>> adjacency,
            final NodeDefinition startNode) {
        this.nodes = nodes;
        this.adjacency = adjacency;
        this.startNode = startNode;
    }

    /**
     * Builds a graph from nodes and edges.
     *
     * @param nodes node definitions
     * @param edges edge definitions
     * @return workflow graph
     */
    public static WorkflowGraph build(
            final Collection<NodeDefinition> nodes, final Collection<EdgeDefinition> edges) {
        final Map<Long, NodeDefinition> nodeIndex = new HashMap<>();
        for (final NodeDefinition node : nodes) {
            nodeIndex.put(node.getId(), node);
        }
        final Map<Long, List<NodeDefinition>> adjacency = new HashMap<>();
        final Set<Long> targetNodes = new HashSet<>();
        for (final EdgeDefinition edge : edges) {
            final NodeDefinition from = nodeIndex.get(edge.getFromNodeId());
            final NodeDefinition to = nodeIndex.get(edge.getToNodeId());
            if (from == null || to == null) {
                throw new IllegalArgumentException("Edge references unknown node");
            }
            adjacency.computeIfAbsent(from.getId(), ignored -> new ArrayList<>()).add(to);
            targetNodes.add(to.getId());
        }
        final Optional<NodeDefinition> maybeStart = nodes.stream()
                .filter(node -> !targetNodes.contains(node.getId()))
                .findFirst();
        final NodeDefinition startNode = maybeStart.orElseThrow(() -> new IllegalArgumentException("No start node"));
        validateAcyclic(startNode, adjacency, nodes.size());
        return new WorkflowGraph(nodeIndex, adjacency, startNode);
    }

    private static void validateAcyclic(
            final NodeDefinition startNode, final Map<Long, List<NodeDefinition>> adjacency, final int expectedSize) {
        final Set<Long> visited = new HashSet<>();
        final Queue<NodeDefinition> queue = new ArrayDeque<>();
        queue.add(startNode);
        while (!queue.isEmpty()) {
            final NodeDefinition node = queue.poll();
            if (!visited.add(node.getId())) {
                continue;
            }
            for (final NodeDefinition next : adjacency.getOrDefault(node.getId(), List.of())) {
                if (visited.contains(next.getId())) {
                    throw new IllegalStateException("Cycle detected in workflow graph");
                }
                queue.add(next);
            }
        }
        if (visited.size() != expectedSize) {
            throw new IllegalStateException("Workflow graph is disconnected");
        }
    }

    /**
     * @return start node for traversal
     */
    public NodeDefinition getStartNode() {
        return startNode;
    }

    /**
     * Resolves outgoing nodes from given node.
     *
     * @param nodeId node identifier
     * @return adjacent nodes
     */
    public List<NodeDefinition> nextNodes(final Long nodeId) {
        return adjacency.getOrDefault(nodeId, List.of());
    }
}
