package jp.gn3.reversiai;

import java.util.*;

public class ReversiAI {

    public static void main(String[] args) throws InterruptedException {
        new ReversiAI();
    }

    private Reversi reversi;

    public static int black = 0;
    public static int white = 0;
    public static int draw = 0;


    long start;

    public ReversiAI(){
        start = System.currentTimeMillis();

        reversi = new Reversi();

        while (true){
            if(reversi.status == Status.End) {
                System.out.print("\r");
                int game = black+white+draw;
                float winrateBlack = (((float)black)/((float)game))*100;
                float winrateWhite = (((float)white)/((float)game))*100;
                float rateDraw = (((float)draw)/((float)game))*100;

                long time = System.currentTimeMillis()-start;
                float speed = (((float)game)/((float)time))*1000;
                System.out.print("試合数:"+game+" ("+speed+"/s) | 黒: "+black+" ("+winrateBlack+"%) | 白: "+white+" ("+winrateWhite+"%) | 引き分け: "+draw+" ("+rateDraw+"%)");
                reversi = new Reversi();
            }


            //ランダムに置く場合
            List<Location> r = new ArrayList<>();
            for (Location location : reversi.allPickupCount.keySet()) {
                r.add(location);
            }
            Collections.shuffle(r);
            reversi.place(r.get(0));


            //一番ひっくり返せる場所(複数あったらランダム)に置く場合
//            List<Location> max = new ArrayList<>();
//            int count = 0;
//            for (Map.Entry<Location, Integer> list : reversi.allPickupCount.entrySet()) {
//                if (list.getValue() > count) {
//                    max.clear();
//                    max.add(list.getKey());
//                    count = list.getValue();
//                } else if (list.getValue() == count) {
//                    max.add(list.getKey());
//                }
//            }
//            Collections.shuffle(max);
//            reversi.place(max.get(0));


//            Scanner sc = new Scanner(System.in);
//            String line = sc.nextLine();
//            try {
//                String[] split = line.split(":");
//                int pickup = reversi.place(Integer.valueOf(split[0]), Integer.valueOf(split[1]), reversi.getNext());
//                if (pickup == 0) {
//                    System.out.println("その場所は選択できません");
//                }
//            }catch (Exception e){
//                clearLog();
//                System.out.println("正しく入力してください (例: \"3:2\")");
//            }
//            if(reversi.status == Status.InProgress) {
//                clearLog();
//                reversi.sendTable();
//                System.out.println(reversi.getNext());
//            }
        }
    }

    public void clearLog(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
