# 2048Fx


The project developed using **IntelliJ**

**Spanish**

Juego 2048 basado en el juego de [gabrielecirulli](https://gabrielecirulli.github.io/2048/), completamente re-escrito a JavaFX, el código aquí no fue copy-paste de otro projecto asi que probablemente puede que contenga algunos bugs ó en su defecto algunas malas practicas.


El projecto esta dirigido para animar la creación/uso de inteligencia artificial ó desarrollo de algoritmos que controle el juego y sea fácil de implementar y usar en cualquier proyecto Java, así sean desarrolladores con experiencia ó estudiantes.


**English**

Game 2048 based on the game of [gabrielecirulli](https://gabrielecirulli.github.io/2048/), completely rewritten to JavaFX, the code here was not copy-paste of another project so it probably contains some bugs or failing some bad practices.


The project is aimed to encourage the creation / use of artificial intelligence or development of algorithms that control the game and is easy to implement and use in any Java project, whether they are experienced developers or students.



```java 

//JavaFX Application - MainFX.java
@Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("2048");
        primaryStage.setScene(Integrator.createScene(new Board()));
        primaryStage.show();
    }
```

**Java - Swing** -> MainSwing.java 


Examples:

```java

//Mover el tablero - Move the board
board.move(Move.RIGHT);

//Nuevo juego - New Game
board.newGame();

//Regresar al movimiento anterior - Back to the future! (not the same random tile will be added)
board.reverse();
```


[logo]: https://github.com/d0tplist/2048Fx/raw/master/screen.png "Example"
