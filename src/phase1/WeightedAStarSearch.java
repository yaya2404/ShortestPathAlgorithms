package phase1;

import java.util.Comparator;
import java.util.PriorityQueue;

public class WeightedAStarSearch extends Search {
	
	private double weight;

	public WeightedAStarSearch(Map m, double w) {
		super(m);
		weight = w;
		// TODO Auto-generated constructor stub
	}
	
	public void setWeight(double w){
		weight = w;
	}

	@Override
	public void updateVertex(Node current, Node neighbor){
		if(current.get_g() + current.cost(neighbor) < neighbor.get_g()){
			
			neighbor.set_g(current.get_g() + current.cost(neighbor));
			//neighbor.calculate_h(goalX, goalY);
			neighbor.update_f(weight);
			neighbor.setParent(current);
			
			//remove needed to update the fringe
			if(open.contains(neighbor))
				open.remove(neighbor);
				
			open.add(neighbor);
		}
	}

	@Override
	public void setupFringe(Comparator<Node> compare) {
		open = new PriorityQueue<Node>(size, new Node.NodeComparator());
		Node start = map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY());
		start.set_g(0);
		//start.calculate_h(goalX, goalY);
		start.update_f(weight);
		open.add(start);
		
	}
	
	

}
