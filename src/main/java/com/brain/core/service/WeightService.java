package com.brain.core.service;

import com.brain.core.rest.model.InitialParameters;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.NormalizedRandomGenerator;
import org.apache.commons.math3.random.UniformRandomGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeightService {

    NormalizedRandomGenerator normalizedRandomGenerator = new UniformRandomGenerator(new JDKRandomGenerator());
    NormalDistribution normalDistribution = new NormalDistribution(0.5, 0.1);

    public List<Double> generateWeightDistribution(InitialParameters initialParameters) {

        double sum = 0;
        List<Double> initialWeights = new ArrayList<>();
        for (int i = 0; i < initialParameters.getNodeSize(); i++) {

            double randomWeight;
            switch (initialParameters.getWeightDistributionType()) {
                case NORMAL:
                    randomWeight = Math.abs(normalDistribution.sample());
                    break;
                case RANDOM:
                default:
                    randomWeight = Math.abs(normalizedRandomGenerator.nextNormalizedDouble());
                    break;
            }
            initialWeights.add(randomWeight);
            sum += randomWeight;
        }

        final double finalizedSum = sum;
        List<Double> normalizedWeights = new ArrayList<>();
        initialWeights.forEach(iw -> {
            double normalizedValue = iw / finalizedSum;
            normalizedWeights.add(normalizedValue);
        });
        return normalizedWeights;
    }
}
