package engine.graphics.texture;

import de.matthiasmann.twl.utils.PNGDecoder;
import engine.graphics.shader.ShaderProgram;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL21.GL_SRGB8_ALPHA8;

public class Texture {

    private int id;
    private Type type;
    private String path;

    //The type will determine which texture unit to use
    public enum Type {
        AMBIENT, DIFFUSE, SPECULAR
    }

    public Texture(String path, Type type) throws IOException {
        this.path = path;
        this.type = type;
        init();
    }

    public void use(ShaderProgram program) {
        activateTextureUnit();
        glUniform1i(program.getLocation(getLocationID()), type.ordinal());
        glBindTexture(GL_TEXTURE_2D, id);
    }

    private void init() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        PNGDecoder decoder = new PNGDecoder(inputStream);
        ByteBuffer buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
        System.out.println(path);
        decoder.decode(buf, decoder.getWidth()*4, getDecoderFormat());
        buf.flip();

        activateTextureUnit();
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
        glTexImage2D(GL_TEXTURE_2D, 0, getInternalFormat(type), decoder.getWidth(), decoder.getHeight(), 0, GL_BGRA, GL_UNSIGNED_BYTE, buf);
    }

    private void activateTextureUnit() {
        glActiveTexture(GL_TEXTURE0 + type.ordinal());
    }

    private int getInternalFormat(Type type) {
        // If this is a normal texture then use gamma correlation
        if(type == Type.DIFFUSE) {
            return GL_SRGB8_ALPHA8;
        }
        // Else use normal formal
        else {
            return GL_RGBA;
        }
    }

    private PNGDecoder.Format getDecoderFormat() {
        switch (type) {
            case AMBIENT:
                return PNGDecoder.Format.ALPHA;
            case DIFFUSE:
                return PNGDecoder.Format.RGBA;
            case SPECULAR:
                return PNGDecoder.Format.RGBA;
        }
        return null;
    }

    private String getLocationID() {
        switch (type) {
            case AMBIENT:
                return "material.ambient";
            case DIFFUSE:
                return "material.diffuse";
            case SPECULAR:
                return "material.specular";
        }
        return null;
    }

}
