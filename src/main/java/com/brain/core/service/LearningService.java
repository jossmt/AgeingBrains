package com.brain.core.service;

import com.brain.core.model.BipartiteGraph;
import com.brain.core.model.Edge;
import com.brain.core.model.OutputNode;
import com.brain.core.rest.model.InitialParameters;
import com.brain.core.rest.model.LearningParameters;
import com.brain.core.rest.model.NodeSelectionType;
import com.brain.core.rest.model.ResultsData;
import org.apache.commons.collections4.list.SetUniqueList;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LearningService {

    private Random random = new Random();

    public void learn(BipartiteGraph graph, SetUniqueList<Integer> ap, InitialParameters ip, ResultsData resultsData,
                      LearningParameters lp) {

        int steps = 0;
        Set<OutputNode> outputNodesActivated;
        while (!((outputNodesActivated = graph.inputPatternExceedsThreshold(ap, ip)).size() >= ip.getActivatedNodeSize())) {
            if (lp.isYoungLearning()) {
                youngLearning(graph, ap, ip, lp);
            } else {
                oldLearning(graph, ap, ip, lp);
            }
            steps++;
        }

        // Update the step count
        int count = resultsData.getLearningStepCount().getOrDefault(steps, 0) + 1;
        resultsData.getLearningStepCount().put(steps, count);

        // Add the set of output nodes.
        resultsData.addOutputNodeSet(outputNodesActivated);

        graph.resetEdgeWeights();
    }

    private void youngLearning(BipartiteGraph graph, SetUniqueList<Integer> ap, InitialParameters ip, LearningParameters lp) {
        SetUniqueList<Edge> outputEdgesForSelectedInputNode = getInputNodeOutboundEdges(graph, ap, lp);
        Edge selectedEdge = selectEdgeForInputNode(graph, ap, outputEdgesForSelectedInputNode, ip, lp.getFirstEdgeSelection());
        selectedEdge.setWeight(selectedEdge.getWeight() + ((1 - selectedEdge.getWeight()) / 2));
    }

    private void oldLearning(BipartiteGraph graph, SetUniqueList<Integer> ap, InitialParameters ip, LearningParameters lp) {
        int index = random.nextInt(ap.size());
        int index2 = random.nextInt(ap.size());
        while (index == index2) index2 = random.nextInt(ap.size());
        final int index2Final = index2;

        SetUniqueList<Edge> edges1 = getInputNodeOutboundEdges(graph, ap, lp);
        SetUniqueList<Integer> apExcludingFirstIndex = SetUniqueList.setUniqueList(ap.stream().filter(apidx -> apidx != edges1.get(0).getInputNode().getId())
                .collect(Collectors.toList()));
        SetUniqueList<Edge> edges2 = getInputNodeOutboundEdges(graph, apExcludingFirstIndex, lp);
        Edge randomEdgeFromRandomInput = selectEdgeForInputNode(graph, ap, edges1, ip, lp.getFirstEdgeSelection());
        Edge randomEdgeFromRandomInput2 = selectEdgeForInputNode(graph, ap, edges2, ip, lp.getSecondEdgeSelection());
        randomEdgeFromRandomInput.setWeight(randomEdgeFromRandomInput.getWeight() + randomEdgeFromRandomInput2.getWeight());
    }

    /**
     * TODO: Check if there's a better way of creating the graph instead to optimise ordering beforehand
     */
    private SetUniqueList<Edge> getInputNodeOutboundEdges(BipartiteGraph graph, SetUniqueList<Integer> ap, LearningParameters learningParameters) {

        switch (learningParameters.getInputNodeSelection()) {
            case RANDOM:
                int index = random.nextInt(ap.size());
                return SetUniqueList.setUniqueList(graph.getEdges().stream()
                        .filter(e -> e.getInputNode().getId() == ap.get(index))
                        .collect(Collectors.toList()));
            case HIGHEST:
                SetUniqueList<Edge> outboundEdgesForMaxInput = null;

                double currentMax = 0;
                for (int i = 0; i < ap.size(); i++) {
                    final int idx = i;
                    SetUniqueList<Edge> edges = SetUniqueList.setUniqueList(graph.getEdges().stream()
                            .filter(e -> e.getInputNode().getId() == ap.get(idx))
                            .collect(Collectors.toList()));
                    double sum = edges.stream().mapToDouble(Edge::getWeight).sum();
                    if (sum > currentMax) {
                        outboundEdgesForMaxInput = edges;
                        currentMax = sum;
                    }
                }
                return outboundEdgesForMaxInput;
            case LOWEST:
                SetUniqueList<Edge> outboundEdgesForMinInput = null;

                double currentMin = Double.MAX_VALUE;
                for (int i = 0; i < ap.size(); i++) {
                    final int idx = i;
                    SetUniqueList<Edge> edges = SetUniqueList.setUniqueList(graph.getEdges().stream()
                            .filter(e -> e.getInputNode().getId() == ap.get(idx))
                            .collect(Collectors.toList()));
                    double sum = edges.stream().mapToDouble(Edge::getWeight).sum();
                    if (sum < currentMin) {
                        outboundEdgesForMinInput = edges;
                        currentMin = sum;
                    }
                }
                return outboundEdgesForMinInput;
            default:
                throw new IllegalArgumentException("No learning parameter for inputNodeSelectionType chosen");
        }
    }

    private Edge selectEdgeForInputNode(BipartiteGraph graph, SetUniqueList<Integer> ap, SetUniqueList<Edge> edges,
                                        InitialParameters ip, NodeSelectionType nodeSelectionType) {

        SetUniqueList<Edge> edgesWithUnactivatedOutputNode = SetUniqueList.setUniqueList(edges.stream()
                .filter(e -> !graph.outputNodeExceedsThreshold(e.getOutputNode(), ap, ip))
                .collect(Collectors.toList()));
        switch (nodeSelectionType) {
            case RANDOM:
                return edgesWithUnactivatedOutputNode.get(random.nextInt(edgesWithUnactivatedOutputNode.size()));
            case HIGHEST:
                return edgesWithUnactivatedOutputNode.stream()
                        .max(Comparator.comparing(Edge::getWeight))
                        .orElseThrow(IllegalArgumentException::new);
            case LOWEST:
                return edgesWithUnactivatedOutputNode.stream()
                        .min(Comparator.comparing(Edge::getWeight))
                        .orElseThrow(IllegalArgumentException::new);
            default:
                throw new IllegalArgumentException("No edge selection type provided");
        }
    }
}
