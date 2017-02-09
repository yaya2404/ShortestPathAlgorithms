package phase2;

import phase1.Map;
import phase1.Node;

public abstract class Search {

	
	Map map;
	int goalX;
	int goalY;
	
	public Search(Map m){
		map = m;
		goalX = map.getEndCoordinate().getX();
		goalY = map.getEndCoordinate().getY();
	}
	
	
	//returns g(s) + w1 * h(s)
	public int getKey(Node s, int i){
		return 0;
	}
	
	public abstract void expandState(Node s, int i);
	public abstract void setupFringe(int i);
	
	public boolean findPath(){
		
	}
}
