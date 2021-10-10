package jp.gn3.reversiai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reversi {

    private List<List<Stone>> table = new ArrayList<>();
    private Stone nextStone = Stone.black;
    public HashMap<Location, Integer> allPickupCount;

    public Reversi(){
        for (int i = 0; i < 8; i++) {
            List<Stone> line = new ArrayList<>();
            for (int i2 = 0; i2 < 8; i2++) {
                line.add(Stone.none);
            }
            table.add(line);
        }
        setStone(3,3, Stone.white);
        setStone(4,4, Stone.white);
        setStone(3,4, Stone.black);
        setStone(4,3, Stone.black);

//        for (int y = 0; y < 8; y++) {
//            for (int x = 0; x < 7; x++) {
//                setStone(x,y,Stone.white);
//            }
//        }
//        setStone(6,0, Stone.black);
//        next();

        allPickupCount = getAllPickupCount(nextStone);
    }

    public Stone getNext() {
        return nextStone;
    }

    public Status status = Status.InProgress;

    private void next(){
        nextStone = nextStone.getReverse();
        allPickupCount = getAllPickupCount(nextStone);
        if (allPickupCount.size() == 0) {
            nextStone = nextStone.getReverse();
            allPickupCount = getAllPickupCount(nextStone);
            if (allPickupCount.size() == 0) {
                HashMap<Stone, Integer> end = getEnd();
//                sendTable();
                int white = end.get(Stone.white);
                int black = end.get(Stone.black);
//                System.out.println("white: "+white);
//                System.out.println("black: "+black);
                if(black > white){
                    ReversiAI.black++;
//                    System.out.println("Black Win");
                }
                else if(black < white) {
                    ReversiAI.white++;
//                    System.out.println("White Win");
                } else {
                    ReversiAI.draw++;
                }
                status = Status.End;
            }
        }
    }


    public int place(Location loc,Stone stone) {
        return place(loc.getX(),loc.getY(),stone);
    }

    public int place(Location loc) {
        return place(loc.getX(),loc.getY(),nextStone);
    }

    public int place(int x, int y) {
        return place(x,y,nextStone);
    }

    public int place(int x, int y, Stone stone){
        int count = 0;

        int up = directionPickup(x, y, 0, -1, stone);
        count +=up;
        int rightUp = directionPickup(x, y, 1, -1, stone);
        count +=rightUp;
        int right = directionPickup(x, y, 1, 0, stone);
        count +=right;
        int rightDown = directionPickup(x, y, 1, 1, stone);
        count +=rightDown;
        int down = directionPickup(x, y, 0, 1, stone);
        count +=down;
        int leftDown = directionPickup(x, y, -1, 1, stone);
        count +=leftDown;
        int left = directionPickup(x, y, -1, 0, stone);
        count +=left;
        int leftUp = directionPickup(x, y, -1, -1, stone);
        count +=leftUp;

        if(count != 0) {
            setStone(x,y,stone);
            next();
        }

        return count;
    }

    private int directionPickup(int x,int y,int addX,int addY,Stone stone){
        if(stone == Stone.none) return 0;
        List<Location> pickup = new ArrayList<>();
        int count = 0;
        while (true) {
            x += addX;
            y += addY;
            Stone target = getStone(x, y);
            if(target == null){
                return 0;
            }
            if(target == Stone.none||stone == null){
                return 0;
            }
            if(target == stone){
                break;
            }
            pickup.add(new Location(x,y));
            count++;
        }
        for (Location loc : pickup) {
            setStone(loc,stone);
        }
        return count;
    }

    public HashMap<Location, Integer> getAllPickupCount(Stone stone){
        HashMap<Location,Integer> pickupCount = new HashMap<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Location loc = new Location(x, y);
                int count = pickupCount(x, y, stone);
                if(count != 0) {
                    pickupCount.put(loc, count);
                }
            }
        }
        return pickupCount;
    }

    public HashMap<Stone,Integer> getEnd(){
        int white = 0;
        int black = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Stone stone = getStone(x, y);
                if(stone == Stone.white) {
                    white++;
                }
                else if(stone == Stone.black) {
                    black++;
                }
            }
        }
        HashMap<Stone,Integer> stoneCount = new HashMap<>();
        stoneCount.put(Stone.black,black);
        stoneCount.put(Stone.white,white);
        return stoneCount;
    }

    public int pickupCount(Location loc,Stone stone){
        return pickupCount(loc.getX(),loc.getY(),stone);
    }

    public int pickupCount(int x,int y,Stone stone){
        if(getStone(x,y) != Stone.none) return 0;
        if(stone == Stone.none) return 0;

        int count = 0;

        int up = directionPickupCount(x, y, 0, -1, stone);
        count +=up;
        int rightUp = directionPickupCount(x, y, 1, -1, stone);
        count +=rightUp;
        int right = directionPickupCount(x, y, 1, 0, stone);
        count +=right;
        int rightDown = directionPickupCount(x, y, 1, 1, stone);
        count +=rightDown;
        int down = directionPickupCount(x, y, 0, 1, stone);
        count +=down;
        int leftDown = directionPickupCount(x, y, -1, 1, stone);
        count +=leftDown;
        int left = directionPickupCount(x, y, -1, 0, stone);
        count +=left;
        int leftUp = directionPickupCount(x, y, -1, -1, stone);
        count +=leftUp;

        return count;
    }

    private int directionPickupCount(int x,int y,int addX,int addY,Stone stone){
        if(stone == Stone.none) return 0;
        int count = 0;
        while (true) {
            x += addX;
            y += addY;
            Stone target = getStone(x, y);
            if(target == null){
                return 0;
            }
            if(target == Stone.none||stone == null){
                return 0;
            }
            if(target == stone){
                return count;
            }
            count++;
        }
    }

    public Stone getStone(Location location) {
        return getStone(location.getX(), location.getY());
    }

    public Stone getStone(int x, int y) {
        try {
            Stone stone = table.get(y).get(x);
            return stone;
        }catch (Exception e){
            return null;
        }
    }

    public void setStone(Location location, Stone stone){
        setStone(location.getX(), location.getY(),stone);
    }

    public void setStone(int x, int y, Stone stone){
        table.get(y).set(x,stone);
    }

    public void sendTable(){
        System.out.println("  0 1 2 3 4 5 6 7 x");
        for (int y = 0; y < 8; y++) {
            String lineMsg = null;
            for (int x = 0; x < 8; x++) {
                int count = 0;
                Location loc = new Location(x, y);
                if(allPickupCount.containsKey(loc)){
                    count = allPickupCount.get(loc);
                }
                String display = toPickCountDisplay(x, y, count);
                if(lineMsg == null){
                    lineMsg = y+" "+display;
                } else {
                    lineMsg += ","+display;
                }
            }
            System.out.println(lineMsg);
        }
        System.out.println("y    Next: "+nextStone);
    }

    private String toPickCountDisplay(int x,int y,int count){
        Stone stone = getStone(x,y);
        if(stone == Stone.white){
            return "●";
        }
        else if(stone == Stone.black){
            return "◯";
        } else {
            if(count == 0){
                return "□";
            } else {
                return count+"";
            }
        }
    }
}
