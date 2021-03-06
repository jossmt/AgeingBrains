package com.brain.core.model;

import com.brain.core.rest.model.InitialParameters;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BipartiteGraph {

    // Maintain a copy of the original edges to iterate learning
    private SetUniqueList<Edge> edgesFixed;
    private SetUniqueList<Edge> edges;

    public BipartiteGraph(SetUniqueList<Edge> edges) {
        this.edges = edges;
        this.setFixedEdgeWeights();
    }

    public SetUniqueList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(SetUniqueList<Edge> edges) {
        this.edges = edges;
    }

    public SetUniqueList<Edge> getEdgesFixed() {
        return edgesFixed;
    }

    public void setEdgesFixed(SetUniqueList<Edge> edgesFixed) {
        this.edgesFixed = edgesFixed;
    }

    public void clear() {
        edges.clear();
    }

    public boolean inputPatternActivatesNoOutputNodes(SetUniqueList<Integer> inputPattern, InitialParameters initialParameters) {
        return outputNodesActivated(inputPattern, initialParameters).size() == 0;
    }

    public Set<OutputNode> inputPatternExceedsThreshold(SetUniqueList<Integer> inputPattern, InitialParameters initialParameters) {

        return outputNodesActivated(inputPattern, initialParameters);
    }

    public boolean outputNodeExceedsThreshold(OutputNode outputNode, SetUniqueList<Integer> inputPattern, InitialParameters ip) {
        SetUniqueList<Edge> outputInputEdges = SetUniqueList.setUniqueList(edges.stream()
                .filter(e -> e.getOutputNode().getId() == outputNode.getId())
                .filter(e -> inputPattern.contains(e.getInputNode().getId()))
                .collect(Collectors.toList()));
        double inboundWeightSum = sumWeights(outputInputEdges);
        return inboundWeightSum >= ip.getWeightThreshold();
    }

    private Set<OutputNode> outputNodesActivated(SetUniqueList<Integer> inputPattern, InitialParameters initialParameters) {
        Set<OutputNode> outputNodes = edges.stream().map(Edge::getOutputNode).collect(Collectors.toSet());

        Set<OutputNode> outputNodesActivated = new HashSet<>();
        for (OutputNode outputNode : outputNodes) {
            if (outputNodeExceedsThreshold(outputNode, inputPattern, initialParameters)) {
                outputNodesActivated.add(outputNode);
            }
        }
        return outputNodesActivated;
    }

    private double sumWeights(SetUniqueList<Edge> inputEdges) {
        return inputEdges.stream().map(Edge::getWeight)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public void resetEdgeWeights() {
        List<Edge> edges = this.edgesFixed.stream()
                .map(e -> new Edge(e.getWeight(), e.getInputNode(), e.getOutputNode()))
                .collect(Collectors.toList());
        this.edges = SetUniqueList.setUniqueList(edges);
    }

    public void setFixedEdgeWeights() {
        List<Edge> edges = this.edges.stream()
                .map(e -> new Edge(e.getWeight(), e.getInputNode(), e.getOutputNode()))
                .collect(Collectors.toList());
        this.edgesFixed = SetUniqueList.setUniqueList(edges);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BipartiteGraph{");
        sb.append("inputNodes=").append(edges.stream().map(Edge::getInputNode).collect(Collectors.toList()));
        sb.append(", outputNodes=").append(edges.stream().map(Edge::getOutputNode).collect(Collectors.toList()));
        sb.append('}');
        return sb.toString();
    }
}
