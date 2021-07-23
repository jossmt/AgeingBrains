package com.brain.core.model;

public class InputNode extends Node {

    public InputNode(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "InputNode{" + "id=" + id + '}';
    }
}
