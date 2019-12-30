# ColorLines-Android
Сlassic color lines game for android phones
Color Lines Android
========================
About game
-------------------------
The game takes place on a square field of 9 × 9 cells and is a series of moves. 
Each turn, the computer first puts three balls of random colors into random cells, the last of which is only 7. 
Next, the player makes a move. He can move any ball into another free cell, 
but there must be a path from free cells between the initial and final cells. 
If after moving it turns out that five balls of the same color are collected in a horizontal, 
vertical and diagonal line, then all such balls (of which there may be more than 5) disappear and 
the player is given the opportunity to make another movement of the ball. 
If after moving the line it does not line up, then the move ends and a new one begins with the advent of new balls. 
If a line is collected when new balls appear, then it disappears, the player receives points, but no additional movement is given. 
The game continues until the entire field is filled with balls and the player can not make a move.
The goal of the game is to score maximum points. 
The account is arranged in such a way that when removing more balls than 5 in one move, the player receives significantly more points. 
During the game, the screen shows three colors of balls that will be thrown onto the field the next turn.
In custom mode, you can change the size of the field, 
the number of appearing balls, the length of the disappearing line, and the amount of color.

About code
-------------------------
All game logic be found in "ColorLines.java". This class provides a custom field size, a custom number of balls colors, 
a custom number of balls appearing, and a custom disappearing line length.

Class "GameSurfaceView" was created by me for games requiring a field. 
It allows you to fill a field cell with a circle or square of any size and any color.

The code provides for saving the game, continuing the game, creating a custom game.

Screenshots from the application
-------------------------
<img src="https://github.com/Evgeny268/ColorLines-Android/blob/master/example_screen/Screenshot_2019-11-02-23-42-44-372_com.evgendev.colorlines.png" width="150">
<img src="https://github.com/Evgeny268/ColorLines-Android/blob/master/example_screen/Screenshot_2019-11-02-23-47-02-262_com.evgendev.colorlines.png" width="150">
<img src="https://github.com/Evgeny268/ColorLines-Android/blob/master/example_screen/Screenshot_2019-11-02-23-49-07-606_com.evgendev.colorlines.png" width="150">
<img src="https://github.com/Evgeny268/ColorLines-Android/blob/master/example_screen/Screenshot_2019-11-02-23-49-44-347_com.evgendev.colorlines.png" width="150">
<img src="https://github.com/Evgeny268/ColorLines-Android/blob/master/example_screen/Screenshot_2019-11-02-23-50-13-159_com.evgendev.colorlines.png" width="150">

Google Play
-------------------------
https://play.google.com/store/apps/details?id=com.evgendev.colorlines&hl
