package engine.components;

import engine.models.Component;
import engine.models.GameObject;
import lombok.AllArgsConstructor;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Transform extends Component {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private Matrix4f matrix;
    private Transform parent;
    private boolean dirty = true;

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale, Transform parent) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.parent = parent;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        dirty = true;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
        dirty = true;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
        dirty = true;
    }

    private Matrix4f generateMatrix(){
        Matrix4f matrix = new Matrix4f().identity();
        matrix.rotateXYZ(rotation);
        matrix.scale(scale);
        matrix.transform(new Vector4f(position, 1.0f));
        return generateMatrix();
    }

    public Matrix4f getMatrix() {
        if(dirty) {
            matrix = generateMatrix();
            dirty = false;
        }
        return matrix;
    }

    public Matrix4f getAbsoluteMatrix() {
        return parent.getMatrix().mul(getMatrix());
    }

    public Vector3f getAbsolutePosition() {
        Vector4f v = new Vector4f(position, 1.0f).mul(getAbsoluteMatrix());
        return new Vector3f(v.x, v.y, v.z).div(v.w);
    }

    @Override
    protected Type getType() {
        return Type.TRANSFORM;
    }
}
