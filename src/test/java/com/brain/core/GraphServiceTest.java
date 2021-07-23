package com.brain.core;

import com.brain.core.model.BipartiteGraph;
import com.brain.core.model.WeightDistributionType;
import com.brain.core.rest.model.InitialParameters;
import com.brain.core.service.GraphService;
import com.brain.core.service.LearningService;
import com.brain.core.service.PatternsService;
import com.brain.core.service.WeightService;
import org.junit.Test;

public class GraphServiceTest {

    private GraphService graphService = new GraphService(new WeightService(), new PatternsService(), new LearningService());

    @Test
    public void initialiseGraph() {
        InitialParameters initialParameters = new InitialParameters();
        initialParameters.setNodeSize(10);
        initialParameters.setActivatedNodeSize(3);
        initialParameters.setWeightThreshold(0.43);
        initialParameters.setWeightDistributionType(WeightDistributionType.RANDOM);
        BipartiteGraph graph = graphService.initialiseBipartiteGraph(initialParameters);
        assert graph.getEdges().size() == 100;
    }

    @Test
    public void learningYoungTest() {
        initialiseGraph();
        graphService.generateActivationPatterns();
        graphService.triggerLearning(true);
    }
}
