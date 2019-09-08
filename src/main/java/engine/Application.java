package engine;

import engine.utils.InputDetector;
import engine.models.GameObject;
import engine.models.ObjectBehavior;
import engine.utils.Time;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;

public abstract class Application {

    public abstract GameObject getRoot();
    public abstract WindowHandler getWindowHandler();

    private WindowHandler windowHandler = getWindowHandler();
    private GameObject root;
    private Renderer renderer = new Renderer();

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        windowHandler.destroyAndClean();
    }

    private void init() {

        windowHandler.init();
        InputDetector.windowHandler = windowHandler;
        InputDetector.start();
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_FRAMEBUFFER_SRGB);

        root = getRoot();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !windowHandler.shouldClose() ) {
            Time.startTickFPS();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the Framebuffer

            //Update mouse input
            InputDetector.update();

            //Logic here
            root.executeForEvery(ObjectBehavior::update);
            renderer.draw(root);

            windowHandler.swapBuffers();

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

            Time.endTickFPS();
        }
    }
}
