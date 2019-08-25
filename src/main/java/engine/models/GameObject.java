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

    }


    public <T> Component getComponent(Class<T> t){
        for(Component component : components) {
            if(t.isAssignableFrom(component.getClass())) {
                return  component;
            }
        }
        return null;
    }

    public void addComponent(Component component) {
        components.add(component);
    }


}
