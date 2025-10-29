package com.zhun.tetris.pieces;

import java.util.Random;

public class PieceFactory {
    private final Random random = new Random();

    public Piece createPiece() {
        switch (random.nextInt(7)) {
            case 0 -> {return new LPiece();}
            case 1 -> {return new RLPiece();}
            case 2 -> {return new ZPiece();}
            case 3 -> {return new RZPiece();}
            case 4 -> {return new TPiece();}
            case 5 -> {return new IPiece();}
            case 6 -> {return new OPiece();}
        }
        return new LPiece(); // Fallback
    }
}
