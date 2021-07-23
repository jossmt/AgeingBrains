package com.brain.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Edge {

    private double weight;
    private InputNode inputNode;
    private OutputNode outputNode;

    public Edge(double weight, InputNode inputNode, OutputNode outputNode) {
        this.weight = weight;
        this.inputNode = inputNode;
        this.outputNode = outputNode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public InputNode getInputNode() {
        return inputNode;
    }

    public void setInputNode(InputNode inputNode) {
        this.inputNode = inputNode;
    }

    public OutputNode getOutputNode() {
        return outputNode;
    }

    public void setOutputNode(OutputNode outputNode) {
        this.outputNode = outputNode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Edge{");
        sb.append("weight=").append(weight);
        sb.append(", inputNode=").append(inputNode.getId());
        sb.append(", outputNode=").append(outputNode.getId());
        sb.append('}');
        return sb.toString();
    }
}
