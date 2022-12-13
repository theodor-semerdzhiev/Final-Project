package finalproject;

import finalproject.system.Logger;
import finalproject.system.Tile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal
{
	public static ArrayList<Tile> BFS(Tile s)
	{
		ArrayList<Tile> arr = new ArrayList<Tile>();
		HashSet<Integer> visited = new HashSet<>();
		LinkedList<Tile> list = new LinkedList<Tile>();

		visited.add(s.nodeID);
		list.addLast(s);
		arr.add(s);

		while(!list.isEmpty()) {
			Tile cur = list.getFirst();
			list.removeFirst();
			for(Tile t: cur.neighbors) {
				if(t.isWalkable() && !visited.contains(t.nodeID)) {
					visited.add(t.nodeID);
					arr.add(t);
					list.addLast(t);
				}
			}
		}
		return arr;
	}
	public static ArrayList<Tile> DFS(Tile s) {

		ArrayList<Tile> arr = new ArrayList<Tile>();
		HashSet<Integer> visited = new HashSet<>();
		DepthFirstTraversal(s,arr,visited);

		return arr;
	}
	public static void DepthFirstTraversal(Tile s, ArrayList<Tile> arr, HashSet<Integer> visitedSet) {

		visitedSet.add(s.nodeID); //adds tile id to set (since they are unique)
		arr.add(s);
		for(Tile t: s.neighbors){
			if(t.isWalkable() && !visitedSet.contains(t.nodeID)) {
				DepthFirstTraversal(t, arr, visitedSet);
			}
		}
	}
}  