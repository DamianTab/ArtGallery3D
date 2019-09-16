package artgellary;

import engine.Application;
import engine.WindowHandler;
import engine.models.GameObject;

public class ArtGallery  extends Application {
    @Override
    public GameObject getRoot() {
        return new ArtGalleryRootObject();
    }

    @Override
    public WindowHandler getWindowHandler() {
        return new WindowHandler("Art Gallery 3D", 1800, 1000);
    }
}
