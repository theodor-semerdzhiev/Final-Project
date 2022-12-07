package finalproject;

import java.util.ArrayList;
import java.util.HashSet;
import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {
	// TODO level 2: Add fields that can help you implement this data type

    private ArrayList<Edge> Edges;
    private ArrayList<Tile> vertices; //will store all the vertices in the Graph

    // TODO level 2: initialize and assign all variables inside the constructor
	public Graph(ArrayList<Tile> vertices) {
		Edges = new ArrayList<Edge>();
        this.vertices=vertices;
	}
	
    // TODO level 2: add an edge to the graph
    // weight ---> cost Field of destination tile
    public void addEdge(Tile origin, Tile destination, double weight){
        if (destination.isWalkable()) Edges.add(new Edge(origin,destination, weight));
    }

	public ArrayList<Edge> getAllEdges() {
        return Edges;
    }

    // only returns the neighbor tiles that are reachable
	public ArrayList<Tile> getNeighbors(Tile tile) {
        ArrayList<Tile> arr = new ArrayList<Tile>();
        for(Tile t: tile.neighbors){
           if(t.isWalkable()){
               arr.add(t);
           }
        }
        return arr;
    }
	
	// TODO level 2: return total cost for the input path
	public double computePathCost(ArrayList<Tile> path) {
        double cost=0;
        if(path.size()==1) return cost;

        for(int i=0 ;i < path.size()-2; i++){
            Double tmp= getEdge(path.get(i),path.get(i+1)).weight;
            if(tmp == null) return 0; //if getEdge() returns null then edge does not exist so path cannot be finished
            cost+= tmp;
        }
        return cost;
	}

    private Edge getEdge(Tile start, Tile next){
        for(Edge e: Edges) {
            if(e.origin == start && e.destination == next) {
                return e;
            }
        }
        return null; //means edge between vertices does not exist.
    }
    //used to get all Edge ArrayList
    public ArrayList<Edge> getHashSetOfEdges(){
        return Edges;
    }
    public ArrayList<Tile> getAllVertices(){
        return this.vertices;
    }
    public static class Edge{
    	Tile origin;
    	Tile destination;
    	double weight;

        public Edge(Tile s, Tile d, double cost){
        	origin=s;
            destination=d;
            weight = cost;
        }

        public Tile getStart(){
            return origin;
        }

        public Tile getEnd() {
            return destination;
        }
        
    }

    //for testing purposes
    public static void main(String args[]) {
        ArrayList<Tile> arr = new ArrayList<Tile>();
        arr.add(new DesertTile());
        arr.add(new PlainTile());
        arr.add(new MountainTile());
        arr.add(new MetroTile());
        for(Tile t: arr){
            for(Tile ti : arr){
                if(!t.equals(ti)) {
                    t.neighbors.add(ti);
                }
            }
        }

        Graph g = new Graph(arr);
        for(Tile t: arr){
            for(Tile ti : arr){
                if(!t.equals(ti)) {
                    g.addEdge(t,ti,ti.damageCost);
                }
            }
        }
        for(Edge e: g.getAllEdges()) {
            System.out.println("Edge: Origin Tile: "+e.origin.type+ " || Destination Tile: "+ e.destination.type + " || Weight: "+ e.weight);
        }
    }
}