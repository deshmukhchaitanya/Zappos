Zappos
======

The required jar files are in lib folder. The main class is located in src\zappos\product\main folder.
It takes two commandline argument:
1. number of products
2. price limit 
These could be provided by setting command line arguments in Eclipse Run configuration

Also providing the executable JAR file for this project by name 'zappos.jar'
Running the jar: 

>java -jar zappos.jar 3 15.00

where 3 is the number of input and 15 is the price limit

Note:
The number of pages to fetch is currently set to 20 pages which could be increased by changing PAGE_NUM parameter.
The combinations within $1.00 range of given price limit are considered to satisfy the criteria.
This $1.00 margin is customizable and could be increased. More infomation is given in the comments in the source code.

