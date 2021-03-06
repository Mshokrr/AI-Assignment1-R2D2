%%pad intial positions decalared
pad(2,0).
pad(0,0).

%%teleport intial positions decalared
teleport(1,1).
obstacle(1,0).

%%grid width declaration every predict defines a column
w(0).
w(1).
w(2).


%%grid height declaration every predict defines a row
h(0).
h(1).
h(2).

%%main predicate to run the code where Q represent query to be used
run(Q):-
  run_helper(Q,3).

%%helper for the main predicate just to have the initial starting depth called I
run_helper(Q, I):-
  call_with_depth_limit(Q,I,R),
  run_helper2(Q,I,R).

%%checks if R is not depth_limit_exceeded then the agent have found a solution so it stops and returns this solution
run_helper2(_, _, R):-
  R \= depth_limit_exceeded.

%%checks if R is depth_limit_exceeded then the agent have not found a solution so it increments the depth to search in a deeper level
run_helper2(Q, I, R):-
  R == depth_limit_exceeded,
  I1 is I +1,
  run_helper(Q,I1).

%%agent intial position at situation s0
agent(1,2,s0).

%%agent fluent axioms to move the agent they check if an agent can be in position I,J as a result of an action A in a previous situation S
%%the possible actions are either down, up, right, left

%%Predicate for agent to be in a position I,J as result of down in a previous situation S
agent(I, J, result(down,S)):-
  move_agent(I,J,0,-1, S),
  N is J-1,
  agent(I,N,S).

%%Predicate for agent to be in a position I,J as result of up in a previous situation S
agent(I, J, result(up,S)):-
  move_agent(I,J,0,1,S),
  N is J+1,
  agent(I,N,S).

%%Predicate for agent to be in a position I,J as result of right in a previous situation S
agent(I, J, result(right,S)):-
  move_agent(I,J,-1,0,S),
  N is I-1,
  agent(N,J,S).

%%Predicate for agent to be in a position I,J as result of left in a previous situation S
agent(I, J, result(left,S)):-
  move_agent(I,J,1,0,S),
  N is I+1,
  agent(N,J,S).

%%move_agent is used to check if the agent can be in the I,J given the previous state S
%%moreover this predicate takes two extra parameters K,L they are used to generalize the method where K represent change in I and L represent change in J

%%This is the case to check if the agent is moving to a cell I,J which was completely free in situation S and exists inside the grid
move_agent(I, J, _, _, S):-
  \+ rock(I,J,S),
  \+ obstacle(I,J),
  w(I),
  h(J).

%%This is the case to check if the agent is moving to a cell I,J which is not free and if it contains a rock that can be pushed so the agent will be also alowed to move
move_agent(I, J, K, L, S):-
  N1 is I-K,
  N2 is J-L,
  rock(I,J,S),
  \+pad(I,J),
  \+rock(N1,N2, S),
  \+obstacle(N1,N2),
  w(N1),
  h(N2).

%%rock intial position at situation s0
rock(0,1,s0).
rock(2,1,s0).

%%rock fluent axioms to move the rock they check if an agent can be in position I,J as a result of an action A in a previous situation S
%%the possible actions are either down, up, right, left

%%Predicate for rock to be in a position I,J as result of down in a previous situation S
rock(I, J, result(down, S)):-
  move_rock(I, J, 0, -1, S),
  N is J-1,
  rock(I,N,S).

%%Predicate for rock to be in a position I,J as result of up in a previous situation S
rock(I, J, result(up, S)):-
  move_rock(I, J, 0, 1, S),
  N is J+1,
  rock(I,N,S).

%%Predicate for rock to be in a position I,J as result of right in a previous situation S
rock(I, J, result(right, S)):-
  move_rock(I, J, -1, 0, S),
  N is I-1,
  rock(N,J,S).

%%Predicate for rock to be in a position I,J as result of left in a previous situation S
rock(I, J, result(left, S)):-
  move_rock(I, J, 1, 0, S),
  N is I+1,
  rock(N,J,S).

%%Predicate for rock PRESISTENCE to be in previous position I,J as result of a failed down action in a previous situation S
rock(I, J, result(down, S)):-
  \+ move_rock(I, J, 0, -1, S),
  rock(I,J,S).
  
%%Predicate for rock PRESISTENCE to be in previous position I,J as result of a failed up action in a previous situation S
rock(I, J, result(up, S)):-
  \+ move_rock(I, J, 0, 1, S),
  rock(I,J,S).

%%Predicate for rock PRESISTENCE to be in previous position I,J as result of a failed right action in a previous situation S
rock(I, J, result(right, S)):-
  \+ move_rock(I, J, -1, 0, S),
  rock(I,J,S).

%%Predicate for rock PRESISTENCE to be in previous position I,J as result of a failed left action in a previous situation S
rock(I, J, result(left, S)):-
  \+ move_rock(I, J, 1, 0, S),
  rock(I,J,S).
  
%%move_rock is used to check if the rock can be in the I,J given the previous state S
%%moreover this predicate takes two extra parameters K,L they are used to generalize the method where K represent change in I and L represent change in J

%%This is the case to check if the rock is moved to a cell I,J which was completely free in situation S and exists inside the grid and the rock in previous state was not on a pad
%%also it checks that an agent was just near the rock and did the same movement in order for the rock to move
move_rock(I, J, K, L, S):-
  N1 is I+K+K,
  N2 is J+L+L,
  agent(N1,N2,S),
  \+rock(I,J,S),
  \+obstacle(I,J),
  N3 is I+K,
  N4 is J+L,
  \+pad(N3,N4),
  w(I),
  h(J).
  
  
%% Query to run the file.
%% run((agent(1,1,S),rock(0,0,S),rock(2,0,S))).