package engine.models;


import lombok.Setter;

public abstract class Component {

    public enum Type {
        TRANSFORM, MESH_FILTER, MESH_RENDERER, COLLIDER, LIGHT_SOURCE, CAMERA
    }

    protected abstract Type getType();

    @Setter
    protected GameObject gameObject;
}
