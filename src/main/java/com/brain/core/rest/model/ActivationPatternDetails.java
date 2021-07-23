package com.brain.core.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class ActivationPatternDetails {

    double percentage;
    int count;
    int countTotal;

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }
}
