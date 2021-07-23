package com.brain.core;

import com.brain.core.model.WeightDistributionType;
import com.brain.core.rest.model.InitialParameters;
import com.brain.core.service.WeightService;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class WeightServiceTest {

    WeightService weightService = new WeightService();

    @Test
    public void testRandomWeightDistribution() {

        InitialParameters initialParameters = new InitialParameters();
        initialParameters.setNodeSize(10);
        initialParameters.setWeightDistributionType(WeightDistributionType.RANDOM);
        List<Double> randomWeightDistribution = weightService.generateWeightDistribution(initialParameters);
        System.out.println(Arrays.toString(randomWeightDistribution.toArray()));
        double sum = 0;
        for (double dl : randomWeightDistribution) {
            sum += dl;
        }
        System.out.println(sum);
    }

    @Test
    public void testNormalWeightDistribution() {

        InitialParameters initialParameters = new InitialParameters();
        initialParameters.setNodeSize(10);
        initialParameters.setWeightDistributionType(WeightDistributionType.NORMAL);
        List<Double> randomWeightDistribution = weightService.generateWeightDistribution(initialParameters);
        System.out.println(Arrays.toString(randomWeightDistribution.toArray()));
        double sum = 0;
        for (double dl : randomWeightDistribution) {
            sum += dl;
        }
        System.out.println(sum);
    }
}
