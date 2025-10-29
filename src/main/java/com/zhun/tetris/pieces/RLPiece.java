package com.zhun.tetris.pieces;


public class RLPiece extends Piece {
    public RLPiece() {
        coordinates = new int[4][2];
        coordinates[0] = new int[]{x+1, y};
        coordinates[1] = new int[]{x, y};
        coordinates[2] = new int[]{x-1, y};
        coordinates[3] = new int[]{x-1, y-1};

        shape = Shape.RL;
    }

    @Override
    public Piece copy() {return new RLPiece();}
}

