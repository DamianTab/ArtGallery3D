package engine.components;

import engine.models.Component;
import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Transform extends Component {

    @Getter
    private Vector3f position = new Vector3f();
    @Getter
    private Vector3f rotation = new Vector3f();
    @Getter
    private Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
    private Matrix4f matrix;
    @Getter
    @Setter
    private Transform parent;
    private boolean dirty = true;

    public Transform() {
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        dirty = true;
    }

    public void shiftBy(Vector3f position) {
        Vector3f result = new Vector3f();
        getPosition().add(position, result);
        setPosition(result);
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
        dirty = true;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
        dirty = true;
    }

    public void setScale(float scalar) {
        setScale(new Vector3f(scalar, scalar, scalar));
    }

    private Matrix4f generateMatrix(){
        Matrix4f matrix = new Matrix4f().identity();
        matrix.translate(position);
        matrix.rotateXYZ(rotation);
        matrix.scale(scale);
        return matrix;
    }

    public Matrix4f getMatrix() {
        if(dirty) {
            matrix = generateMatrix();
            dirty = true;
        }
        return matrix;
    }

    // Get transform matrix relative to the world
    public Matrix4f getAbsoluteMatrix() {
        if(parent != null) {
            Matrix4f result = new Matrix4f();
            parent.getAbsoluteMatrix().mul(getMatrix(), result);
            return result;
        }
        else {
            return getMatrix();
        }
    }

    // Get position relative to the world
    public Vector3f getAbsolutePosition() {
        if(parent == null) {
            return position;
        }
        else {
            Vector4f v = new Vector4f(position, 1.0f).mul(parent.getAbsoluteMatrix());
            return new Vector3f(v.x, v.y, v.z).div(v.w);
        }
    }

    // Get matrix relative to the transform p
    public Matrix4f getRelativeMatrix(Transform p) {
        if(parent == null) {
            return null;
        }
        else if(parent == p) {
            return getMatrix();
        }
        else {
            Matrix4f result = new Matrix4f();
            parent.getRelativeMatrix(p).mul(getMatrix(), result);
            return result;
        }
    }


    @Override
    protected Type getType() {
        return Type.TRANSFORM;
    }
}
