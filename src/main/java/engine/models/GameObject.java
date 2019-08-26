package engine.models;

import engine.components.Transform;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public abstract class GameObject implements ObjectBehavior {

    List <GameObject> children = new ArrayList<>();
    List <Component> components = new ArrayList<>();

    public GameObject() {
        addComponent(new Transform());
    }


    public Component getComponent(Component.Type type){
        for(Component component : components) {
            if(type == component.getType()) {
                return component;
            }
        }
        return null;
    }

    public void addChild(GameObject child) {
        child.getTransform().setParent(getTransform());
        children.add(child);
    }

    public void addComponent(Component component) {
        component.setGameObject(this);
        components.add(component);
    }

    public Transform getTransform() {
        return (Transform)getComponent(Component.Type.TRANSFORM);
    }



    public interface Callable {
        void call(GameObject gameObject);
    }

    public void executeForEvery(Callable callable) {
        for(GameObject gameObject : children) {
            gameObject.executeForEvery(callable);
        }
        callable.call(this);
    }

}
