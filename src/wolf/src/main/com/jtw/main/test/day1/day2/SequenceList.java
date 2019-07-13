package com.jtw.main.test.day1.day2;

public class SequenceList<T> {

    private final int DEFAULT_CAPACITY = 10;
    //用来存储数据的数组
    private Object[] objectElements;
    //数组的初始容量
    private int capacity;

    private int size = 0;
    public SequenceList(){
        this.capacity = DEFAULT_CAPACITY;
        objectElements = new Object[this.capacity];
    }
    public SequenceList(T element){
        this();
        objectElements[0] = element;
        size++;

    }
    public SequenceList(T element,int initSize){
        this.capacity = 1;
        while(this.capacity<initSize){
            this.capacity <<= 1;
        }
        objectElements = new Object[this.capacity];
        objectElements[0] = element;
        size++;

    }
    public void add(T t){
        if (size > capacity){
            //把数组长度扩大两倍
            this.capacity >>=1;
            objectElements = new Object[this.capacity];
            System.arraycopy(objectElements,0,objectElements,0,++size);
        }
    }

    public T remove(int i){
        return null;
    }

    public T get(int i){
        return null;
    }
    public int size(){

        return size;
    }
    public void clear(){

    }

}
