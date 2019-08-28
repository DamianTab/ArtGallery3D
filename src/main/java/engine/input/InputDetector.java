package engine.input;


import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

//todo Damian to zrobi
public class InputDetector {

    private static double mouseX, mouseY;
    private static List<Boolean> keyboardInputList = new ArrayList();

    private static boolean firstLoad = true;


    public static Vector2f getMousePosition() {
        return new Vector2f().set((float) mouseX, (float) mouseY);
    }

    public static boolean isKeyPressed(int key) {
        return keyboardInputList.get(key);
    }

    public static void updateInputDevices(long window){
        loadAllKeys(window);
        loadMousePosition(window);
    }


    private static void loadAllKeys(long window) {
        if (firstLoad){
            for (int i = 0; i < 350; i++) {
                keyboardInputList.add(i, isKeyPressed(window, i));
            }
            firstLoad = false;
        }else{
            for (int i = 32; i < 348; i++) {
                keyboardInputList.set(i, isKeyPressed(window, i));
            }
        }

    }

    private static boolean isKeyPressed(long window, int key) {
        return glfwGetKey(window, key) == GLFW_PRESS;
    }

    private static void loadMousePosition(long window) {

        glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long win, double mouseX, double mouseY) {
                InputDetector.mouseX = mouseX;
                InputDetector.mouseY = mouseY;
            }
        });
    }

}
