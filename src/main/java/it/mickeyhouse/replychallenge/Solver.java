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
        Position nextPosition;

        while (!inputData.getPersonQueue().isEmpty()) {
            Edge edge = (Edge) inputData.getPersonQueue().poll();

            if (edge.a.isPlaced() && edge.b.isPlaced())
                continue;

            if (!edge.a.isPlaced() && edge.b.isPlaced()) {
                char type;
                if (edge.a instanceof Manager)
                    type = 'M';
                else
                    type = '_';
                Position p = getNextFreePosition(new Position(edge.b.getXPosition(), edge.b.getYPosition()), type);
                if (p == null)
                    continue;
                edge.a.setXPosition(p.getX());
                edge.a.setYPosition(p.getY());
                inputData.getFloor()[p.getY()][p.getX()].setPerson(edge.a);
                edge.a.setPlaced(true);
            }

            if (edge.a.isPlaced() && !edge.b.isPlaced()) {
                char type;
                if (edge.b instanceof Manager)
                    type = 'M';
                else
                    type = '_';
                Position p = getNextFreePosition(new Position(edge.a.getXPosition(), edge.a.getYPosition()), type);
                if (p == null)
                    continue;
                edge.b.setXPosition(p.getX());
                edge.b.setYPosition(p.getY());
                inputData.getFloor()[p.getY()][p.getX()].setPerson(edge.a);
                edge.b.setPlaced(true);
            }

            if (!edge.a.isPlaced() && !edge.b.isPlaced()) {
                initPositions = getFirstTwoFreePlace();
                // assegno alle due persone le initPositions
                edge.a.setXPosition(initPositions[0].getX());
                edge.a.setYPosition(initPositions[0].getY());
                inputData.getFloor()[initPositions[0].getY()][initPositions[0].getX()].setPerson(edge.a);
                edge.a.setPlaced(true);

                edge.b.setXPosition(initPositions[1].getX());
                edge.b.setYPosition(initPositions[1].getY());
                inputData.getFloor()[initPositions[1].getY()][initPositions[1].getX()].setPerson(edge.b);
                edge.b.setPlaced(true);
            }

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


    private Position getNextFreePosition(Position position, char type) {

        int x = position.getX();
        int y = position.getY();

        if (x + 1 < width)
            if (inputData.getFloor()[x+1][y].getType() == type && inputData.getFloor()[x+1][y].getPerson() == null)
                return new Position(x+1,y);

        if (y + 1 < height)
            if (inputData.getFloor()[x+1][y].getType() == type && inputData.getFloor()[x][y+1].getPerson() == null)
                return new Position(x,y+1);

        if (x - 1 > 0)
            if (inputData.getFloor()[x+1][y].getType() == type && inputData.getFloor()[x-1][y].getPerson() == null)
                return new Position(x-1,y);

        if (y - 1 > 0)
            if (inputData.getFloor()[x+1][y].getType() == type && inputData.getFloor()[x-1][y].getPerson() == null)
                return new Position(x,y-1);

        return null;
    }

}
