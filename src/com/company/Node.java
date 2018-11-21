package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Math.log;

public class Node {
    private Node parent;
    private Node left;
    private Node right;
    private ArrayList<TheElevenDoublesAndOneInt> arrayList = new ArrayList<>();
    public int label;
    private int bestAttributeNumber;
    private double bestBoundary;
    private double bestEntropyReduction;

    public Node(Node parent) {
        this.parent = parent;
    }

    public void addVector(ArrayList<TheElevenDoublesAndOneInt> arrayListArg) {
        arrayList.addAll(arrayListArg);
    }

    public void split() {
        bestEntropyReduction = Double.MAX_VALUE;
        int arr1[] = new int[11];
        int arr2[] = new int[11];
        double newEntropy;
        double entropy1;
        double entropy2;
        double sum1;
        double sum2;
        double sum3;
        for(int attributeNumber=0 ; attributeNumber<=10 ; attributeNumber++){
            for(TheElevenDoublesAndOneInt candidateForBorder : arrayList) {
                Arrays.fill(arr1,0);
                Arrays.fill(arr2,0);
                newEntropy = 0;
                sum1 = 0;
                sum2 = 0;
                entropy1 = 0;
                entropy2 = 0;
                for(TheElevenDoublesAndOneInt tmp : arrayList) {
                    if(tmp.getAttribute(attributeNumber)<=candidateForBorder.getAttribute(attributeNumber))
                        arr1[tmp.getLabel()]++;
                    else
                        arr2[tmp.getLabel()]++;
                }
                for(int i=0 ; i<=10 ; i++){
                    sum1 += arr1[i];
                    sum2 += arr2[i];
                }
                sum3 = sum2 + sum1;
                for(int i=0 ; i<=10 ; i++) {
                    if(arr1[i] != 0) entropy1 -= (arr1[i]/sum1 * log(arr1[i]/sum1));
                    if(arr2[i] != 0) entropy2 -= (arr2[i]/sum2 * log(arr2[i]/sum2));
                }
                newEntropy += sum1/sum3 * entropy1 + sum2/sum3 * entropy2;
                if (newEntropy<bestEntropyReduction && sum1!=0 && sum2!=0){
                    bestAttributeNumber = attributeNumber;
                    bestBoundary = candidateForBorder.getAttribute(attributeNumber);
                    bestEntropyReduction = newEntropy;
                }
            }
        }
        if(bestEntropyReduction!=0) {
            left = new Node(this);
            right = new Node(this);
            for (TheElevenDoublesAndOneInt tmp : arrayList) {
                if (tmp.getAttribute(bestAttributeNumber) <= bestBoundary)
                    left.arrayList.add(tmp);
                else
                    right.arrayList.add(tmp);
            }
            left.check();
            right.check();
        } else {
           setLabel();
        }
    }

    private void setLabel(){
        int[] arr1 = new int[11];
        Arrays.fill(arr1,0);
        for(TheElevenDoublesAndOneInt tmp : arrayList) {
            arr1[tmp.getLabel()]++;
        }
        int max = 0;
        label = 0;
        for(int i=0 ; i<=10 ; i++){
            if (max < arr1[i]) {
                max = arr1[i];
                label = i;
            }
        }
    }
    public void check() {
        System.out.println(arrayList.size());
        for(TheElevenDoublesAndOneInt the : arrayList) {
            if(the.getLabel() != arrayList.get(0).getLabel()) {
                split();
                return;
            }
        }
        label = arrayList.get(0).getLabel();
    }

    public boolean hasChildren() {
        if (right != null && left != null) return true;
        return false;
    }

    public Node consequent(TheElevenDoublesAndOneInt tmp) {
        if (tmp.getAttribute(bestAttributeNumber)<=bestBoundary)
            return left;
        return right;
    }

    public void prune(DecisionTree decisionTree) {
        if(left.hasChildren()) left.prune(decisionTree);
        if(right.hasChildren()) right.prune(decisionTree);
        double currentAcc = decisionTree.acc();

    }
}
