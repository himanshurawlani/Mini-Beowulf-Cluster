# Finding-primes-using-Socket-programming

## MINI PROJECT-BEOWULF CLUSTER

### AIM: 
To implement Beowulf cluster using java socket programming by calculating number of primes in the range 0 - N(input), using client server architecture.

### PROJECT REQUIREMENTS:
•	At least 3 computers (1 server,2 clients)

•	Java Runtime Environment

### DESCRIPTION:

#### 1) Beowulf Cluster:
  	Beowulf is a multi-computer architecture which can be used for parallel computations. A Beowulf cluster is a computer cluster of what are normally identical, commodity-grade computers networked into a small local area network with libraries and programs installed which allow processing to be shared among them. The result is a high-performance parallel computing cluster from inexpensive personal computer hardware.

  	Beowulf clusters normally run a Unix-like operating system, such as BSD, Linux, or Solaris, normally built from free and open source software. Commonly used parallel processing libraries include Message Passing Interface (MPI) and Parallel Virtual Machine (PVM).


#### 3) IMPLEMENTATION:
  	In our mini project we have implemented the concept of Beowulf cluster using java socket programming which uses java.net library.

  	Using Socket programming, Server-Client network is formed.

  	After the network is established, a range of numbers (0 – N) is given as an input (N) on the server side.

  	Server divides this range into equal sub-ranges and assign each client to find the number of prime numbers in that sub-range.

  	Each client sends its output to server after the completion of execution of task. Server, then, pipelines the output to the text file which the user can read.


#### 4) ALGORITHM:

  Server:-
  1.	Accept a number
  2.	Put the number in sample file in desktop
  3.	Wait for clients to connect
  4.	Assign a unique client id to every client and send to the client
  5.	Distribute the range evenly to all the connected clients
  6.	Wait for all the clients to send back the result
  7.	Store the results in the sample file on desktop
  
Client:-
  1.	Connect to server
  2.	Print successful connection message with client id
  3.	Receive the range of number from server
  4.	Find the number of primes in the given range
  5.	Send the result to server
  6.	Stop


Please refer the Mini Project Report.docx for the diagram of Beowulf cluster, the algorithm used and input-output screenshots.
