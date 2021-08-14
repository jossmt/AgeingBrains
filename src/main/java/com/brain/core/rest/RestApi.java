package com.brain.core.rest;

import com.brain.core.model.BipartiteGraph;
import com.brain.core.rest.model.ActivationPatternDetails;
import com.brain.core.rest.model.InitialParameters;
import com.brain.core.rest.model.LearningParameters;
import com.brain.core.rest.model.ResultsData;
import com.brain.core.service.GraphService;
import com.brain.core.service.WeightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/brain", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestApi {

    GraphService graphService;
    WeightService weightService;

    public RestApi(GraphService graphService, WeightService weightService) {
        this.graphService = graphService;
        this.weightService = weightService;
    }

    @PostMapping("/initiate")
    public @ResponseBody
    BipartiteGraph initialise(@RequestBody InitialParameters initialParameters) {
        return graphService.initialiseBipartiteGraph(initialParameters);
    }

    @GetMapping("/graph")
    public @ResponseBody
    BipartiteGraph getGraph() {
        return graphService.getGraph();
    }

    @GetMapping("/generateActivationPatterns")
    public @ResponseBody
    ActivationPatternDetails generateActivationPatterns() {
        return graphService.generateActivationPatterns();
    }

    @GetMapping("/updateThreshold/{threshold}")
    public void updateThreshold(@PathVariable("threshold") double threshold) {
        graphService.getInitialParameters().setWeightThreshold(threshold);
    }

    @PostMapping("/learning/start")
    public @ResponseBody
    ResultsData startLearning(@RequestBody LearningParameters learningParameters) {
        System.out.println("Started learning");
        ResultsData resultsData = graphService.triggerLearning(learningParameters);
        System.out.println("Finished learning");
        System.out.println(resultsData);
        return resultsData;
    }

    @GetMapping("/results")
    public @ResponseBody
    ResultsData getResults() {
        return graphService.getResultsData();
    }
}
