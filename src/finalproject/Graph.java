package finalproject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {

    //private ArrayList<Edge> Edges;
    private HashMap<Tile,ArrayList<Edge>> Edges;
    private ArrayList<Tile> vertices; //will store all the vertices in the Graph
	public Graph(ArrayList<Tile> vertices) {
		Edges = new HashMap<Tile,ArrayList<Edge>>();
        this.vertices=vertices;
	}

    // weight ---> cost Field of destination tile
    public void addEdge(Tile origin, Tile destination, double weight){
        if (destination.isWalkable()) {
            if(!Edges.containsKey(origin) ) {
                Edges.put(origin, new ArrayList<Edge>());
                Edges.get(origin).add(new Edge(origin, destination, weight));
            } else if(origin.isWalkable()) {
                Edges.get(origin).add(new Edge(origin, destination, weight));
            }
        }
    }
	public ArrayList<Edge> getAllEdges() {
        ArrayList<Edge> arr = new ArrayList<Edge>();
        Edges.forEach((key,arraylist) -> {
            for(Edge e: arraylist){
                arr.add(e);
            }
        });
        return arr;
    }
    // only returns the neighbor tiles that are reachable
	public ArrayList<Tile> getNeighbors(Tile tile) {
        ArrayList<Tile> arr = new ArrayList<Tile>();
        for(Edge e: Edges.get(tile)){
           if(e.destination.isWalkable() && tile.isWalkable()){
               arr.add(e.destination);
           }
        }
        return arr;
    }
	public double computePathCost(ArrayList<Tile> path) {
        double cost=0;

        if(path.size()==1) return cost;

        for(int i=0 ;i < path.size()-1;i++){
            Edge tmp= getEdge(path.get(i),path.get(i+1));
            if(tmp == null) return 0; //if getEdge() returns null then edge does not exist so path cannot be finished
            cost += tmp.weight;
        }
        return cost;
}
    public Edge getEdge(Tile start, Tile next){
        for(Edge t: Edges.get(start)) {
            if(t.destination.equals(next)){
                return t;
            }
        }
        return null; //means edge between vertices does not exist.
    }
    public ArrayList<Tile> getAllVertices(){
        return this.vertices;
    }

    public HashMap<Tile, ArrayList<Edge>> getEdgesHashMap() {
        return Edges;
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

        arr.add(new PlainTile());
        arr.add(new MountainTile());
        arr.add(new MetroTile());
        arr.add(new FacilityTile());
        arr.add(new PlainTile());

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
                    g.addEdge(t,ti,ti.distanceCost);
                }
            }
        }
        for(Edge e: g.getAllEdges()) {
            System.out.println("Edge: Origin Tile: "+e.origin.type+ " || Destination Tile: "+ e.destination.type + " || Weight: "+ e.weight);
        }
        System.out.println(g.computePathCost(arr));
    }
}