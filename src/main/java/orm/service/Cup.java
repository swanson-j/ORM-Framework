package orm.service;

import orm.annotations.Column;

public class Cup {
    private String color;
    private int height;

    public Cup(String color, int height) {
        this.color = color;
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
