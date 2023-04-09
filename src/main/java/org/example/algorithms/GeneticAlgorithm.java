package org.example.algorithms;

import org.example.classes.Order;
import org.example.classes.Pair;
import org.example.classes.Solution;
import org.example.utils.Function;

import java.util.*;

public class GeneticAlgorithm {
    /* Important note, in this specific Genetic Algorithm, bigger fitness is better,
    *  due to easier implementation of this for our cost functions
    */

    private static final int POPULATION_SIZE = 200; ///100
    private static final double MUTATION_RATE = 0.05;
    private static final double CROSSOVER_RATE = 0.6;
    private static final int TOURNAMENT_SIZE = 8; //5
    private static final int ELITISM_COUNT = 5;
    private static final int MAX_GENERATION = 20; ///100

    private List<Order> orders;
    private List<String> pickers;

    private int pickingStartTime;
    private int pickingEndTime;
    private Random random;

    private Function<Order, Double> costFunction;

    private Chromosome bestSolution = null;
    private int iterationsWithoutImprovement = 0;

    public GeneticAlgorithm(List<Order> orders, List<String> pickers,
                            int pickingStartTime, int pickingEndTime, Function<Order, Double> costFunction) {
        this.orders = orders;
        this.pickers = pickers;
        this.pickingStartTime = pickingStartTime;
        this.pickingEndTime = pickingEndTime;
        this.random = new Random();
        this.costFunction = costFunction;
    }

    public void run() {

        Comparator<Order> completeByThenCostComparator = Comparator
                .comparing(Order::getCompleteBy)
                .thenComparing((o1, o2) -> costFunction.apply(o1).compareTo(costFunction.apply(o2)))
                .thenComparing(Order::getPickingTime);

        orders.sort(completeByThenCostComparator);

        List<Chromosome> population = initPopulation();
        evaluatePopulation(population);

        int generationCount = 0;
        while (generationCount < MAX_GENERATION) {
            List<Chromosome> newPopulation = new ArrayList<>(
                    getElite(population)
            );

            while (newPopulation.size() < POPULATION_SIZE) {
                Chromosome parent1 = tournamentSelection(population);
                Chromosome parent2 = tournamentSelection(population);
                crossoverAndMutation(parent1, parent2, newPopulation);
            }
            population = newPopulation;
            if(evaluatePopulation(population)) {
                iterationsWithoutImprovement = 0;
            } else {
                iterationsWithoutImprovement++;
            }
            generationCount++;
        }

        bestSolution.calculateFitness().printSolution();
    }


    private List<Chromosome> initPopulation() {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE-1; i++) {

            Chromosome chromosome = new Chromosome(orders);
            chromosome.shuffle();
            population.add(chromosome);
        }

        Chromosome initChromosome = new Chromosome(orders);
        initChromosome.calculateFitnessSorted();
        bestSolution = initChromosome;

