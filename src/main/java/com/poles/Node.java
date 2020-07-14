package com.poles;
/**
*********************************************************************
* 
* @author poles
* @date 2020/5/21 10:31 上午
*
*********************************************************************
*/
public class Node<Key, Value> {
    public Key key;
    public Value value;
    public Node<Key, Value> left;
    public Node<Key, Value> right;

    public Node(Key key, Value value, Node<Key, Value> left, Node<Key, Value> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
