package com.brain.core.service;

import com.brain.core.model.BipartiteGraph;
import com.brain.core.model.Edge;
import com.brain.core.model.OutputNode;
import com.brain.core.rest.model.InitialParameters;
import com.brain.core.rest.model.ResultsData;
import org.apache.commons.collections4.list.SetUniqueList;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LearningService {

    private Random random = new Random();

    public void learn(BipartiteGraph graph, SetUniqueList<Integer> ap, InitialParameters ip, ResultsData resultsData,
                      boolean isLtp) {

        int steps = 0;
        Set<OutputNode> outputNodesActivated;
        while (!((outputNodesActivated = graph.inputPatternExceedsThreshold(ap, ip)).size() >= ip.getActivatedNodeSize())) {
            if (isLtp) {
                ltplearning(graph, ap, ip);
            } else {
                misLearning(graph, ap, ip);
            }
            steps++;
        }

        // Update the step count
        int count = resultsData.getLearningStepCount().getOrDefault(steps, 0) + 1;
        resultsData.getLearningStepCount().put(steps, count);

        // Add the set of output nodes.
        resultsData.addOutputNodeSet(outputNodesActivated);

        // Reset edge weights for the next iteration (maybe a better way to do this, do we even need input/output nodes)
        graph.resetEdgeWeights();
    }

    private void ltplearning(BipartiteGraph graph, SetUniqueList<Integer> ap, InitialParameters ip) {
        int index = random.nextInt(ap.size());
        SetUniqueList<Edge> outputEdgesForRandomIndex = SetUniqueList.setUniqueList(graph.getEdges().stream()
                .filter(e -> e.getInputNode().getId() == ap.get(index))
                .collect(Collectors.toList()));
        Edge randomEdgeFromRandomInput = outputEdgesForRandomIndex.get(random.nextInt(outputEdgesForRandomIndex.size()));
        randomEdgeFromRandomInput.setWeight(randomEdgeFromRandomInput.getWeight() + ((1 - randomEdgeFromRandomInput.getWeight()) / 2));
    }

    private void misLearning(BipartiteGraph graph, SetUniqueList<Integer> ap, InitialParameters ip) {
        int index = random.nextInt(ap.size());
        int index2 = random.nextInt(ap.size());
        while (index == index2) index2 = random.nextInt(ap.size());
        final int index2Final = index2;

        SetUniqueList<Edge> inputEdgesForRandomIndex = SetUniqueList.setUniqueList(graph.getEdges().stream()
                .filter(e -> e.getInputNode().getId() == ap.get(index))
                .collect(Collectors.toList()));
        SetUniqueList<Edge> inputEdgesForRandomIndex2 = SetUniqueList.setUniqueList(graph.getEdges().stream()
                .filter(e -> e.getInputNode().getId() == ap.get(index2Final))
                .collect(Collectors.toList()));
        Edge randomEdgeFromRandomInput = inputEdgesForRandomIndex.get(random.nextInt(inputEdgesForRandomIndex.size()));
        Edge randomEdgeFromRandomInput2 = inputEdgesForRandomIndex2.get(random.nextInt(inputEdgesForRandomIndex2.size()));
        randomEdgeFromRandomInput.setWeight(randomEdgeFromRandomInput.getWeight() + randomEdgeFromRandomInput2.getWeight());
    }
}
