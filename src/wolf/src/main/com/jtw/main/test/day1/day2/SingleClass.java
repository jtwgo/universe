package com.jtw.main.test.day1.day2;

import java.util.*;
import java.util.logging.Logger;

public class SingleClass {
    private static final Logger logger = Logger.getLogger(SingleClass.class.getSimpleName());

    private SingleClass() {
//        System.out.println("load singleClass()");
    }

    private static SingleClass s = new SingleClass();

    //    public static String a = testStr();
    public static SingleClass getInstance() {
        return s;
    }

    private static String testStr() {
        System.out.println("testStr（）");
//        throw new RuntimeException("error");
        return "testStr";
    }

    public static void main(String[] args) {
//        String a = "annnn";
//        int h = a.hashCode();
//        System.out.println("h:"+h);
//        int m;
//        System.out.println("h >>> 16:"+(m = h >>> 16));
//        int r = h ^ (h >>> 16);
//        System.out.println(r);
//        System.out.println(Integer.toBinaryString(h));
//        System.out.println(Integer.toBinaryString(h>>>16));
//        System.out.println(Integer.toBinaryString(r));
//        String s = "101100010101001011011101011";
//        System.out.println(s.equals(Integer.toBinaryString(r)));
//        System.out.println(s==Integer.toBinaryString(r));
//        System.out.println(0 & 12);

//        System.out.println(101100010101001011011101011);
//        Queue<Integer> queue = new LinkedList<>();
//        Random random = new Random(47);
//        for (int i = 0; i < 10 ; i++) {
//            queue.offer(random.nextInt(i+10));
//
//        }
////        System.out.println(queue);
//        Queue<Character> qc = new LinkedList<>();
//        for (char s:"mrjiang".toCharArray()) {
//            qc.offer(s);
//        }
//        while(qc.peek()!=null){
//            System.out.print(qc.remove()+"");
//            System.out.println();
//        }
//        Map<Integer,Integer> map = new HashMap<>();
//        int[] aa = {1,2,3,4,5};
//        for (int i:aa){
//            System.out.println(aa);
//        }
//        test(aa);
        //        List<String> list = new ArrayList<String>(1);
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        ListIterator<String> li = list.listIterator(2);
//        while (li.hasPrevious()){
//            System.out.println(li.previous()+" "+li.previousIndex()+" "+li.nextIndex());
//        }
//        Iterator<String> it = list.iterator();
//        List<Integer> l = new ArrayList<>();
//        while (it.hasNext()){
//            it.remove();
//            System.out.println(it.next());
//        }
//        System.out.println(list.iterator());
        System.out.println(get());
    }

    private static <T> void test(Iterable<T> it) {
        for (T t : it) {
            System.out.println(t);
        }
    }

    private static int get() {
        int i = 1;
        String s1 = String.format("aaa%s", "bbb");
        System.out.println(s1);
        try {

//            throw new RuntimeException("runtimeException");
            return i;
//            String sq = "a";
//            String s1;


        } catch (Exception e) {

        }finally {
            System.out.println("finally");
            return i=3;
        }
//        return 0;
    }
}
