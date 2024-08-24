package info.sigmaclient.sigma.gui.games.snake;

import net.minecraft.util.math.vector.Vector2f;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Vector2f> pos = new ArrayList<>();
    public ArrayList<Vector2f> lastPos = new ArrayList<>();

    private snakeDir Dir;

    public Snake(Vector2f head, snakeDir dir) {
        this.pos.add(head);
        Dir = dir;
    }

    public ArrayList<Vector2f> getPos() {
        return pos;
    }

    public snakeDir getDir() {
        return Dir;
    }


    public void setDir(snakeDir dir) {
        Dir = dir;
    }

    public enum snakeDir{
        UP,DOWN,LEFT,RIGHT
    }
}
