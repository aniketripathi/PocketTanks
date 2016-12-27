package objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ObjectHandler {

	// game objects that need to be updated and drawn
private ArrayList<GameObject> gameUpdateObjects;
private ArrayList<GameObject> gameRenderObjects;

public ObjectHandler(){
		gameUpdateObjects = new ArrayList<GameObject>();
		gameRenderObjects = new ArrayList<GameObject>();
	}

	


public void updateObjects(){
	for(int i = 0; i <gameUpdateObjects.size(); i++ )
		gameUpdateObjects.get(i).update(this);
}

public void renderObjects(Graphics2D graphics){
	for(int i = 0; i <gameRenderObjects.size(); i++ )
		gameRenderObjects.get(i).render(graphics);
}




public void addGameUpdateObject(GameObject object){
	gameUpdateObjects.add(object);
}

public void deleteGameUpdateObject(GameObject object){
	gameUpdateObjects.remove(object);
}

public void addGameRenderObject(GameObject object){
	gameRenderObjects.add(object);
}

public void deleteGameRenderObject(GameObject object){
	gameRenderObjects.remove(object);
}


public ArrayList<GameObject> getGameUpdateObjects(){
	return gameUpdateObjects;
}

public ArrayList<GameObject> getGameRenderObjects(){
	return gameRenderObjects;
}


}