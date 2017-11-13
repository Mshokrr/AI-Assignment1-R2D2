pad(2,0).

teleport(0,0).
obstacle(0,2).

w(0).
w(1).
w(2).

h(0).
h(1).
h(2).

run(Q):-
  run_helper(Q,3).

run_helper(Q, I):-
  call_with_depth_limit(Q,I,R),
  run_helper2(Q,I,R).
  
run_helper2(_, _, R):-
  R \= depth_limit_exceeded.

run_helper2(Q, I, R):-
  R == depth_limit_exceeded,
  I1 is I +1,
  run_helper(Q,I1).

agent(2,2,s0).

agent(I, J, result(down,S)):-
  move_agent(I,J,0,-1, S),
  N is J-1,
  agent(I,N,S).

agent(I, J, result(up,S)):-
  move_agent(I,J,0,1,S),
  N is J+1,
  agent(I,N,S).

agent(I, J, result(right,S)):-
  move_agent(I,J,-1,0,S),
  N is I-1,
  agent(N,J,S).

agent(I, J, result(left,S)):-
  move_agent(I,J,1,0,S),
  N is I+1,
  agent(N,J,S).

move_agent(I, J, _, _, S):-
  \+ rock(I,J,S),
  \+ obstacle(I,J),
  w(I),
  h(J).

move_agent(I, J, K, L, S):-
  N1 is I-K,
  N2 is J-L,
  rock(I,J,S),
  \+pad(I,J),
  \+rock(N1,N2, S),
  \+obstacle(N1,N2),
  w(N1),
  h(N2).

rock(2,1,s0).

rock(I, J, result(down, S)):-
  move_rock(I, J, 0, -1, S),
  N is J-1,
  rock(I,N,S).

rock(I, J, result(up, S)):-
  move_rock(I, J, 0, 1, S),
  N is J+1,
  rock(I,N,S).

rock(I, J, result(right, S)):-
  move_rock(I, J, -1, 0, S),
  N is I-1,
  rock(N,J,S).

rock(I, J, result(left, S)):-
  move_rock(I, J, 1, 0, S),
  N is I+2,
  rock(N,J,S).

rock(I, J, result(_, S)):-
  \+move_rock(I, J, 1, 0, S),
  \+move_rock(I, J, 11, 0, S),
  \+move_rock(I, J, 0, -1, S),
  \+move_rock(I, J, 0, 1, S),
  rock(I,J,S).
  
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

%%move_rock(I, J, K, L, S):-
  %%N1 is I-K,
  %%N2 is J-L,
  %%\+agent(N1,N2,S),
  %%rock(I,J,S).
