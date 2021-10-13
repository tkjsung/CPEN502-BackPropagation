// algorithmBP.java
// Author: Tom Sung

package CPEN502_BP;

import java.util.ArrayList;

public class algorithmBP {
    // Variable Set-up: Representation, Learning rate, Momentum, and Iterations
    private String representation = "binary";
    private int iterations;
    private double momentum;
    private double learnRate;

    // Variable Set-up: Expected input/output; Arrays
    double[][] binary_input = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    double[][] binary_input_bias = {{0, 0, 1}, {0, 1, 1}, {1, 0, 1}, {1, 1, 1}};
    double[] binary_output = {0.0, 1.0, 1.0, 0.0};
    double[][] bipolar_input = {{-1, -1}, {-1, +1}, {+1, -1}, {+1, +1}};
    double[][] bipolar_input_bias = {{-1, -1, 1}, {-1, +1, 1}, {+1, -1, 1}, {+1, +1, 1}};
    double[] bipolar_output = {-1, +1, +1, -1};
    double[][] input_expected = binary_input.clone();
    double[] output_expected = binary_output.clone();

    boolean biasWeight = true; // Hard-coded; don't change

    public void setIterations(int iterations){
        this.iterations = iterations;
    }
    public void setMomentum(double momentum){
        this.momentum = momentum;
    }
    public void setLearnRate(double learnRate){
        this.learnRate = learnRate;
    }
    public void setRepresentation(boolean represent) {
        if (!represent) {
            this.representation = "binary";
        } else {
            this.representation = "bipolar";
        }

        if (representation.equals("bipolar")) {
            this.output_expected = this.bipolar_output.clone();
            if (biasWeight == false) {
                this.input_expected = this.bipolar_input.clone();
            } else {
                this.input_expected = this.bipolar_input_bias.clone();
            }
        } else {
            this.output_expected = this.binary_output.clone();
            if (biasWeight == false) {
                this.input_expected = this.binary_input.clone();
            } else {
                this.input_expected = this.binary_input_bias.clone();
            }
        }
    }
    public void setBiasWeight(boolean biasWeight){
        this.biasWeight = biasWeight;
    }

    public void algorithm()
    {
        // Call upon class BackPropagation for the main algorithm and set up private variables in the class
        BackPropagation bp = new BackPropagation(2,4,this.learnRate,this.momentum,
                this.representation,this.biasWeight);
//        bp.setLearningRate(this.learnRate);
//        bp.setMomentum(this.momentum);
//        bp.setRepresentation(this.representation);
//        bp.setNumWeights(8, 4); // Hard-coded. 2 inputs * 4 hidden = 8 links
//        bp.setBiasWeight(this.biasWeight); // Do we want +1 bias weight?
        bp.setArrays();

        int[] epoch_num = new int[iterations];

        for (int h = 0; h < iterations; h++) {
            // Variable Set-up: Error
            double[] error_squared = new double[binary_input.length];
            double error = 1000;
            ArrayList<Double> error_all = new ArrayList<Double>();
            double desired_error = 0.05; //desired_error = 0.001;
            int epoch = 1;

            // Initializing weights
            bp.zeroWeights();
            bp.initializeWeights();

/*
        // Custom hard-coded weights in the beginning to verify work
        double[] inputWeight = {-0.33 ,0.18,
                0.42, -0.18,
                0.0, 0.02,
                -0.08, 0.39};
        double[] hiddenWeight = {0.25, -0.5, 0.41, -0.17};
        // set the weights in the private variables in BackPropagation.java
        for(int i = 0;i<inputWeight.length;i++) bp.setInputWeights(inputWeight[i],i);
        for(int i = 0;i<hiddenWeight.length;i++) bp.setHiddenWeights(hiddenWeight[i],i);


 */


            bp.savePrevWeights(); // Save previous weights for momentum calculations

            while (error > desired_error && epoch < 15000 + 1) {
                for (int i = 0; i < input_expected.length; i++) {
                    // Forward Propagation
                    bp.hiddenNeurons(input_expected[i]);
                    bp.outputNeuron();

                    double actual_output = bp.getFinalOutput();
//                System.out.println("The final output for pattern " + (i + 1) + " is: " + actual_output);

                    error_squared[i] = Math.pow((actual_output - output_expected[i]), 2);
//                System.out.println("Squared error for pattern " + (i + 1) + " is: " + error_squared[i]);

                    // Back Propagation
                    bp.sigmoidDerivative();
                    bp.deltaFinal(output_expected[i]);
                    bp.updateHiddenWeights();
                    bp.deltaHidden();
                    bp.updateInputWeights(input_expected[i]);
                    bp.savePrevWeights();
                    bp.saveNextWeights();
                    // You don't update weights back to +1... I was mistaken. You don't even need this function
//                    bp.updateBiasWeights(); // This will cause system not converge properly sometimes

                    // For debugging purposes
//                double finalOutput = bp.getFinalOutput();
//                System.out.println("For epoch " + epoch + " and pattern " + (i+1)
//                        + ", actual output: " + finalOutput + ". Expected output: " + output_expected[i]);
                }

                // Calculate Error
                error = bp.error(error_squared);
//            System.out.println("Epoch " + epoch + " error: " + error);
                epoch++;
                error_all.add(error);
            }

            epoch--;
            epoch_num[h] = epoch;

            // Getting total error and saving them into a separate file for easy graphing
            int j = error_all.size();
            double[] error_data = new double[j];
            for (int i = 0; i < error_all.size(); i++) error_data[i] = error_all.get(i);
            bp.fileWrite(error_data, "error.txt");
            System.out.println("The system reached an error of " + error + " in " + epoch + " epochs.");

        }
//        BackPropagation bp = new BackPropagation();
        bp.fileWrite(epoch_num, "epochIterations.txt");
        double epoch_sum = 0;
        for(int i = 0;i<epoch_num.length;i++){
            epoch_sum += epoch_num[i];
        }
        System.out.println("Out of " + iterations + " iterations, the average epoch is: " + epoch_sum/iterations);

    }
}


