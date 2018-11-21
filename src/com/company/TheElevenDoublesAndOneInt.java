package com.company;

public final class TheElevenDoublesAndOneInt {
    private final int attributeNumber = 11;
    private final double[] array = new double[attributeNumber];
    private final int label;

    public TheElevenDoublesAndOneInt(String line) {
        String[] words = line.split(";");
        for (int i=0 ; i<=attributeNumber-1 ; i++){
            array[i] = Double.valueOf(words[i]);
        }
        label = Integer.valueOf(words[attributeNumber]);
    }

    public double getAttribute(int i){
        return array[i];
    }

    public int getLabel(){
        return label;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0 ; i<=attributeNumber-1 ; i++){
            stringBuilder.append(array[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\nLabel: ");
        stringBuilder.append(label);
        return stringBuilder.toString();
    }
}
