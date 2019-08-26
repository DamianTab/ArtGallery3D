package engine.models;


import engine.components.Transform;
import lombok.Setter;

public abstract class Component {

    public enum Type {
        TRANSFORM, MESH_FILTER, MESH_RENDERER, COLLIDER, LIGHT_SOURCE, CAMERA
    }

    protected abstract Type getType();

    public Transform getTransform() {
        return gameObject.getTransform();
    }

    @Setter
    protected GameObject gameObject;
}
