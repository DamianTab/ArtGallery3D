package engine.graphics.texture;

import de.matthiasmann.twl.utils.PNGDecoder;
import engine.graphics.shader.ShaderProgram;
import lombok.Getter;

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
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class FileTexture extends Texture{

    @Getter
    private Type type;
    @Getter
    private String path;
    private int width;
    private int height;

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public int getTextureUnit() {
        return type.ordinal();
    }

    //The type will determine which texture unit to use
    public enum Type {
        AMBIENT, DIFFUSE, SPECULAR, NORMAL
    }

    public FileTexture(String path, Type type) throws IOException {
        this.path = path;
        this.type = type;
        initFileTexture();
    }

    public void use(ShaderProgram program) {
        use(program, getLocationID());
    }

    private void initFileTexture() throws IOException {
        super.init();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        // We use PNGDecoder to decode png file to BGRA format.
        PNGDecoder decoder = null;
        ByteBuffer buf = null;
        try {
            decoder = new PNGDecoder(inputStream);
            buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, getDecoderFormat());
            buf.flip();
        }
        catch (NullPointerException e) {
            System.err.println("Invalid format: "  + path);
        }
        width = decoder.getWidth();
        height = decoder.getHeight();

        bind();
        // We use mipmap for performace
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        // FileTexture will repeat when out of bounds
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
        glTexImage2D(GL_TEXTURE_2D, 0, getInternalFormat(type), width, height, 0, GL_BGRA, GL_UNSIGNED_BYTE, buf);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    private int getInternalFormat(Type type) {
        // If this is a normalMapping texture then use gamma correlation
        if(type == Type.DIFFUSE) {
            return GL_SRGB8_ALPHA8;
        }
        // Else use normalMapping formal
        else {
            return GL_RGBA;
        }
    }

    // Different textures have different internal format
    private PNGDecoder.Format getDecoderFormat() {
        switch (type) {
            case AMBIENT:
                return PNGDecoder.Format.ALPHA;
            case DIFFUSE:
                return PNGDecoder.Format.BGRA;
            case SPECULAR:
                return PNGDecoder.Format.BGRA;
            case NORMAL:
                return PNGDecoder.Format.BGRA;
        }
        return null;
    }

    // Identifier in shader
    private String getLocationID() {
        switch (type) {
            case AMBIENT:
                return "material.ambientMap";
            case DIFFUSE:
                return "material.diffuseMap";
            case SPECULAR:
                return "material.specularMap";
            case NORMAL:
                return "material.normalMap";
        }
        return null;
    }

}
