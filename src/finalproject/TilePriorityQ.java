package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;
import finalproject.tiles.DesertTile;
import finalproject.tiles.MetroTile;
import finalproject.tiles.MountainTile;
import finalproject.tiles.PlainTile;

//TODO IMPLEMENT HASHMAP FOR INDEX AND TILE PAIRS
//TODO MAKE BUILD HEAP O(n) TIME
//CURRENTLY FUNCTIONAL BUT NEEDS IMPROVEMENT
public class TilePriorityQ {


	private ArrayList<Tile> Heap;
	private int size;


	public TilePriorityQ (ArrayList<Tile> vertices) {
		size=0;
		Heap=new ArrayList<Tile>(1);
		Heap.add(null); //sets first index to 0
		for(Tile t: vertices){
			add(t);
		}
	}
	//Test this method
	public Tile add(Tile t){
		size++;
		Heap.add(size, t);
		int i=size;
		while(i > 1 && Heap.get(i).costEstimate < Heap.get(i/2).costEstimate){
			swap(Heap,i ,i/2);
			i=i/2;
		}
		return Heap.get(1); //returns the head of this array heap
	}

	private void swap(ArrayList<Tile> arr, int index1, int index2){
		Tile tmp = arr.get(index1);
		arr.set(index1, arr.get(index2));
		arr.set(index2, tmp);
	}

	public Tile removeMin() {
		if(size == 0){
			return null;
		}
		Tile tmp = Heap.get(1);
		Heap.set(1, Heap.get(size));
		Heap.set(size,null);
		size--;
		downHeap(1,size);
		return tmp;
	}
	public ArrayList<Tile> getHeap() {
		return Heap;
	}
	public void downHeap(int startindex, int maxindex){
		int i= startindex;
		while(2*i <= maxindex) {
			int child = 2*i;
			if(child < maxindex) {
				if(Heap.get(child +1).costEstimate < Heap.get(child).costEstimate) {
					child++;
				}
			}
			if(Heap.get(child).costEstimate < Heap.get(i).costEstimate) {
				swap(Heap,i,child);
				i=child;
			} else {
				break;
			}
		}
	}
	// TODO level 3: implement a faster algorithm for this instead of removing and adding again
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		for(Tile tile: Heap){
			if(tile == null) continue;
			if(tile == t) {
				t.predecessor=newPred;
				t.costEstimate=newEstimate;
				Heap.remove(t);
				size--;
				add(t);
				break;
			}
		}
	}
	public int getSize() {
		return size;
	}
	public boolean isEmpty() {
		if(size==0) {
			return true;
		} else {
			return false;
		}
	}
	//for testing purposes
	private static void PrintTest(TilePriorityQ PriorityQ){
		System.out.print("[");
		for(Tile t: PriorityQ.Heap){
			if(t == null) {
				System.out.print(" null ,");
				continue;
			}
			System.out.print(" "+t.costEstimate+" ,");
		}
		System.out.println("]");
		for(Tile t: PriorityQ.Heap) {
			try {
				System.out.print(t + " || Cost: " + t.costEstimate + " ");
			} catch(Exception e){
				continue;
			}
			try {
				System.out.print(" || Parent: "+PriorityQ.Heap.get(PriorityQ.Heap.indexOf(t)/2).costEstimate);
			} catch (Exception e){
				System.out.print(" || Parent: null");
			}
			try {
				System.out.print(" || Left Child: "+PriorityQ.Heap.get(PriorityQ.Heap.indexOf(t)*2).costEstimate);
			} catch (Exception e){
				System.out.print(" || Left Child: null");
			}
			try {
				System.out.print(" || Right Child: "+PriorityQ.Heap.get(PriorityQ.Heap.indexOf(t)*2+1).costEstimate+"\n");
			} catch (Exception e){
				System.out.print(" || Right Child: null\n");
			}
		}
	}
	public static void main (String args[]){
		ArrayList<Tile> arr = new ArrayList<Tile>();
		arr.add(new DesertTile());
		arr.add(new PlainTile());
		arr.add(new MountainTile());
		arr.add(new MetroTile());
		arr.add(new DesertTile());
		arr.add(new PlainTile());
		arr.add(new PlainTile());
		arr.add(new PlainTile());
		arr.add(new PlainTile());
		//arr.add(new PlainTile());
		//arr.add(new PlainTile());

		int i=arr.size();
		for(Tile t: arr){
			t.costEstimate=3+i++;
		}
		TilePriorityQ PriorityQ = new TilePriorityQ(arr);
		System.out.print("[");

		PrintTest(PriorityQ);
		PriorityQ.removeMin();
		System.out.println();
		System.out.println();
		PrintTest(PriorityQ);
		System.out.println();
		System.out.println();
		PriorityQ.updateKeys(PriorityQ.Heap.get(8),null,10);
		PrintTest(PriorityQ);
	}
}
