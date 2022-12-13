package finalproject;

import finalproject.system.Logger;
import finalproject.system.Tile;
import finalproject.system.TileType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal
{
	public static ArrayList<Tile> BFS(Tile s)
	{
		ArrayList<Tile> arr = new ArrayList<Tile>();
		HashSet<Tile> visited = new HashSet<>();
		LinkedList<Tile> list = new LinkedList<Tile>();

		visited.add(s);
		list.addLast(s);
		arr.add(s);

		while(!list.isEmpty()) {
			Tile cur = list.getFirst();
			list.removeFirst();
			for(Tile t: cur.neighbors) {
				if(t.isWalkable() && !visited.contains(t)) {
					visited.add(t);
					arr.add(t);
					list.addLast(t);
				}
			}
		}
		return arr;
	}
	public static ArrayList<Tile> DFS(Tile s) {

		ArrayList<Tile> arr = new ArrayList<Tile>();
		HashSet<Tile> visited = new HashSet<>();
		DepthFirstTraversal(s,arr,visited);

		return arr;
	}
	public static void DepthFirstTraversal(Tile s, ArrayList<Tile> arr, HashSet<Tile> visitedSet) {

		visitedSet.add(s); //adds tile id to set (since they are unique)
		arr.add(s);
		for(Tile t: s.neighbors)
			if(t.type!= TileType.Moutain && !visitedSet.contains(t)) {
				DepthFirstTraversal(t, arr, visitedSet);
			}
	}
}  