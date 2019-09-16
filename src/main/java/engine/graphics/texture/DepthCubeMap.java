package engine.graphics.texture;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;

//Not used
public class DepthCubeMap extends Texture {

    private int textureUnit;

    public DepthCubeMap(int textureUnit) {
        this.textureUnit = textureUnit;
        init();
    }

    @Override
    public int getHeight() {
        return 1024;
    }

    @Override
    public int getWidth() {
        return 1024;
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, id);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }

    @Override
    public int getTextureUnit() {
        return 5 + textureUnit;
    }

    protected void init() {
        super.init();
        bind();
        for(int i = 0; i < 6; i++) {
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_DEPTH_COMPONENT,
                    getWidth(), getHeight(), 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer)null);
        }
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
    }
}
