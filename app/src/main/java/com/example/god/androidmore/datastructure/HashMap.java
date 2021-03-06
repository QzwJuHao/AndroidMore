/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

import java.util.Map;

/**
 * key存在Set集合中（Set中数据不能相同），Value存在Collection中（可以相同）。
 * 无序集合，多线程下不安全
 * Hashmap默认初始化大小16，负载因子0.75
 * hashmap元素的key可以为空，key对应的index可以相同（链表）
 * JDK 1.8 以前 HashMap的实现是数组+链表，即使哈希函数取得再好，也很难达到元素百分百均匀分布。
 * 针对这种情况，JDK 1.8 中引入了 红黑树（查找时间复杂度为O(logn)）来优化这个问题,
 * 红黑树是自动平衡的二叉查找树，通过变色，左转，右转来实现平衡。
 *
 * 通过 Node<K,V> 对象实现 HashMap
 * @param <K>
 * @param <V>
 */
public class HashMap<K, V> {

  private Node<K, V>[] table;
  public int length;
  //默认的负载因子
  static final float DEFAULT_LOAD_FACTOR = 0.75f;
  public float load_factor = DEFAULT_LOAD_FACTOR;

  static final int hash(Object key) {
    //通过hashCode来算出hash值，hash值对应table上的位置
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }

  static class Node<K, V> implements Map.Entry<K, V> {

    final int hash;
    final K key;
    V value;
    Node<K, V> next;

    Node(int hash, K key, V value, Node<K, V> next) {
      this.hash = hash;
      this.key = key;
      this.value = value;
      this.next = next;
    }

    public final K getKey() {
      return key;
    }

    public final V getValue() {
      return value;
    }

    public final String toString() {
      return key + "=" + value;
    }

//    public final int hashCode() {
//      return Objects.hashCode(key) ^ Objects.hashCode(value);
//    }

    public final V setValue(V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }

  public void put(K key,V value) {

    int hash = hash(key);
    Node node = new Node(hash,key,value,null);

    if (table[hash] == null) {
      table[hash] = node;
    } else {
      Node<K, V> x = table[hash];
      while (x != null) {
        if ((x.hash == hash && (x.key == node.key ))) {
          table[hash]=node;
          break;
        }
        if (x.next == null) {
          x.next = node;
          break;
        }
        x = x.next;
      }
    }
    length++;
  }

  public Node<K, V> remove(K key) {

    int hash = hash(key);
    Node<K, V> x = table[hash];

    if (table[hash] == null) {
      return null;
    }
    if (table[hash].key == key) {
      if (x.next == null) {
        table[hash] = null;
      } else {
        table[hash] = x.next;
      }
      return x;
    }

    while (x != null) {
      if (x.key == key) {
        Node<K, V> node = x;
        x = x.next;
        return node;
      }
      x = x.next;
    }
    return null;
  }

  public void set(Object key, Object value) {
    Node<Object, Object> newNode = new Node<>(hash(key), key, value, null);
    Node<K, V> x = table[newNode.hash];
    if (x == null) {
      table[newNode.hash] = (Node<K, V>) newNode;
      return;
    } else {
      while (x != null) {
        if (x.key == key) {
          return;
        }
        x = x.next;
      }
      x.next = (Node<K, V>) newNode;
    }
  }

  public Node get(Object key) {
    int hash = hash(key);
    if (table[hash]==null) {
      return null;
    }else {
      Node<K,V> x = table[hash];
      while (x!=null){
        if (x.key == key) {
          return x;
        }
        x=x.next;
      }
      return null;
    }
  }
}
