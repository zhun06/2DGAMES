package com.zhun.util;

// Coordinate system
public class Point {
    public int x, y;

    // Constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point copy() {
        return new Point(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;
        Point p = (Point)o;
        return x == p.x && y == p.y;
    }
}
