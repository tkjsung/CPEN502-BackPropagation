// BackPropagationTest.java
// Author: Tom Sung

package CPEN502_BP;

import org.junit.Test;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BackPropagationTest {

    public void testRNG(){
        BackPropagation bp = new BackPropagation(8,4,0.2,0.9,"bipolar",true);
        bp.zeroWeights();
        bp.initializeWeights();
        double [] hiddenNum = bp.getHiddenWeights();
        double [] inputNum = bp.getInputWeights();
        System.out.print("The input layer random numbers are: ");
        for (int i = 0; i<inputNum.length; i++) System.out.print(inputNum[i] + ", ");
        System.out.print("\nThe hidden layer random numbers are: ");
        for (int i = 0; i<hiddenNum.length; i++) System.out.print(hiddenNum[i] + ", ");

    }

    public void testString() {
        String s = "String1";
        String t = "String2";
        String u = "String1";
        if (s.equals(u) == false){
            System.out.println("1");
        } else {
            System.out.println("2");
        }
    }

    public void testScanner(){
        char confirm;
        String representation = "binary"; //Default setting
        int iterations = 1; //Default iteration is 1.
        double learnRate = 0.2; //Default learning rate
        double momentum = 0; //Default momentum value
        boolean bipolar = false; //Default is false
        boolean userChoice = false;
        Scanner inputSC = new Scanner(System.in);

        // Bipolar or binary
        while(!userChoice) {
            System.out.println("Bipolar Representation? (Y/N): ");
            confirm = inputSC.next().charAt(0);
            representation = Character.toString(confirm);

            if (representation.equalsIgnoreCase("Y")) {
                bipolar = true;
                userChoice = true;
            } else if (representation.equalsIgnoreCase("N")) {
                bipolar = false;
                userChoice = true;
            } else {
                userChoice = false;
                inputSC.nextLine();
                System.out.println("Please type either Y or N. Try again.");
            }
        }

        // # of iterations to do
        userChoice = false;
        while(!userChoice) {
            try {
                System.out.println("Number of iterations?: ");
                iterations = inputSC.nextInt();
                userChoice = true;
                if(iterations <= 0){
                    System.out.println("Iterations cannot be negative. Please try again.");
                    userChoice = false;
                } else if (iterations >= 1000){ //not a necessary condition.
                    System.out.println("Iterations is very large (above 1000). Try something smaller.");
                    userChoice = false;
                }
            } catch (InputMismatchException e){
                System.out.println("\"" + e + "\"" + "is not the correct input type. Please try again.");
                inputSC.nextLine();
                userChoice = false;
            }
        }

        // Get learning rate
        userChoice = false;
        while(!userChoice){
            try {
                System.out.println("Set learning rate (between 0 and 1): ");
                learnRate = inputSC.nextDouble();
                userChoice = true;
                if(learnRate < 0){
                    System.out.println("Learning rate cannot be negative. Please try again.");
                    userChoice = false;
                } else if (learnRate > 1){ //not a necessary condition.
                    System.out.println("Learning rate is above 1. Enter something else.");
                    userChoice = false;
                }
            } catch (InputMismatchException e){
                System.out.println("\"" + e + "\"" + "is not the correct input type. Please try again.");
                inputSC.nextLine();
                userChoice = false;
            }
        }

        // Get momentum
        userChoice = false;
        while(!userChoice){
            try {
                System.out.println("Set momentum (between 0 and 1): ");
                momentum = inputSC.nextDouble();
                userChoice = true;
                if(momentum < 0){
                    System.out.println("Momentum cannot be negative. Please try again.");
                    userChoice = false;
                } else if (momentum > 1){ //not a necessary condition.
                    System.out.println("Momentum cannot be above 1. Try something else.");
                    userChoice = false;
                }
            } catch (InputMismatchException e){
                System.out.println("\"" + e + "\"" + "is not the correct input type. Please try again.");
                inputSC.nextLine();
                userChoice = false;
            }
        }

        if(bipolar)
            System.out.println("Chosen representation: Bipolar. # of iterations chosen: " + iterations +
                    ". Learning rate is " + learnRate + ". Momentum is " + momentum);
        else System.out.println("Chosen representation: Binary. # of iterations chosen: " + iterations + ".");

    }

    @Test
    public void startBP_manualValues(){
//        char confirm;
        String representation = "bipolar"; //Default setting
        int iterations = 1; //Default iteration is 1.
        double learnRate = 0.2; //Default learning rate
        double momentum = 0.9; //Default momentum value
        boolean bipolar = true; //Default is false
//        boolean userChoice = false;
//        Scanner inputSC = new Scanner(System.in);


        System.out.println("Chosen representation: Bipolar. # of iterations chosen: " + iterations +
                ". Learning rate is " + learnRate + ". Momentum is " + momentum);


        algorithmBP algorithm = new algorithmBP();
        algorithm.setIterations(iterations);
        algorithm.setLearnRate(learnRate);
        algorithm.setMomentum(momentum);
        algorithm.setRepresentation(bipolar);
        algorithm.algorithm();

    }


    // TODO: make another test unit that doesn't take user input for testing purposes. Then implement scanner
    // TODO: Improve user input by implementing them in another class, not this test unit.
    public void startBP(){
        char confirm;
        String representation = "binary"; //Default setting
        int iterations = 1; //Default iteration is 1.
        double learnRate = 0.2; //Default learning rate
        double momentum = 0; //Default momentum value
        boolean bipolar = false; //Default is false
        boolean userChoice = false;
        boolean biasWeight = true;
        Scanner inputSC = new Scanner(System.in);

        // Bipolar or binary
        while(!userChoice) {
            System.out.println("Bipolar Representation? (Y/N): ");
            confirm = inputSC.next().charAt(0);
            representation = Character.toString(confirm);

            if (representation.equalsIgnoreCase("Y")) {
                bipolar = true;
                userChoice = true;
            } else if (representation.equalsIgnoreCase("N")) {
                bipolar = false;
                userChoice = true;
            } else {
                userChoice = false;
                inputSC.nextLine();
                System.out.println("Please type either Y or N. Try again.");
            }
        }

        // # of iterations to do
        userChoice = false;
        while(!userChoice) {
            try {
                System.out.println("Number of iterations?: ");
                iterations = inputSC.nextInt();
                userChoice = true;
                if(iterations <= 0){
                    System.out.println("Iterations cannot be negative. Please try again.");
                    userChoice = false;
                } else if (iterations >= 1000){ //not a necessary condition.
                    System.out.println("Iterations is very large (above 1000). Try something smaller.");
                    userChoice = false;
                }
            } catch (InputMismatchException e){
                System.out.println("\"" + e + "\"" + "is not the correct input type. Please try again.");
                inputSC.nextLine();
                userChoice = false;
            }
        }

        // Get learning rate
        userChoice = false;
        while(!userChoice){
            try {
                System.out.println("Set learning rate (between 0 and 1): ");
                learnRate = inputSC.nextDouble();
                userChoice = true;
                if(learnRate < 0){
                    System.out.println("Learning rate cannot be negative. Please try again.");
                    userChoice = false;
                } else if (learnRate > 1){ //not a necessary condition.
                    System.out.println("Learning rate is above 1. Enter something else.");
                    userChoice = false;
                }
            } catch (InputMismatchException e){
                System.out.println("\"" + e + "\"" + "is not the correct input type. Please try again.");
                inputSC.nextLine();
                userChoice = false;
            }
        }

        // Get momentum
        userChoice = false;
        while(!userChoice){
            try {
                System.out.println("Set momentum (between 0 and 1): ");
                momentum = inputSC.nextDouble();
                userChoice = true;
                if(momentum < 0){
                    System.out.println("Momentum cannot be negative. Please try again.");
                    userChoice = false;
                } else if (momentum > 1){ //not a necessary condition.
                    System.out.println("Momentum cannot be above 1. Try something else.");
                    userChoice = false;
                }
            } catch (InputMismatchException e){
                System.out.println("\"" + e + "\"" + "is not the correct input type. Please try again.");
                inputSC.nextLine();
                userChoice = false;
            }
        }

        if(bipolar)
            System.out.println("Chosen representation: Bipolar. # of iterations chosen: " + iterations +
                    ". Learning rate is " + learnRate + ". Momentum is " + momentum);
        else System.out.println("Chosen representation: Binary. # of iterations chosen: " + iterations +
                ". Learning rate is " + learnRate + ". Momentum is " + momentum);

        algorithmBP algorithm = new algorithmBP();
        algorithm.setIterations(iterations);
        algorithm.setLearnRate(learnRate);
        algorithm.setMomentum(momentum);
        algorithm.setRepresentation(bipolar);
//        algorithm.setBiasWeight(biasWeight); // Uncomment to change bias weight to false.
        algorithm.algorithm();

        }

    // Manually entering values
    public void testBP(){
        // Variable Set-up: Expected input/output
//        String representation = "binary";
        String representation = "bipolar";
        int iterations = 100;

        // Variable Set-up: Arrays
        double[][] binary_input = {{0,0}, {0,1}, {1,0}, {1,1}};
        double[][] binary_input_bias = {{0,0,1}, {0,1,1}, {1,0,1}, {1,1,1}};
        double[] binary_output = {0.0, 1.0, 1.0, 0.0};
        double[][] bipolar_input = {{-1,-1}, {-1,+1}, {+1,-1}, {+1,+1}};
        double[][] bipolar_input_bias = {{-1,-1,1}, {-1,+1,1}, {+1,-1,1}, {+1,+1,1}};
        double[] bipolar_output = {-1, +1, +1, -1};
        double[][] input_expected = binary_input.clone();
        double[] output_expected = binary_output.clone();
        int[] epoch_num = new int[iterations];
        boolean biasWeight = true;

        if(representation.equals("bipolar")) {
            output_expected = bipolar_output.clone();
            if (biasWeight == false){
                input_expected = bipolar_input.clone();
            }
            else{
                input_expected = bipolar_input_bias.clone();
            }
        } else {
            output_expected = binary_output.clone();
            if(biasWeight == false){
                input_expected = binary_input.clone();
            } else {
                input_expected = binary_input_bias.clone();
            }
        }

        for(int h = 0; h<iterations; h++) {
            // Variable Set-up: Error
            double[] error_squared = new double[binary_input.length];
            double error = 1000;
            ArrayList<Double> error_all = new ArrayList<Double>();
            double desired_error = 0.05; //desired_error = 0.001;
            int epoch = 1;

            // Call upon class BackPropagation for the main algorithm and set up private variables in the class
            BackPropagation bp = new BackPropagation(8,4,0.2,0.9,"bipolar",true);
//            bp.setLearningRate(0.2);
//            bp.setMomentum(0);
//            bp.setRepresentation(representation);
//            bp.setNumWeights(8,4);
//            bp.setBiasWeight(biasWeight); // Do we want +1 bias weight?
            bp.setArrays();

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

            while (error > desired_error && epoch < 15000+1) {
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

                    // For debugging purposes
//                    double finalOutput = bp.getFinalOutput();
//                    System.out.println("For epoch " + epoch + " and pattern " + (i+1)
//                            + ", actual output: " + finalOutput + ". Expected output: " + output_expected[i]);
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

//            double [] hiddenLayer = bp.getHiddenOutputs();
//            System.out.print("Hidden layer outputs: ");
//            for(int k = 0; k<hiddenLayer.length; k++){
//                System.out.print(hiddenLayer[k] + ", ");
//            }
//            System.out.println();

        }
        BackPropagation bp = new BackPropagation(8,4,0.2,0.9,"bipolar",true);
        bp.fileWrite(epoch_num,"epoch_iterations.txt");
        double epoch_sum = 0;
        for(int i = 0;i<epoch_num.length;i++){
            epoch_sum += epoch_num[i];
        }
        System.out.println("Out of " + iterations + " iterations, the average epoch is: " + epoch_sum/iterations);
    }

}
