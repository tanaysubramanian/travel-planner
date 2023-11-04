# Travel Planner

In this project, I designed a program that plans a custom travel route based on a user's needs. This program can read in a travel graph and output an optimal route for the user in any one of 3 priorities: price (cheapest route), time (shortest travel time), and directness (least connections). I implemented Dijkstra’s algorithm to determine the route for the priorities of price and time. I implemented Breadth-first search (BFS) algorithm to find the path for the priority of directness.

# Results
Example travel graph:

<img src="https://github.com/tanaysubramanian/travel-planner/assets/139258609/6c86709a-cafb-4887-96d9-c7da3d575c21" alt="Image" width="473" height="392.5"> <br />

Program output of the cheapest route from Boston to New York in the above graph:
<img src="https://github.com/tanaysubramanian/travel-planner/assets/139258609/134dec04-418e-49b4-87b6-549f704e802b" alt="Image" width="626.25" height="103.5"> <br />

Program output of the shortest travel time route from Boston to New York in the above graph:
<img src="https://github.com/tanaysubramanian/travel-planner/assets/139258609/d46b7833-82e1-4cca-9d0e-850c25ed8b93" alt="Image" width="621" height="85.5"> <br />

Program output of the most direct route from Hanover to Providence in the above graph:
<img src="https://github.com/tanaysubramanian/travel-planner/assets/139258609/52132493-8439-415d-865a-271defe53284" alt="Image" width="598" height="85.5"> <br />
