package com.brain.core.model;

import com.brain.core.rest.model.InitialParameters;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.List;
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
        return activeCount(inputPattern, initialParameters) == 0;
    }

    public Boolean inputPatternExceedsThreshold(SetUniqueList<Integer> inputPattern, InitialParameters initialParameters) {

        return activeCount(inputPattern, initialParameters) >= initialParameters.getActivatedNodeSize();
    }

    private int activeCount(SetUniqueList<Integer> inputPattern, InitialParameters initialParameters) {
        Set<OutputNode> outputNodes = edges.stream().map(Edge::getOutputNode).collect(Collectors.toSet());

        int activeCount = 0;
        for (OutputNode outputNode : outputNodes) {
            SetUniqueList<Edge> outputInputEdges = SetUniqueList.setUniqueList(edges.stream()
                    .filter(e -> e.getOutputNode().getId() == outputNode.getId())
                    .filter(e -> inputPattern.contains(e.getInputNode().getId()))
                    .collect(Collectors.toList()));
            double inboundWeightSum = sumWeights(outputInputEdges);
            if (inboundWeightSum >= initialParameters.getWeightThreshold()) {
                activeCount++;
            }
        }
        return activeCount;
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
