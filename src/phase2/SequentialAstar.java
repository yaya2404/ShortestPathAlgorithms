package phase2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import phase1.Map;
import phase1.Node;

public class SequentialAstar extends Search {

	//ArrayList<PriorityQueue<Node>> openList; 
	ArrayList<HashMap<Node,Double>> openList;
	ArrayList<HashSet<Node>> closedList;
	int chosenheuristic;
	
	public SequentialAstar(Map m, Double weight1, Double weight2, int heuristics) {
		super(m, weight1, weight2, heuristics);
		// TODO Auto-generated constructor stub
	}
	
	public void expandState(Node s, int index) {
		// TODO Auto-generated method stub
		HashMap<Node,Double> open = openList.get(index);
		HashSet<Node> close = closedList.get(index);
		open.remove(s);
		
		for (Node neighbor : this.findSuccessorSet(s)) {
			if(!open.containsKey(neighbor) && !close.contains(neighbor)){
				initLists(neighbor, numberOfHeuristics);
				neighbor.set_sG(Double.POSITIVE_INFINITY, index);
				neighbor.set_sBp(null, index);
			}
			if(neighbor.get_sG(index) > s.get_sG(index) + s.cost(neighbor)){
				
				neighbor.set_sG(s.get_sG(index) + s.cost(neighbor), index);
				neighbor.set_sBp(s, index);
				if(!close.contains(neighbor)){
					neighbor.set_sH(goalX, goalY, index);
					open.put(neighbor,getKey(neighbor, index));
				}
			}
		}
		
	}

	@Override
	public void setupFringe() {
		// TODO Auto-generated method stub
		
		Node start = map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY());
		Node goal = map.getCell(goalX, goalY);
		
		initLists(start, numberOfHeuristics);
		initLists(goal, numberOfHeuristics);
		
		this.openList = new ArrayList<HashMap<Node,Double>>();
		this.closedList = new ArrayList<HashSet<Node>>();	
		
