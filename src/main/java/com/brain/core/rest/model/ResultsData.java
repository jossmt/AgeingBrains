package com.brain.core.rest.model;

import com.brain.core.model.OutputNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonSerialize
public class ResultsData {

    // Key: step count, Value: count of input patterns that completed learning at this step
    Map<Integer, Integer> learningStepCount = new HashMap<>();
    List<Set<OutputNode>> activatedOutputNodes = new ArrayList<>();
    Map<OutputNode, Integer> activatedOutputNodeCount = new HashMap<>();

    public Map<Integer, Integer> getLearningStepCount() {
        return learningStepCount;
    }

    public void setLearningStepCount(Map<Integer, Integer> learningStepCount) {
        this.learningStepCount = learningStepCount;
    }

    public List<Set<OutputNode>> getActivatedOutputNodes() {
        return activatedOutputNodes;
    }

    public void setActivatedOutputNodes(List<Set<OutputNode>> activatedOutputNodes) {
        this.activatedOutputNodes = activatedOutputNodes;
    }

    public Map<OutputNode, Integer> getActivatedOutputNodeCount() {
        return activatedOutputNodeCount;
    }

    public void setActivatedOutputNodeCount(Map<OutputNode, Integer> activatedOutputNodeCount) {
        this.activatedOutputNodeCount = activatedOutputNodeCount;
    }

    public void addOutputNodeSet(Set<OutputNode> outputNodes) {
        outputNodes.forEach(on -> {
            Integer count = activatedOutputNodeCount.getOrDefault(on, 0) + 1;
            activatedOutputNodeCount.put(on, count);
        });
        activatedOutputNodes.add(outputNodes);
    }
}
