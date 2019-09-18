package engine.graphics.texture;

import engine.graphics.shader.ShaderProgram;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUniform1i;

public abstract class Texture {
    @Getter
    protected int id;
    public abstract int getHeight();
    public abstract int getWidth();
    public abstract void bind();
    public abstract void unbind();
    public abstract int getTextureUnit();

    protected void init() {
        activateTextureUnit();
        id = glGenTextures();
    }

    protected void activateTextureUnit() {
        glActiveTexture(GL_TEXTURE0 + getTextureUnit());
    }

    public void use(ShaderProgram program, String location) {
        glUniform1i(program.getLocation(location),  getTextureUnit());
        activateTextureUnit();
        bind();
    }

}