		for(int count = 0; count < numberOfHeuristics; count++){
			HashMap<Node,Double> open = new HashMap<Node,Double>();
			HashSet<Node> close = new HashSet<Node>();
			
			start.set_sG(0.0, count);
			start.set_sBp(null, count);
			
			goal.set_sG(Double.POSITIVE_INFINITY, count);
			goal.set_sBp(null, count);
			
			//not sure if this is the correct way to do it
			start.set_sH(goalX, goalY, count);
		
			open.put(start,getKey(start, count));
			
			openList.add(open);
			closedList.add(close);
		}
	}

	@Override
	public boolean findPath() {
		// TODO Auto-generated method stub
		
		long starttime = System.nanoTime();
		double startMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; 
		double endMemory = 0;
		
		Node goal = map.getCell(goalX, goalY);
		Node s;
		
		while(minKey(openList.get(0)) < Double.POSITIVE_INFINITY){
			for (int i = 1; i < numberOfHeuristics; i++) {
				
				HashMap<Node,Double> open = openList.get(i);
				
				if(minKey(open) <= w2 * minKey(openList.get(0))){
					if(goal.get_sG(i) <= minKey(open)){
						if(goal.get_sG(i) < Double.POSITIVE_INFINITY){
							time = (System.nanoTime() - starttime)/1000000;
							endMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; //KB output
							memory = endMemory - startMemory;
							if(memory < 0){
								memory = 0;
							}
							chosenheuristic = i;
							return true;
						}
					}else{
						s = top(open);
						expandState(s, i);
						closedList.get(i).add(s);
					}
				}else{
					if(goal.get_sG(0) <= minKey(openList.get(0))){
						time = (System.nanoTime() - starttime)/1000000;
						endMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; //KB output
						memory = endMemory - startMemory;
						if(memory < 0){
							memory = 0;
						}
						chosenheuristic = 0;
						return true;
					}else{
						s = top(openList.get(0));
						expandState(s, 0);
						closedList.get(0).add(s);
					}
				}
			}
		}
		
		return false;
	}
	
	
	/*
	public void expandState(Node s, int index) {
		// TODO Auto-generated method stub
		PriorityQueue<Node> open = openList.get(index);
		HashSet<Node> close = closedList.get(index);
		open.remove(s);
		
		for (Node neighbor : this.findSuccessorSet(s)) {
			if(!open.contains(neighbor) && !close.contains(neighbor)){
				neighbor.set_sG(Double.POSITIVE_INFINITY, index);
				neighbor.set_sBp(null, index);
			}
			if(neighbor.get_sG(index) > s.get_sG(index) + s.cost(neighbor)){
				
				neighbor.set_sG(s.get_sG(index) + s.cost(neighbor), index);
				neighbor.set_sBp(s, index);
				if(!close.contains(neighbor)){
					
					open.remove(neighbor);
					neighbor.set_sH(goalX, goalY, index);
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
		
		
		for(int count = 0; count < numberOfHeuristics; count++){
			PriorityQueue<Node> open = new PriorityQueue<Node>(new Node.NodeKeyComparator());
			HashSet<Node> close = new HashSet<Node>();
			
			start.set_sG(0.0, count);
			start.set_sBp(null, count);
			
			goal.set_sG(Double.POSITIVE_INFINITY, count);
			goal.set_sBp(null, count);
			
			//not sure if this is the correct way to do it
			start.set_sH(goalX, goalY, count);
			start.setKey(getKey(start, count));
			open.add(start);
			
			openList.add(open);
			closedList.add(close);
		}
	}

	@Override
	public boolean findPath() {
		// TODO Auto-generated method stub
		
		long starttime = System.nanoTime();
		double startMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; 
		double endMemory = 0;
		
		Node goal = map.getCell(goalX, goalY);
		Node s;
		
		while(getKey(openList.get(0).peek(), 0) < Double.POSITIVE_INFINITY){
			for (int i = 1; i < numberOfHeuristics; i++) {
				
				PriorityQueue<Node> open = openList.get(i);
				
				if(getKey(open.peek(),i) <= w2 * getKey(openList.get(0).peek(), 0)){
					if(goal.get_sG(i) <= getKey(open.peek(), i)){
						if(goal.get_sG(i) < Double.POSITIVE_INFINITY){
							time = (System.nanoTime() - starttime)/1000000;
							endMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; //KB output
							memory = endMemory - startMemory;
							if(memory < 0){
								memory = 0;
							}
							chosenheuristic = i;
							return true;
						}
					}else{
						s = open.remove();
						expandState(s, i);
						closedList.get(i).add(s);
					}
				}else{
					if(goal.get_sG(0) <= getKey(openList.get(0).peek(), 0)){
						time = (System.nanoTime() - starttime)/1000000;
						endMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; //KB output
						memory = endMemory - startMemory;
						if(memory < 0){
							memory = 0;
						}
						chosenheuristic = 0;
						return true;
					}else{
						s = openList.get(0).remove();
						expandState(s, 0);
						closedList.get(0).add(s);
					}
				}
			}
		}
		
		return false;
	}
	*/
	@Override
	public int getNumOfExpandedNodes() {
		// TODO Auto-generated method stub
		
		int num = 0;
		
		for(int count = 0; count < numberOfHeuristics; count++){
			num+=closedList.get(count).size();
		}
		
		return num;
	}

	@Override
	public double getKey(Node s, int index) {
		// TODO Auto-generated method stub
		return s.get_sG(index) + w1 * s.get_sH(index);
	}
	
	
	public double getPathCost(){
		Node goal = map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY());
		return goal.get_sG(chosenheuristic);
	}
	
	
	public void printPath(){
		pathlength = 0;
		Node s = map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY());
		
		
		while(s!=null){
			//System.out.println(s.getType());
			s.setType(Node.path);
			
			//sets the g and f value to the appropriate heur chosen
			s.set_g(s.get_sG(chosenheuristic));
			s.update_f(w1);
			s = s.get_sBp(chosenheuristic);
			pathlength++;
		}
	}
	
	/**
	 * Should only initialize for nodes involved in the search
	 * @param s
	 * @param numberOfHeuristics
	 */
	private void initLists(Node s, int numberOfHeuristics) {
				s.initbpList(numberOfHeuristics);
				s.inithList(numberOfHeuristics);
				s.initgList(numberOfHeuristics);
			
	}
	
}
