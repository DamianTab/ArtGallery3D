package engine.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

// A simple helper to convert primitive types to FloatBuffer
public class FloatBufferUtils {
    public static FloatBuffer vector3ToFloatBuffer(Vector3f v) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(3);
        v.get(floatBuffer);
        return floatBuffer;
    }

    public static FloatBuffer matrix4ToFloatBuffer(Matrix4f m) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        m.get(floatBuffer);
        return floatBuffer;
    }
}
