Zappos
======

The required jar files are in lib folder. The main class is located in src\zappos\product\main folder.
It takes two commandline argument:
1. desired number of products
2. price limit/desired dollar amount 
These inputs could be provided by setting command line arguments in Eclipse Run configuration.

Also providing the executable JAR file for this project by name 'zappos.jar' 
Running the jar: 

>java -jar zappos.jar 3 15.00

where 3 and 15.00 are the command line arguments, 3 being the number of products and 15.00 being  the price limit/desired dollar amount.

Note:
(More information about below metioned notes is given as part of source code in form of comments)
1. The number of the pages being fetched is currently set to 20 pages which could be increased by changing 'PAGE_NUM' parameter. The product combinations within $1.00 range of desired dollar amount are considered to satisfy the criteria and hence become part of the output. This $1.00 margin is customizable and controlled by 'priceMargin' parameter. More infomation is given as comments in the source code. 
2. Also as the products are sorted according to pice (using sort parameter of Zappos API), the products with high cost are very much down the pages. Thus for dealing with high value of desired dollar amount, the 'PAGE_NUM' parameter might have to set to high values to avoid zero product combination but this increases the time latency.
3. The products are assumed to have unique productId
4. The output diplays List of combination of products and their total cost. ProductId, price and Product name is displayed as part of Product detail.


