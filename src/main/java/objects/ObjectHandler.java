package objects;

import java.util.ArrayList;

public class ObjectHandler {

private ArrayList<GameObject> gameObjects;


public void updateObjects(){
	for(GameObject gameObject: gameObjects)
		gameObject.update();
}

public void renderObjects(){
	for(GameObject gameObject: gameObjects)
		gameObject.render();
}

	
}
