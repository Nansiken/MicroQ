%% @author Simon Evertsson

%% @doc A simple module which sends messages to a client_state-process when it should switch its state.

-module(state_switcher).
-export([init/2]).

-include_lib("eunit/include/eunit.hrl").

%% @doc init - Initializes the state switcher
init(Switch_rate, PID) ->
	random:seed(erlang:now()),
    loop(Switch_rate, PID).


%% @doc The switch loop
loop(Switch_rate, PID) ->
	Rand = random:uniform(1000),
	if 
		Rand >= Switch_rate ->
			PID ! switch,
			%%io:format("I will now switch and sleep!");
			timer:sleep(1000 + random:uniform(4000));
		true ->
			loop(Switch_rate, PID)
	end,
	loop(Switch_rate, PID).

%% ------------------ Eunit Tests ----------------------
   
init_test() ->
    ?assertMatch(tbi, tbi).
	
loop_test() ->
    ?assertMatch(tbi, tbi).