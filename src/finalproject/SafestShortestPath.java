package finalproject;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	private enum  weightType {
		DISTANCE,DAMAGE
	}

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
	}

	public void generateGraph() {
		costGraph=createGraph(weightType.DISTANCE);
		damageGraph=createGraph(weightType.DAMAGE);
		aggregatedGraph=createGraph(weightType.DAMAGE);
	}
	private Graph createGraph(weightType type){
		Graph g;
		ArrayList<Tile> arr = new ArrayList<Tile>();
		HashSet<Integer> visited = new HashSet<>();
		GraphTraversal.DepthFirstTraversal(super.source,arr,visited);
		g = new Graph(arr);
		for(Tile t1: g.getAllVertices()){
			for(Tile t2: t1.neighbors) {
				switch (type){
					case DISTANCE:
						g.addEdge(t1,t2, t2.distanceCost);
						break;
					case DAMAGE:
						g.addEdge(t1,t2, t2.damageCost);
						break;
				}
			}
			if(t1.type != TileType.Metro) continue;
			for(Tile tile: g.getAllVertices()){
				if(tile == t1) continue;
				if(tile.type == TileType.Metro) {
					((MetroTile)tile).fixMetro(t1);
					switch (type){
						case DISTANCE:
							g.addEdge(t1,tile, ((MetroTile)tile).metroDistanceCost);
							break;
						case DAMAGE:
							g.addEdge(t1,tile, ((MetroTile)tile).damageCost);
							break;
					}
				}
			}
		}
		return g;
	}
	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
		super.g=costGraph;
		ArrayList<Tile> costPath = super.findPath(start,waypoints);
		double cost= damageGraph.computePathCost(costPath);

		if(cost < health) return costPath;

		super.g=damageGraph;
		ArrayList<Tile> damagePath = super.findPath(start,waypoints);
		double damagecost= g.computePathCost(damagePath);

		if(damagecost > health) return null;

		while(true) {
			double lambda_multiplier = (costGraph.computePathCost(costPath)-costGraph.computePathCost(damagePath))/(damagecost-damageGraph.computePathCost(costPath));

			for(Graph.Edge e: aggregatedGraph.getAllEdges()){
				e.weight=e.destination.distanceCost+(lambda_multiplier*e.destination.damageCost);
			}

			super.g=aggregatedGraph;
			ArrayList<Tile> aggregatedpath = super.findPath(start,waypoints);
			double aggregatecost = g.computePathCost(aggregatedpath);
			if (aggregatecost == g.computePathCost(costPath)) {
				return damagePath;
			} else if (damageGraph.computePathCost(aggregatedpath) <= health) {
				damagePath=aggregatedpath;
			} else {
				//aggregatedpath = costPath;
				costPath = aggregatedpath;
			}
		}
	}

	public double computeCost(ArrayList<Tile> path, weightType type){
		double cost=0;
		for(int i=1; i< path.size(); i++){
			switch (type) {
				case DAMAGE:
					cost += path.get(i).damageCost;
					continue;
				case DISTANCE:
					cost += path.get(i).distanceCost;
			}
		}
		return cost;
	}
}
