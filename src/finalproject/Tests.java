package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.DesertTile;
import finalproject.tiles.MetroTile;
import finalproject.tiles.MountainTile;
import finalproject.tiles.PlainTile;

import java.util.ArrayList;

public class Tests {

    public static void main (String[] args){

        Tile T1 = new DesertTile();
        Tile T2 = new MetroTile();
        Tile T3 = new PlainTile();
        Tile T4 = new PlainTile();
        Tile T5 = new DesertTile();
        Tile T6 = new MetroTile();
        Tile T7 = new PlainTile();
        Tile T8 = new PlainTile();
        Tile T9 = new DesertTile();
        T1.costEstimate = 2.0;
        T2.costEstimate = 50.0;
        T3.costEstimate = 1.0;
        T4.costEstimate = 10.0;
        T5.costEstimate = 2.0;
        T6.costEstimate = 6.0;
        T7.costEstimate = 4.0;
        T8.costEstimate = 7.0;
        T9.costEstimate = 55.0;




        ArrayList <Tile> tiles = new ArrayList<>();

        tiles.add(T1);
        tiles.add(T2);
        tiles.add(T3);
        tiles.add(T4);
        tiles.add(T5);
        tiles.add(T6);
        tiles.add(T7);
        tiles.add(T8);
        tiles.add(T9);
        /*
        Starts: 2.0 50.0 1.0 10.0 2.0 6.0 4.0 7.0 55.0

        Should End: 1.0 2.0 2.0 7.0 50.0 6.0 4.0 10.0 55.0
         */

        TilePriorityQ q = new TilePriorityQ(tiles);

        if (q.Heap.get(1).equals(T3) &&
                q.Heap.get(2).equals(T5) &&
                q.Heap.get(3).equals(T1) &&
                q.Heap.get(4).equals(T8) &&
                q.Heap.get(5).equals(T2) &&
                q.Heap.get(6).equals(T6) &&
                q.Heap.get(7).equals(T7) &&
                q.Heap.get(8).equals(T4) &&
                q.Heap.get(9).equals(T9) ){
            System.out.println("You Passed");

        } else {
            System.out.println("Your Q was");
            for ( Tile t: q.Heap){
                if ( t != null){
                    System.out.print(t.costEstimate + " ");
                }}
            System.out.println("");
            System.out.println("========");
            System.out.println("The correct Q is");
            System.out.println("1.0 2.0 2.0 7.0 50.0 6.0 4.0 10.0 55.0");
        }
    }

}

