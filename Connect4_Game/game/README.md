# Connect 4 Game 

Get 4 in a line to win

#### To Play --> 
	gradle run --console=plain -q


## Example 1:

Enter ‘P’ if you want to play against another player; enter ‘C’ to play against computer.
>> c

Enter ‘C’ if you want to play on the console; enter ‘G’ to play on GUI.
>> g

This will cause a single GUI game against the computer begin.

## Example 2:

Enter ‘P’ if you want to play against another player; enter ‘C’ to play against computer.
>> p

Enter ‘O’ if you want to play online; enter ‘L’ for local.
>> o

Please make sure server is running
Enter ‘C’ if you want to play on the console; enter ‘G’ to play on GUI.
>> g

This will create a single GUI game that connects to the server. If there is a player waiting to play the server will set you up with them to play the game otherwise you will wait till another play connects.


## Online games:

The Connect4Client class has the host set to 'localhost' you will need to change this to the servers IP address.