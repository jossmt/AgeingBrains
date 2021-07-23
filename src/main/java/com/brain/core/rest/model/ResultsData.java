package com.brain.core.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize
public class ResultsData {

    // Key: step count, Value: count of input patterns that completed learning at this step
    Map<Integer, Integer> learningStepCount = new HashMap<>();

    public Map<Integer, Integer> getLearningStepCount() {
        return learningStepCount;
    }

    public void setLearningStepCount(Map<Integer, Integer> learningStepCount) {
        this.learningStepCount = learningStepCount;
    }
}
