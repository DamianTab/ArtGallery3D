package artgellary;

import engine.Application;
import engine.models.GameObject;

public class ArtGallery  extends Application {
    @Override
    public GameObject getRoot() {
        return new ArtGalleryRootObject();
    }
}
