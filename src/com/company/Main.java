package com.company;

public class Main {
    public static void main(String[] args) {
        DecisionTree tree = new DecisionTree();
        tree.create_dataset("winequality-white.csv");
        tree.create_tree();
        tree.evaluate_tree();
        tree.prune_tree();
        Forest forest = new Forest();
        forest.create_random_forest("winequality-white.csv");
        forest.evaluate_random_forest();
    }
}
