package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;
	
	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();
    

    public ArrayList<Tile> findPath(Tile startNode) {

        init_single_source(startNode);
        TilePriorityQ priQ = new TilePriorityQ(g.getAllVertices());
        Tile tmp=null;
        Tile destination=null;
        while(!priQ.isEmpty()) {
            boolean foundDestination=false;
            tmp = priQ.removeMin();
            for(Tile t: g.getNeighbors(tmp)) {
                relax(tmp, t, priQ);
                if(t.isDestination) {
                    foundDestination=true;
                    destination=t;
                    break;
                }
            }
            if(foundDestination) break;
        }
        ArrayList<Tile> arr = new ArrayList<>();

        while(destination !=null) {
            arr.add(0,destination);
            destination=destination.predecessor;
        }
        System.out.println(arr.toString());
        return arr;

    }
    private void init_single_source(Tile tile) {
        for(Tile t: g.getAllVertices()) {
            t.costEstimate=Integer.MAX_VALUE; //means that the costEstimate has not been checked yet
            t.predecessor=null;
        }
        tile.costEstimate=0;
    }
    private void relax(Tile current, Tile next, TilePriorityQ Q){
        Graph.Edge e = g.getEdge(current,next);
        if(next.costEstimate > (current.costEstimate + e.weight)) {
            Q.updateKeys(next,current,current.costEstimate + e.weight);
        }
    }

    public ArrayList<Tile> findPath(Tile start, Tile end) {

        init_single_source(start);
        TilePriorityQ priQ = new TilePriorityQ(g.getAllVertices());
        Tile tmp=null;
        Tile destination=null;
        while(!priQ.isEmpty()) {
            boolean foundDestination=false;
            tmp = priQ.removeMin();
            for(Tile t: g.getNeighbors(tmp)) {
                relax(tmp, t, priQ);
                if(t.equals(end)) {
                    foundDestination=true;
                    destination=t;
                    break;
                }
            }
            if(foundDestination) break;
        }
        ArrayList<Tile> arr = new ArrayList<>();
        while(destination !=null) {
            if(destination == start) break;
            arr.add(0,destination);
            destination=destination.predecessor;
        }
        arr.add(0, start);
        System.out.println(arr.toString());
        return arr;

    }

    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
        ArrayList<Tile> arr = new ArrayList<>();
        Tile tmp = start;
        ArrayList<Tile> tmp2=null;
        for (Tile t : waypoints) {
            arr.addAll(findPath(tmp, t));
            tmp = t;
            arr.remove(tmp);
        }
        tmp2 = findPath(tmp);
        arr.addAll(findPath(tmp2.get(0)));
        return arr;
    }
}

