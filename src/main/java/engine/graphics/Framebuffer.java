package engine.graphics;

import engine.graphics.texture.Texture;
import lombok.Getter;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

//Not used
public class Framebuffer {
    private int id;
    @Getter
    private Texture texture;

    public Framebuffer(Texture texture) {
        this.texture = texture;
        init();
    }

    private void init() {
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, texture.getId(), 0);
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            System.err.println("Framebuffer not finished!");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void setViewPort() {
        glViewport(0, 0, texture.getWidth(), texture.getHeight());
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
}
