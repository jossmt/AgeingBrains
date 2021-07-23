package com.brain.core.model;

public class OutputNode extends Node {

    public OutputNode(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OutputNode{" + "id=" + id + '}';
    }
}
