%% @author Simon Evertsson

%% @doc The communication module which acts like a bridge between the state processes and Java.

-module(jcom).
-export([communicator/2, init/0, tester/0, loop/1]).

%%-include("fruit.hrl").
-include_lib("eunit/include/eunit.hrl").

%% @doc Spawns a new state process and returns the PID
spawner(Switch_rate) ->
	PID = spawn_link(client_state, init, [self()]),
	spawn_link(state_switcher, init, [Switch_rate, PID]),
	PID.

%% @doc The communication function which sends and recieves message from its child processes and Java
communicator(Pid_list, Java_node) ->
	receive
		{new_client, Switch_rate} ->
			io:format("EN NYTT BARN HAR SKAPATS!!!!~n"),
			PID = spawner(Switch_rate),
			{mbox, Java_node} ! {new_client, PID},
			Updated_pid_list = [PID| Pid_list],
			communicator(Updated_pid_list, Java_node);
		{ready, PID} ->
			PID ! ready,
			communicator(Pid_list, Java_node);
		{client_ready, PID} ->
			{mbox, Java_node} ! {client_ready, PID},
			communicator(Pid_list, Java_node)
	end.


%% @doc Initializes communication with Java.
init() ->
	io:format("inside jcom:init()~n"),
	process_flag(trap_exit, true),
	%%net_kernel:start([erlcom]),
	Java_node = list_to_atom(lists:append("javacom@", net_adm:localhost())),
	loop(Java_node),
	{mbox, Java_node} ! self(),
	communicator([], Java_node).

loop(J_Node) ->
	Result = net_adm:ping(J_Node),
	if 
		Result =/= pong ->
			timer:sleep(1000),
			loop(J_Node);
		true ->
			hej
	end.
	
tester() ->
	init(),
	receive
		true -> hej
	end.
	

%% ------------------ Eunit Tests ----------------------
   
init_test() ->
    ?assertMatch(pong, init()).
	
spawner_test() ->
    ?assertMatch(tbi, tbi).
	
communicator_test() ->
    ?assertMatch(tbi, tbi).