        population.add(initChromosome);
        return population;
    }

    private boolean evaluatePopulation(List<Chromosome> population) {
        for (Chromosome chromosome : population) {
            chromosome.calculateFitness();
        }

        Collections.sort(population, Collections.reverseOrder());

        if (population.get(0).getFitness() > bestSolution.getFitness()) {
            bestSolution = population.get(0);
            return true;
        }

        return  false;
    }

    private Chromosome getFittest(List<Chromosome> population) {
        return population.get(0);
    }

    private List<Chromosome> getElite(List<Chromosome> population) {
        return population.stream().limit(ELITISM_COUNT).toList();
    }
    private Chromosome tournamentSelection(List<Chromosome> population) {
        List<Chromosome> tournament = new ArrayList<>();
        int populationSize = population.size();
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            tournament.add(population.get(random.nextInt(populationSize)));
        }

        Collections.sort(tournament, Collections.reverseOrder());
        return tournament.get(0);
    }

    private void crossoverAndMutation(Chromosome parent1, Chromosome parent2, List<Chromosome> newPopulation) {

        if (Math.random() < CROSSOVER_RATE) {
            Pair<Chromosome, Chromosome> children = parent1.crossover(parent2);

            mutate(children.l);
            newPopulation.add(children.l);

            if(children.r != null) {
                mutate(children.r);
                newPopulation.add(children.r);
            }
        } else {
            newPopulation.add(parent1);
            newPopulation.add(parent2);
        }

    }

    private void mutate(Chromosome chromosome) {
        chromosome.mutate();
    }





    //Implementation of Chromosome class for our GA
    private class Chromosome implements Comparable<Chromosome> {
        private List<Order> orderList;
        private double  fitness = -1;

        public Chromosome(List<Order> orderList) {
            this.orderList = new ArrayList<>(orderList);
        }

        public List<Order> getOrderList() {
            return orderList;
        }

        public double getFitness() {
            return fitness;
        }

        public void shuffle() {
            Collections.shuffle(orderList);
        }

        public Pair<Chromosome, Chromosome> crossover(Chromosome other) {
//            Chromosome chromosome1 = orderCrossover(other);
//
//            return new Pair<>(
//                 chromosome1, null
//            );
            return pmxCrossover(other);
        }

        //OX (order crossover)
        public Chromosome orderCrossover(Chromosome other) {
            int size = orderList.size();
            int start = random.nextInt(size);
            int end = random.nextInt(size);
            if (start > end) {
                int temp = start;
                start = end;
                end = temp;
            }
            List<Order> childOrderList = new ArrayList<>(Collections.nCopies(size, null));
            for (int i = start; i <= end; i++) {
                childOrderList.set(i, orderList.get(i));
            }
            int index = end + 1;
            for (int i = end + 1; i < size + end + 1; i++) {
                int current = i % size;
                Order currentOrder = other.orderList.get(current);
                if (!childOrderList.contains(currentOrder)) {
                    childOrderList.set(index % size, currentOrder);
                    index++;
                }
            }
            return new Chromosome(childOrderList);
        }

        //Partially-Mapped Crossover (PMX)
        private Pair<Chromosome, Chromosome> pmxCrossover(Chromosome parent2) {
            int size = orderList.size();
            int cuttingPoint1 = random.nextInt(size);
            int cuttingPoint2 = random.nextInt(size - cuttingPoint1) + cuttingPoint1;

            // Create child chromosomes
            List<Order> child1 = new ArrayList<>(Collections.nCopies(size, null));
            List<Order> child2 = new ArrayList<>(Collections.nCopies(size, null));

            // Copy the middle segment from parents to the children
            for (int i = cuttingPoint1; i <= cuttingPoint2; i++) {
                child1.set(i, orderList.get(i));
                child2.set(i, parent2.getOrderList().get(i));
            }

            // Fill remaining genes of child 1
            for (int i = cuttingPoint1; i <= cuttingPoint2; i++) {
                Order gene = parent2.getOrderList().get(i);
                if (!child1.contains(gene)) {
                    int j = parent2.getOrderList().indexOf(orderList.get(i));
                    while (child1.get(j) != null) {
                        j = parent2.getOrderList().indexOf(orderList.get(j));
                    }
                    child1.set(j, gene);
                }
            }
            for (int i = 0; i < size; i++) {
                if (child1.get(i) == null) {
                    child1.set(i, parent2.getOrderList().get(i));
                }
            }

            // Fill remaining genes of child 2
            for (int i = cuttingPoint1; i <= cuttingPoint2; i++) {
                Order gene = orderList.get(i);
                if (!child2.contains(gene)) {
                    int j = orderList.indexOf(parent2.getOrderList().get(i));
                    while (child2.get(j) != null) {
                        j = orderList.indexOf(parent2.getOrderList().get(j));
                    }
                    child2.set(j, gene);
                }
            }
            for (int i = 0; i < size; i++) {
                if (child2.get(i) == null) {
                    child2.set(i, orderList.get(i));
                }
            }

            // Return new order lists - the children chromosomes
            return new Pair<>(
                    new Chromosome(child1),
                    new Chromosome(child2)
            );
        }

        public void mutate() {

            inversionMutation();
//            swapMutation();
        }

        private void inversionMutation() {
            if (Math.random() < MUTATION_RATE) {
                int startIndex = (int) (Math.random() * orderList.size());
                int endIndex = (int) (Math.random() * orderList.size());

                if (startIndex > endIndex) {
                    int temp = startIndex;
                    startIndex = endIndex;
                    endIndex = temp;
                }

                List<Order> genesToReverse = orderList.subList(startIndex, endIndex + 1);
                Collections.reverse(genesToReverse);
            }
        }

        public void swapMutation() {
            if (Math.random() < MUTATION_RATE) {
                int sentenceIndex1 = (int) (Math.random() * orderList.size());
                int sentenceIndex2 = (int) (Math.random() * orderList.size());

                Order order1 = orderList.get(sentenceIndex1);
                Order order2 = orderList.get(sentenceIndex2);

                // Swap sentences in the chromosome
                orderList.set(sentenceIndex1, order2);
                orderList.set(sentenceIndex2, order1);
            }
        }

        @Override
        public int compareTo(Chromosome o) {
            return Double.compare(fitness, o.getFitness());
        }

        public Solution calculateFitness() {
            FulfillmentScheduler fs = new FulfillmentScheduler(this.orderList, pickers, pickingStartTime, pickingEndTime, costFunction);
            Solution genesSolution = fs.scheduleNotSorted();
            this.fitness = genesSolution.getValue();
            return genesSolution;
        }

        public Solution calculateFitnessSorted() {
            FulfillmentScheduler fs = new FulfillmentScheduler(this.orderList, pickers, pickingStartTime, pickingEndTime, costFunction);
            Solution genesSolution = fs.scheduleNotSorted();
            this.fitness = genesSolution.getValue();
            return genesSolution;
        }
    }

}
