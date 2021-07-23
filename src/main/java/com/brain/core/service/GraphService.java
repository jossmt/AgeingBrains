package com.brain.core.service;

import com.brain.core.model.BipartiteGraph;
import com.brain.core.model.Edge;
import com.brain.core.model.InputNode;
import com.brain.core.model.OutputNode;
import com.brain.core.rest.model.ActivationPatternDetails;
import com.brain.core.rest.model.InitialParameters;
import com.brain.core.rest.model.ResultsData;
import org.apache.commons.collections4.list.SetUniqueList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GraphService {

    //TODO: Move this to a shared model that stores all results
    private ResultsData resultsData;
    private Set<SetUniqueList<Integer>> activationPatterns;
    private InitialParameters initialParameters;
    private BipartiteGraph graph;

    private WeightService weightService;
    private PatternsService patternsService;
    private LearningService learningService;

    public GraphService(WeightService weightService, PatternsService patternsService, LearningService learningService) {
        activationPatterns = new HashSet<>();

        this.weightService = weightService;
        this.patternsService = patternsService;
        this.learningService = learningService;
    }

    public BipartiteGraph initialiseBipartiteGraph(InitialParameters initialParameters) {
        if (graph != null) graph.clear();

        HashSet<InputNode> inputNodes = new HashSet<>();
        HashSet<OutputNode> outputNodes = new HashSet<>();
        SetUniqueList<Edge> edges = SetUniqueList.setUniqueList(new ArrayList<>());

        for (int i = 0; i < initialParameters.getNodeSize(); i++) {
            inputNodes.add(new InputNode(i));
        }
        for (int i = initialParameters.getNodeSize(); i < initialParameters.getNodeSize() * 2; i++) {
            outputNodes.add(new OutputNode(i));
        }
        inputNodes.forEach(in -> {
            List<Double> weightDistribution = weightService.generateWeightDistribution(initialParameters);
            int index = 0;
            for (OutputNode on : outputNodes) {
                Edge edge = new Edge(weightDistribution.get(index), in, on);
                edges.add(edge);
                index++;
            }
        });

        this.graph = new BipartiteGraph(edges);
        this.initialParameters = initialParameters;
        return this.graph;
    }

    public ActivationPatternDetails generateActivationPatterns() {

        ActivationPatternDetails activationPatternDetails = new ActivationPatternDetails();
        activationPatterns = patternsService.generateActivationPatterns(initialParameters);

        int activationPatternsSize = activationPatterns.size();

        // Filter patterns to exclude those already activated from learning
        activationPatterns = activationPatterns.stream()
                .filter(ap -> graph.inputPatternActivatesNoOutputNodes(ap, initialParameters))
                .collect(Collectors.toSet());

        int inputPatternsSize = activationPatterns.size();
        activationPatternDetails.setCount(inputPatternsSize);
        activationPatternDetails.setCountTotal(activationPatternsSize);
        activationPatternDetails.setPercentage(((double) inputPatternsSize) / activationPatternsSize);
        return activationPatternDetails;
    }

    public ResultsData triggerLearning(boolean isLtp) {

        ResultsData resultsData = new ResultsData();
        activationPatterns.forEach(ap -> {

            if (isLtp) {
                learningService.ltpLearning(graph, ap, initialParameters, resultsData);
            } else {
                learningService.misLearning(graph, ap, initialParameters, resultsData);
            }
        });

        this.resultsData = resultsData;
        return resultsData;
    }

    public BipartiteGraph getGraph() {
        return graph;
    }

    public InitialParameters getInitialParameters() {
        return initialParameters;
    }

    public ResultsData getResultsData() {
        return resultsData;
    }
}
