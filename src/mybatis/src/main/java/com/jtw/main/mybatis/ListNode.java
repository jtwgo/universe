package com.jtw.main.mybatis;

public class ListNode<T>
{
    private int capacity;

    private int size;

    private static int DEFAULT_CAPACITY = 10;

    public ListNode()
    {
        this(DEFAULT_CAPACITY);
    }

    public ListNode(int capacity)
    {
        this.capacity = capacity;
    }

    public boolean add(T t)
    {

        return false;
    }


    public T remove(T t)
    {
        return t;
    }

    public T remove(int index)
    {

        return null;
    }

    public int size()
    {
        return size;
    }

    public int getIndex(T t)
    {
        return -1;
    }
}
