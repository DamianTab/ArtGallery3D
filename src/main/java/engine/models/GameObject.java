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
        start();
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

    // Execute a method for every gameObject in the tree
    public void executeForEvery(Callable callable) {
        for(GameObject gameObject : children) {
            gameObject.executeForEvery(callable);
        }
        callable.call(this);
    }

    public interface FindCondition {
        boolean check(GameObject gameObject);
    }

    // Look for a GameObject with a specified condition
    public GameObject find(FindCondition findCondition) {
        if(findCondition.check(this)) {
            return this;
        }
        for(GameObject gameObject : children) {
            GameObject f = gameObject.find(findCondition);
            if(f != null) {
                return f;
            }
        }
        return null;
    }

}
