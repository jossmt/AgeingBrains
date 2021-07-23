package com.brain.core.rest.model;

import com.brain.core.model.WeightDistributionType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class InitialParameters {

    private int nodeSize;
    private int activatedNodeSize;
    private double weightThreshold;
    private WeightDistributionType weightDistributionType;

    public int getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(int nodeSize) {
        this.nodeSize = nodeSize;
    }

    public int getActivatedNodeSize() {
        return activatedNodeSize;
    }

    public void setActivatedNodeSize(int activatedNodeSize) {
        this.activatedNodeSize = activatedNodeSize;
    }

    public double getWeightThreshold() {
        return weightThreshold;
    }

    public void setWeightThreshold(double weightThreshold) {
        this.weightThreshold = weightThreshold;
    }

    public WeightDistributionType getWeightDistributionType() {
        return weightDistributionType;
    }

    public void setWeightDistributionType(WeightDistributionType weightDistributionType) {
        this.weightDistributionType = weightDistributionType;
    }
}
