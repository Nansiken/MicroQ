-module(client_state).
-export([init/1, listener/3]).

-include_lib("eunit/include/eunit.hrl").

%%
%% @doc The listener function receives messages from and answers back to jcom (the parent process) and calls itself recursively.
listener(PID, Waiting, State) ->
	receive
		ready ->
			if 
				State =:= ready ->
					io:format("~p I am ready~n", [self()]),
					PID ! {client_ready, self()},
					listener(PID, false, ready);
				true -> 
					io:format("~p I am not ready yet~n", [self()]),
					listener(PID, true, State)
			end;
		
		switch ->
			if 
				Waiting =:= true ->
					io:format("~p I am back from whatever I did and I am ready~n", [self()]),
					PID ! {client_ready, self()},
					listener(PID, false, ready);
			true -> ok
			end,
			if 
				State =:= ready ->
					io:format("~p Switching to state 'away'~n", [self()]),
					listener(PID, false, away);	
				true ->
					io:format("~p I'm back!~n", [self()]),
					listener(PID, false, ready)
			end
	end.

%%
%% @doc The init function sends "client_initialized_successfully" to jcom (the parent process) if the child process started successfully, then calls function listener.
init(PID) ->
	process_flag(trap_exit, true),
	PID ! client_initialized_successfully,
	listener(PID, false, ready).

	




%% ------------------ Eunit Tests ----------------------
   
init_test() ->
    ?assertMatch(tbi, tbi).

listener_test() ->
    ?assertMatch(tbi, tbi).
	
	

	

