package phase1;

public class UniformCostSearch extends Search {

	public UniformCostSearch(Map m, int x, int y) {
		super(m, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * This method should implement the abstract method findPath(int, int)
	 */
	
	public Node searchPath(int startX, int startY){
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
		*/
		Node start = map.getCell(startX, startY);
		start.set_g(0);
		
		Node current;
		
		this.open.add(start);
		while(!open.isEmpty()){
			current = open.remove();
			
			if(current.equals(map.getCell(goalX, goalY))){
				return current;
			}
			
			findSuccessorSet(current);
			
			for(Node child: sucessors){
				if(!open.contains(child) && !closed.contains(child)){
					child.setParent(current);
					child.set_g(current.get_g() + current.cost(child));
					open.add(child);
				}else if(open.contains(child) && (current.get_g() + current.cost(child) < child.get_g())){
					child.set_g(current.get_g() + current.cost(child));
					child.setParent(current);
				}
			}
		}
		
		return null;
	}
	
	
	@Override
	public void UpdateVertex(Node current, Node neighbor) {
		// TODO Auto-generated method stub

	}
}
