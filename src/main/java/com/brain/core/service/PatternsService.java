package com.brain.core.service;

import com.brain.core.rest.model.InitialParameters;
import org.apache.commons.collections4.list.SetUniqueList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class PatternsService {

    public Set<SetUniqueList<Integer>> generateActivationPatterns(InitialParameters initialParameters) {

        Set<SetUniqueList<Integer>> activationPatterns = new HashSet<>();

        int[] arr = new int[initialParameters.getNodeSize()];
        generateAllBinaryStrings(activationPatterns, initialParameters.getActivatedNodeSize(), arr, initialParameters.getNodeSize(), 0);
        return activationPatterns;
    }

    private void generateAllBinaryStrings(Set<SetUniqueList<Integer>> activationPatterns, int activeNodeSize, int[] arr, int n, int i) {
        if (i == n) {
            addPattern(activationPatterns, activeNodeSize, arr);
            return;
        }
        arr[i] = 0;
        generateAllBinaryStrings(activationPatterns, activeNodeSize, arr, n, i + 1);
        arr[i] = 1;
        generateAllBinaryStrings(activationPatterns, activeNodeSize, arr, n, i + 1);
    }

    private void addPattern(Set<SetUniqueList<Integer>> allPerms, int targetActiveCount, int[] arr) {

        int activeCount = 0;
        SetUniqueList<Integer> indexes = SetUniqueList.setUniqueList(new ArrayList<>());
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                indexes.add(i);
                activeCount++;
            }
        }
        if (activeCount == targetActiveCount) {
            allPerms.add(indexes);
        }
    }
}
