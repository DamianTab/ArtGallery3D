package engine.graphics;

import static org.lwjgl.opengl.GL30.*;

//Not used
public class Framebuffer {
    private int id;

    private void init() {
        id = glGenFramebuffers();
    }

    public void attach(int textureID) {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, textureID, 0);
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
}
