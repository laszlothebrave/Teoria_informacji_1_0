package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

public class DecisionTree {
    private Node root;
    private ArrayList<TheElevenDoublesAndOneInt> trainingSet = new ArrayList<>();
    private ArrayList<TheElevenDoublesAndOneInt> validationSet = new ArrayList<>();
    private ArrayList<TheElevenDoublesAndOneInt> testSet = new ArrayList<>();

    public void create_dataset(String fileName){
        create_dataset(fileName, 5,1,1);
    }

    public void create_dataset(String fileName, int training, int validation, int test){
        Random generator = new Random();
        int sumTVT = training + validation + test;
        int trainingPlusValidation = training + validation;
        Path path = FileSystems.getDefault().getPath(fileName);
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            System.out.println("File name is " + path + " and first line of it is:");
            System.out.println(reader.readLine());
            while ((line = reader.readLine()) != null) {
                int r = generator.nextInt(sumTVT);
                if (r<training) trainingSet.add(new TheElevenDoublesAndOneInt(line));
                    else if( r<trainingPlusValidation) validationSet.add(new TheElevenDoublesAndOneInt(line));
                        else testSet.add(new TheElevenDoublesAndOneInt(line));
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        int vectorsNumber = trainingSet.size() + validationSet.size() + testSet.size();
        System.out.println("TrainingSet size: " + trainingSet.size() + " expected size: " + vectorsNumber*training/sumTVT);
        System.out.println("ValidationSet size: " + validationSet.size() + " expected size: " + vectorsNumber*validation/sumTVT);
        System.out.println("TestSet size: " + testSet.size() + " expected size: " + vectorsNumber*test/sumTVT);
    }

    public void create_tree(){
        root = new Node(null);
        root.addVector(trainingSet);
        root.check();
    }

    public void evaluate_tree(){
        int good = 0, bad = 0;
        Node currentNode;
        for(TheElevenDoublesAndOneInt tmp : testSet) {
            currentNode = root;
            while (currentNode.hasChildren()){
                currentNode = currentNode.consequent(tmp);
            }
            System.out.println(tmp.toString());
            System.out.println("Expected label: " + currentNode.label);
            if (tmp.getLabel() == currentNode.label) good++;
            else bad++;
        }
        System.out.println("Good: " + good);
        System.out.println("Bad: " + bad);
        System.out.println("Precision: " + Math.round((double)good/(bad+good)*100) + "%");
    }

    public void prune_tree() {
        prune_tree(0);
    }

    public void prune_tree(double e) {
        root.prune(this);
    }

    public double acc(){
        int good = 0, bad = 0;
        Node currentNode;
        for(TheElevenDoublesAndOneInt tmp : validationSet) {
            currentNode = root;
            while (currentNode.hasChildren()) {
                currentNode = currentNode.consequent(tmp);
            }
            if (tmp.getLabel() == currentNode.label) good++;
            else bad++;
        }
        return ((double)good/(bad+good)*100);
    }
}
