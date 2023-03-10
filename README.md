# bit - Search words positions

# Input / Output:
Input: textUrl and set of words <br/>
output: Each word where it exists in the text(which lines, and which char it start in the line)
<hr/>

# Technologies:
<ul>
<div>1. JAVA Spring boot</div>
<div>2. MongoDB</div>
</ul>
<hr/>

# Architecture Description:
Searching done by 2 main thing: 
<ul>
<li>Hashing: search the word in each string done by hashmap</li>
<li>Multi-threading: scan for each part (of 1000 rows by default) by multi-threading (by default 1 thread)</li>
</ul>

# Packages Description: <br/>
<ul>
<li>controllers: Contains the controller of the API and the dtos for request and response</li>
<li>exceptions: Contains the custom exception - there are an exception called Validation exception, will be thrown when validation issues thrown </li>
<li>models: Contains the model for mongoDB</li>
<li>pojos: Contains the pojos we need in the app</li>
<li>repositories: contains the queries for mongoDB</li>
<li>services: Contains all services in the app</li>
<h2>Services is: </h2><br/>
<li>MatcherService: its role is to search the words in string</li>
<li>AggregationService: Do the aggregation step</li>
<li>
SearchingService: It is the main service, contains reading the file in parts, creat threads which each thread 
search the words in one line and save the result he found in mongoDB, and when the matching done for all 
the file, it will call the aggregator to aggregate the data.
</li>
</ul>

# Unit Tests
This project also contains UT for the services.
