package phase1;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class UniformCostSearch extends Search{

	public UniformCostSearch(Map m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateVertex(Node current, Node neighbor) {
		if(current.get_g() + current.cost(neighbor) < neighbor.get_g()){
			
			neighbor.set_g(current.get_g() + current.cost(neighbor));
			neighbor.setParent(current);
			
			//remove needed to update the fringe
			if(open.contains(neighbor))
				open.remove(neighbor);
				
			open.add(neighbor);

		}
		
	}

	@Override
	public void setupFringe(Comparator<Node> compare) {
		open = new PriorityQueue<Node>(size, compare);
		Node start = map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY());
		start.set_g(0);
		open.add(start);
		
	}
}