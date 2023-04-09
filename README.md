# ISF Picking Scheduler Problem

### Summary of the task:
<b>In Store Fulfillment (ISF)</b> is one of the ways to fulfill orders.<br>
Employees walk around the store and collect items that customers have ordered
online. Then, the completed orders are placed in lockable cabinets, where customers can pick 
them up on their own. An ISF can be an ordinary shop, or the so-called "dark store". <br>
Every working day at midnight a list of orders is sent to ISFa to be prepared in the next day. 
Later, during work hours, employees (pickers) search for the items that customers have purchased and their
substitutes, and complete the orders in this way. Then they put them in
lockers, and customers can pick them up 24/7.

## Goals

- Goals 1: <br>
  First goal is to create an application that will prepare a schedule for completing orders for 
ISF employees. The aim is to complete as many orders as possible. Each picker should be assigned a 
list of orders to be completed on a working day. Orders that cannot be completed on time should
be skipped as a result. The algorithm should be fast to prepare a schedule in less than 20 seconds.

- Goals 2: <br>
  For the second goal, we would like to maximize the value of completed orders, by taking into
account the orderValue parameter in the algorithm. Goal 2 aims to modify the solution from 
the first part to better meet company business needs.

## Technologies used
Project is created with:
- Java 17
- Maven
- Jackson library - used for reading data from json format

## My ideas and train of thought

This optimization task looks to me very similar to the task scheduling, job scheduling optimization problems 
that were discussed in the operating systems course in my university. <br>
Therefore, the first solutions that came to my mind were the algorithms: <br>
- Shortest Job First (SJF), 
- Earliest Deadline First, 
- Round Robin (RR), 
- Weighted Shortest Job First (WSJF)

However, after a short attempt, it turned out that the task is not that simple. 
Although it resembles dividing tasks into multiple threads, 
we also have imposed deadlines for each task and an upper time limit.

#### This task started to remind me of well-known problems in computer science:
- Knapsack problem, but for multiple knapsacks and with a time limit
- Problem with creating an optimal schedule
- Max3Sat problem
- Travelling Salesman Problem (TSP)

As it happens, about a year ago I used a genetic algorithm to solve the max3sat 
problem with a very good result, so I decided to use it here as well.
An alternative solution could be tabu search, but I didn't try to use it here.
<br> 

### Data types
I noticed that the data type used to store the order price value was BigDecimal.
This data type is of course correct in calculations related to price and provides 
security for operations on them. However, when examining the test data, I found that 
the minimum value was 2.9 and the maximum was 16.72. I decided to store the data as a 
double, as operations on it will be much faster. In addition, we do not perform any 
operations other than adding and subtracting, so the possibility of an error is small. 
Moreover, I think there is a low chance that the product value will exceed the range 
of double, but it is possible. Additionally, operations on duration and localtime 
have been reduced to operations on seconds stored in int. This makes all kinds of 
operations much faster.


## Summary
I think I managed to solve the task quite well, which for simple and medium-sized problems 
finds the correct (or relatively good) solution in a very short time.
