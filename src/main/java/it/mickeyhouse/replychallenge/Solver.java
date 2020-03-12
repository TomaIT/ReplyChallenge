package it.mickeyhouse.replychallenge;

import javafx.geometry.Pos;
import sun.font.CompositeStrike;

public class Solver {

    private InputData inputData;
    private int width;
    private int height;

    public Solver(InputData inputData, int width, int height) {
        this.inputData = inputData;
        this.width = width;
        this.height = height;
    }

    public void solve() {


        Position[] initPositions;

        while((initPositions = getFirstTwoFreePlace()) != null) {
            // ottengo il primo valore della coda a priorità

            // assegno alle due persone le initPositions

            // ottengo posizione vicina libera


        }
    }

    private Position[] getFirstTwoFreePlace() {
        Position[] positions = new Position[2];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (inputData.getFloor()[i][j].getPerson() != null)
                    continue;
                if (i+1 < width) {
                    if (inputData.getFloor()[i+1][j].getPerson() == null) {
                        positions[0].setX(j);
                        positions[0].setY(i);
                        positions[0].setX(j);
                        positions[1].setY(i+1);
                        return positions;
                    }
                }
                if (j+1 < height) {
                    if (inputData.getFloor()[i][j+1].getPerson() == null) {
                        positions[0].setX(j);
                        positions[0].setY(i);
                        positions[1].setX(j+1);
                        positions[1].setY(i);
                        return positions;
                    }
                }
            }
        }
        return null;
    }


    private Position getNextFreePosition(Position position) {

        int x = position.getX();
        int y = position.getY();

        if (x + 1 < width)
            if (inputData.getFloor()[x+1][y].getPerson() == null)
                return new Position(x+1,y);

        if (y + 1 < height)
            if (inputData.getFloor()[x][y+1].getPerson() == null)
                return new Position(x,y+1);

        if (x - 1 > 0)
            if (inputData.getFloor()[x-1][y].getPerson() == null)
                return new Position(x-1,y);

        if (y - 1 > 0)
            if (inputData.getFloor()[x-1][y].getPerson() == null)
                return new Position(x,y-1);

        return null;
    }

}
