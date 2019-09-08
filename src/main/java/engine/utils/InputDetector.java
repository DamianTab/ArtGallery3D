package engine.utils;


import engine.WindowHandler;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

//todo Damian to zrobi // Damian zrobił to źle

public class InputDetector {

    private static double mouseX, mouseY;
    public static WindowHandler windowHandler;

    public static Vector2f getMousePosition() {
        return new Vector2f((float) mouseX, (float) mouseY);
    }

    public static void start() {
        glfwSetInputMode(windowHandler.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public static void update(){
        loadMousePosition();
    }

    public static boolean isKeyPressed(int key) {
        return glfwGetKey(windowHandler.getWindow(), key) == GLFW_PRESS;
    }

    private static void loadMousePosition() {

        glfwSetCursorPosCallback(windowHandler.getWindow(), new GLFWCursorPosCallback() {
            @Override
            public void invoke(long win, double mouseX, double mouseY) {
                InputDetector.mouseX = mouseX;
                InputDetector.mouseY = mouseY;
            }
        });
    }


}
