package engine.models;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public abstract class GameObject implements ObjectBehavior {

    List <GameObject> children = new ArrayList<>();
    Map <String, Component> components = new HashMap<>();

    public Component getComponent(String name){
        return components.get(name);
    }


}
