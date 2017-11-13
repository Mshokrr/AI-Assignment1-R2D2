pad(3,1).

teleport(1,1).
obstacle(2,0).

w(0).
w(1).
w(2).
w(3).
h(0).
h(1).
h(2).
h(3).

at(I,J,S):-
  agent_helper(I,J,S,1).

not_inst(Var):-
  \+(\+(Var=0)),
  \+(\+(Var=1)).

agent_helper(I,J,S,M):-
  \+not_inst(S),
  agent(I,J,S,M).

agent_helper(I,J,S,M):-
  not_inst(S),
  agent(I,J,S,M),
  N is M+1,
  agent_helper(I,J,S,N).

agent(0,3,s0,_).

agent(I, J, result(down,S), M):-
  M > 0,
  move_agent(I,J,0,-1, S),
  N is J-1,
  M1 is M-1,
  agent(I,N,S,M1).

agent(I, J, result(up,S), M):-
  M > 0,
  move_agent(I,J,0,1,S),
  N is J+1,
  M1 is M-1,
  agent(I,N,S,M1).

agent(I, J, result(right,S), M):-
  M > 0,
  move_agent(I,J,-1,0,S),
  N is I-1,
  M1 is M-1,
  agent(N,J,S,M1).

agent(I, J, result(left,S), M):-
  M > 0,
  move_agent(I,J,1,0,S),
  N is I+1,
  M1 is M-1,
  agent(N,J,S,M1).

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
  \+w(N1),
  \+h(N2).

rock(0,1,s0).

%%rock(I, J, result(down, S)):-
  %%move_rock(I, J, 0, -2, S),
  %%N is J-1,
  %%rock(I,N,S).

%%rock(I, J, result(up, S)):-
  %%move_rock(I, J, 0, 2, S),
  %%N is J+1,
  %%rock(I,N,S).

%%rock(I, J, result(right, S)):-
  %%move_rock(I, J, -2, 0, S),
  %%N is I-1,
  %%rock(N,J,S).

%%rock(I, J, result(left, S)):-
%%  move_rock(I, J, 2, 0, S),
%%  N is I+1,
%%  rock(N,J,S).

move_rock(I, J, K, L, S):-
  N1 is I+K,
  N2 is J+L,
  agent(N1,N2,S,-1).

move_rock(I, J, K, L, S):-
  N1 is I+K,
  N2 is J+L,
  \+agent(N1,N2,S,-1),
  rock(I,J,S).