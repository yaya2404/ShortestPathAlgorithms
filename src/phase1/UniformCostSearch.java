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

			
			if(!open.contains(neighbor)) //deviation from pseudo code
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

/*
public class UniformCostSearch extends searchTemplate {

	public UniformCostSearch(Map m) {
		super(m);
		
		// TODO Auto-generated constructor stub
	}
	
	public void setupFringe(Comparator<Node> compare) {
		open = new PriorityQueue<Node>(size, compare);
		Node start = map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY());
		
		start.set_g(0);
		open.add(start);
		
	}
	*/
	/*
	 * This method should implement the abstract method findPath(int, int)
	 */
	/*
	public boolean searchPath(){
		/*
		function UNIFORM-COST-SEARCH(problem) returns a solution, or failure
		node <- a node with STATE = problem.INITIAL-STATE, PATH-COST = 0
		frontier <- a priority queue ordered by PATH-COST, with node as the only element
		explored <- an empty set
		loop do
			if EMPTY?( frontier) then return failure
				node <- POP( frontier )  chooses the lowest-cost node in frontier 
			if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
				add node.STATE to explored
			for each action in problem.ACTIONS(node.STATE) do
				child <- CHILD-NODE(problem, node, action)
				if child .STATE is not in explored or frontier then
					frontier <- INSERT(child , frontier )
				else if child.STATE is in frontier with higher PATH-COST then
					replace that frontier node with child
		
		Node start = map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY());
		start.set_g(0);
		
		Node current;
		
		this.open.add(start);
		while(!open.isEmpty()){
			current = open.remove();
			
			if(current.equals(map.getCell(goalX, goalY))){
				return true;
			}
			
			HashSet<Node> successors = findSuccessorSet(current);
			
			for(Node child: successors){
					
				if(!open.contains(child) && !closed.contains(child)){
					child.setParent(current);
					child.set_g(current.get_g() + current.cost(child));
					open.add(child);
				}else if(open.contains(child) && (current.get_g() + current.cost(child) < child.get_g())){
					child.set_g(current.get_g() + current.cost(child));
					child.setParent(current);
				}
			}
			closed.add(current);
		}
		
		return false;
	}
	*/