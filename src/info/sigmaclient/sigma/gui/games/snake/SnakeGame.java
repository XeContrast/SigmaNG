package info.sigmaclient.sigma.gui.games.snake;

import com.mojang.blaze3d.matrix.MatrixStack;
import info.sigmaclient.sigma.gui.font.JelloFontUtil;
import info.sigmaclient.sigma.utils.TimerUtil;
import info.sigmaclient.sigma.utils.key.ClickUtils;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.StencilUtil;
import info.sigmaclient.sigma.utils.render.blurs.RoundedRectShader;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static info.sigmaclient.sigma.minimap.XaeroMinimap.mc;

public class SnakeGame extends Screen {
    public Vector2f head = new Vector2f(0,0);
    public Snake snake = new Snake(head, Snake.snakeDir.RIGHT);
    private int speedTick = 0;
    private int num = 0;

    public SnakeGame() {
        super(ITextComponent.getTextComponentOrEmpty("SnakeGame"));
    }

    @Override
    protected void init() {
        super.init();
        speedTick = 0;
        num = 3;
        head = new Vector2f(mc.getMainWindow().getScaledWidth() / 2,mc.getMainWindow().getScaledHeight() / 2);
        snake = new Snake(head, Snake.snakeDir.RIGHT);
    }

    @Override
    public void tick() {
        super.tick();
        if(speedTick >= 2) {


            switch (snake.getDir()) {
                case UP -> snake.getPos().add(1, new Vector2f(head.x, head.y - 10));
                case DOWN -> snake.getPos().add(1, new Vector2f(head.x, head.y + 10));
                case LEFT -> snake.getPos().add(1, new Vector2f(head.x - 10, head.y));
                case RIGHT -> snake.getPos().add(1, new Vector2f(head.x + 10, head.y));
            }
            if(snake.getPos().size() > num * 3) {
                snake.getPos().remove(num * 3);
            }

            switch (snake.getDir()) {
                case UP -> head.y -= 10;
                case DOWN -> head.y += 10;
                case LEFT -> head.x -= 10;
                case RIGHT -> head.x += 10;
            }
            for (int i = snake.getPos().size()-1;i > 0;i--) {
                snake.getPos().set(i, snake.getPos().get(i-1));
            }
            snake.getPos().set(0,head);

            speedTick = 0;
        }

        if(ClickUtils.isClickable(mc.getMainWindow().getScaledWidth() / 2 - 190,mc.getMainWindow().getScaledHeight() / 2 - 100,mc.getMainWindow().getScaledWidth() / 2 + 190,
                mc.getMainWindow().getScaledHeight() / 2 + 100,snake.getPos().get(0).x,snake.getPos().get(0).y)){
            speedTick++;
        }else {
            gameOver();
        }


    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        JelloFontUtil.jelloFontBig.drawString("Snake",mc.getMainWindow().getScaledWidth() / 2 - 190,mc.getMainWindow().getScaledHeight() / 2 - 115 - JelloFontUtil.jelloFontBig.getHeight(),-1);
        RoundedRectShader.drawRound(mc.getMainWindow().getScaledWidth() / 2 - 200,mc.getMainWindow().getScaledHeight() / 2 - 110,400,220,10,new Color(252, 252, 252));
        RenderUtils.drawRect(mc.getMainWindow().getScaledWidth() / 2 - 190,mc.getMainWindow().getScaledHeight() / 2 - 100,mc.getMainWindow().getScaledWidth() / 2 + 190,mc.getMainWindow().getScaledHeight() / 2 + 100,new Color(0,0,0).getRGB());

        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(mc.getMainWindow().getScaledWidth() / 2 - 190,mc.getMainWindow().getScaledHeight() / 2 - 100,mc.getMainWindow().getScaledWidth() / 2 + 190,mc.getMainWindow().getScaledHeight() / 2 + 100,-1);
        StencilUtil.readStencilBuffer(1);
        for(Vector2f vector2f : snake.getPos()) {
            RenderUtils.drawRect(vector2f.x - 10,vector2f.y,vector2f.x,vector2f.y + 10,new Color(255, 255, 255).getRGB());
        }
        StencilUtil.uninitStencilBuffer();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        switch (keyCode){
            case GLFW.GLFW_KEY_W:
                if(snake.getDir() != Snake.snakeDir.DOWN) {
                    snake.setDir(Snake.snakeDir.UP);
                }
                break;
            case GLFW.GLFW_KEY_S:
                if(snake.getDir() != Snake.snakeDir.UP) {
                    snake.setDir(Snake.snakeDir.DOWN);
                }
                break;
            case GLFW.GLFW_KEY_A:
                if(snake.getDir() != Snake.snakeDir.RIGHT) {
                    snake.setDir(Snake.snakeDir.LEFT);
                }
                break;
            case GLFW.GLFW_KEY_D:
                if(snake.getDir() != Snake.snakeDir.LEFT) {
                    snake.setDir(Snake.snakeDir.RIGHT);
                }
                break;
            case GLFW.GLFW_KEY_SPACE:
                speedTick++;
                break;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void gameOver(){
        num = 3;
        head = new Vector2f(mc.getMainWindow().getScaledWidth() / 2,mc.getMainWindow().getScaledHeight() / 2);
        snake = new Snake(head, Snake.snakeDir.RIGHT);
    }
}
