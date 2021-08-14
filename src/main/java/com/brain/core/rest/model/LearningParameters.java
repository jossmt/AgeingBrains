package com.brain.core.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize
@JsonSerialize
public class LearningParameters {

    private boolean youngLearning;
    private NodeSelectionType inputNodeSelection;
    private NodeSelectionType firstEdgeSelection;
    private NodeSelectionType secondEdgeSelection;

    public boolean isYoungLearning() {
        return youngLearning;
    }

    public boolean getYoungLearning() {
        return youngLearning;
    }

    public void setYoungLearning(boolean youngLearning) {
        this.youngLearning = youngLearning;
    }

    public NodeSelectionType getInputNodeSelection() {
        return inputNodeSelection;
    }

    public void setInputNodeSelection(NodeSelectionType inputNodeSelection) {
        this.inputNodeSelection = inputNodeSelection;
    }

    public NodeSelectionType getFirstEdgeSelection() {
        return firstEdgeSelection;
    }

    public void setFirstEdgeSelection(NodeSelectionType firstEdgeSelection) {
        this.firstEdgeSelection = firstEdgeSelection;
    }

    public NodeSelectionType getSecondEdgeSelection() {
        return secondEdgeSelection;
    }

    public void setSecondEdgeSelection(NodeSelectionType secondEdgeSelection) {
        this.secondEdgeSelection = secondEdgeSelection;
    }
}
