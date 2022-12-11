package finalproject;


import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;
import java.util.HashSet;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path
    public ShortestPath(Tile start) {
        super(start);
        generateGraph();
    }
	@Override
	public void generateGraph() {
        ArrayList<Tile> arr = new ArrayList<Tile>();
        HashSet<Integer> visited = new HashSet<>();
        DepthFirstTraversal(super.source,arr,visited);
        g = new Graph(arr);
        for(Tile t1: g.getAllVertices()){
            for(Tile t2: t1.neighbors) {
                g.addEdge(t1,t2, t2.distanceCost);
            }
            if(t1.type != TileType.Metro) continue;
            for(Tile tile: g.getAllVertices()){
                if(tile == t1) continue;
                if(tile.type == TileType.Metro) {
                    ((MetroTile)tile).fixMetro(t1);
                    g.addEdge(t1,tile, ((MetroTile)tile).metroTimeCost);
                }
            }
        }
	}
    private static void DepthFirstTraversal(Tile s, ArrayList<Tile> arr, HashSet<Integer> visitedSet) {

        visitedSet.add(s.nodeID); //adds tile id to set (since they are unique)
        arr.add(s);
        for(Tile t: s.neighbors){
            if(t.isWalkable() && !visitedSet.contains(t.nodeID)) {
                DepthFirstTraversal(t, arr, visitedSet);
            }
        }
    }
}