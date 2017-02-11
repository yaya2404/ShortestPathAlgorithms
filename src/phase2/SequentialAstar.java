package phase2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import phase1.Map;
import phase1.Node;

public class SequentialAstar extends Search {

	ArrayList<PriorityQueue<Node>> openList; 
	ArrayList<HashSet<Node>> closedList;
	
	//ArrayList<Double> gStart;
	//ArrayList<Double> gEnd;
	//ArrayList<ArrayList<Node>> bpList;
	
	public SequentialAstar(Map m, Double weight1, Double weight2, int hueristics) {
		super(m, weight1, weight2, hueristics);
		// TODO Auto-generated constructor stub
	}
	
	
	public void expandState(Node s, int index) {
		// TODO Auto-generated method stub
		PriorityQueue<Node> open = openList.get(index);
		HashSet<Node> close = closedList.get(index);
		open.remove(s);
		
		for (Node neighbor : this.findSuccessorSet(s)) {
			if(!open.contains(neighbor) && !close.contains(neighbor)){
				neighbor.set_sG(Double.POSITIVE_INFINITY, index);
				neighbor.set_sBp(null, index);
				
				//was not in pseudocode
				open.add(neighbor);
			}else if(neighbor.get_sG(index) > s.get_sG(index) + s.cost(neighbor)){
				
				neighbor.set_sG(s.get_sG(index) + s.cost(neighbor), index);
				neighbor.set_sBp(s, index);
				if(!close.contains(neighbor)){
					neighbor.setKey(getKey(neighbor, index));
					open.add(neighbor);
				}
			}
		}
		
	}

	@Override
	public void setupFringe() {
		// TODO Auto-generated method stub
		
		Node start = map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY());
		Node goal = map.getCell(goalX, goalY);
		
		this.openList = new ArrayList<PriorityQueue<Node>>();
		this.closedList = new ArrayList<HashSet<Node>>();
		
		//this.gStart = new ArrayList<Double>();
		//this.gEnd = new ArrayList<Double>();
		//this.bpList = new ArrayList<ArrayList<Node>>();
		
		
		for(int count = 0; count < numberOfHueristics; count++){
			PriorityQueue<Node> open = new PriorityQueue<Node>(new Node.NodeKeyComparator());
			HashSet<Node> close = new HashSet<Node>();
			
			start.set_sG(0.0, count);
			start.set_sBp(null, count);
			
			goal.set_sG(Double.POSITIVE_INFINITY, count);
			goal.set_sBp(null, count);
			
			//not sure if this is the correct way to do it
			start.setKey(getKey(start, count));
			open.add(start);
			
			openList.add(open);
			closedList.add(close);
		}
	}

	@Override
	public boolean findPath() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNumOfExpandedNodes() {
		// TODO Auto-generated method stub
		
		int num = 0;
		
		for(int count = 0; count < numberOfHueristics; count++){
			num+=closedList.get(count).size();
		}
		
		return num;
	}

	@Override
	public double getKey(Node s, int index) {
		// TODO Auto-generated method stub
		return s.get_sG(index) + w1 * s.get_sH(index);
	}

}
