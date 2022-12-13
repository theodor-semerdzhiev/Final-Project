package finalproject;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;
import java.util.HashSet;

public class FastestPath extends PathFindingService {
    public FastestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
	public void generateGraph() {
        ArrayList<Tile> arr = GraphTraversal.BFS(super.source);
        g = new Graph(arr);
        for(Tile t1: g.getAllVertices()){
            if(!t1.isWalkable()) continue;
            for(Tile t2: t1.neighbors) {
                if(t1.type == TileType.Metro && t2.type == TileType.Metro && t2.isWalkable()) {
                    ((MetroTile)t2).fixMetro(t1);
                    g.addEdge(t1,t2, ((MetroTile)t2).metroTimeCost);
                } else if(t2.isWalkable()) {
                    g.addEdge(t1, t2, t2.timeCost);
                }
            }
        }
	}
}
