package engine.graphics;

import engine.graphics.texture.Texture;
import lombok.Getter;

import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_NONE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDrawBuffer;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glReadBuffer;
import static org.lwjgl.opengl.GL30.glViewport;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

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

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
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
