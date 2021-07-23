package com.brain.core.service;

import com.brain.core.model.BipartiteGraph;
import com.brain.core.model.Edge;
import com.brain.core.rest.model.InitialParameters;
import org.apache.commons.collections4.list.SetUniqueList;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LearningService {

    private Random random = new Random();

    // Long-Term Potentiation
    public int ltpLearning(BipartiteGraph graph, SetUniqueList<Integer> ap, InitialParameters ip) {

        int steps = 0;
        while (!graph.inputPatternExceedsThreshold(ap, ip)) {
            int index = random.nextInt(ap.size());
            SetUniqueList<Edge> outputEdgesForRandomIndex = SetUniqueList.setUniqueList(graph.getEdges().stream()
                    .filter(e -> e.getInputNode().getId() == ap.get(index))
                    .collect(Collectors.toList()));
            Edge randomEdgeFromRandomInput = outputEdgesForRandomIndex.get(random.nextInt(outputEdgesForRandomIndex.size()));
            randomEdgeFromRandomInput.setWeight(randomEdgeFromRandomInput.getWeight() + ((1 - randomEdgeFromRandomInput.getWeight()) / 2));
            steps++;
        }

        //TODO: Include more details about output pattern that was activated

        // Reset edge weights for the next iteration (maybe a better way to do this, do we even need input/output nodes)
        graph.resetEdgeWeights();

        return steps;
    }

    // Multi-dendritic innervated spines
    public int misLearning(BipartiteGraph graph, SetUniqueList<Integer> ap, InitialParameters ip) {

        int steps = 0;
        while (!graph.inputPatternExceedsThreshold(ap, ip)) {
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
            steps++;
            System.out.println("Finished step" + steps);
        }

        //TODO: Include more details about output pattern that was activated

        graph.resetEdgeWeights();

        return steps;
    }
}
