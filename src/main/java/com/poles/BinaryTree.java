package com.poles;

import java.util.Comparator;

/**
*********************************************************************
* 
* @author poles
* @date 2020/5/21 10:38 上午
*
*********************************************************************
*/
public class BinaryTree<Key extends Comparable<Key>, Value> {
    private int size;
    private Node<Key, Value> root;

    public int size(){
        return this.size;
    }

    /**
     * 插入思想：
     * 1. 如果没有元素，则直接赋值为根节点
     * 2. 从根节点开始遍历，如果小于根节点，找左子节点，如果大于根节点，找右子节点，如果等于根节点，直接更新Value值
     * 3. 如果左子节点或右子节点不存在，直接将插入的元素变成根节点的左子节点或者右子节点
     * 4. 如果左子节点或者右子节点存在，则重复2-3步骤，此时x遍历的是以左子节点或者右子节点为根节点的子树。
     * @author poles
     * @date 2020-05-21 10:40:12
     * @param key Value
     * @param value Value
     * @return 整棵树
     */
    public Node<Key, Value> put(Key key, Value value){
        if(key == null) return root;
        root = put(this.root, key, value);
        return root;
    }

    /**
     * 在root树中增加node节点， 然后返回新树
     * @author poles
     * @date 2020-05-21 10:55:14
     * @param node 子树
     * @param node
     */
    private Node<Key, Value> put(Node<Key,Value> node, Key key, Value value) {
        //根节点为空，直接赋值为根节点
        if(node == null){
            size++;
            return new Node<>(key, value, null, null);
        }

        //1. 比较key值大小
        int result = key.compareTo(node.key);
        if(result == 0){
            //如果key值相等，直接替换value值
            node.value = value;
        }else if(result > 0){
            //如果node的key值大于root的key值， 遍历并右子树，然后返回右子树
            node.right = put(node.right, key, value);
        }else {
            //如果node的key值小于root的key值，遍历并左子树，然后返回左子树
            node.left = put(node.left, key, value);
        }

        return node;
    }


    /**
     * 根据Key获取其对应的Value值
     * 1. 从root开始遍历，挨个比较key值，如果相等则返回value值，如果比root的key小，则遍历左子树，否则遍历右子树
     * 2. 直到找到Value为止，如果不存在则返回null
     * @author liyanlong
     * @date 2020-05-21 16:03:40
     * @param key
     * @return Value
     */
    public Value get(Key key){
        if(key == null) return null;
        return get(root, key);
    }

    private Value get(Node<Key, Value> node, Key key){
        if(node == null) return null;

        int result = key.compareTo(node.key);
        if(result == 0){
            return node.value;
        }else if(result > 0){
            //遍历右子树
            return get(node.right, key);
        }else{
            //遍历左子树
            return get(node.left, key);
        }
    }

    /**
     * 删除Key对应的节点
     * 删除思想：删除节点时，不能直接断开连接，因为该节点可能存在左子树和右子树， 需要做以下操作：
     *    1. 如果该节点是叶子节点，则直接断开跟其父节点的链接即可
     *    2. 如果只有左子树或者只有右子树，则往上提就可以了，即root.left = deleteNode.left 或者 root.right = deleteNode.right
     *    3. 如果存在左右子树，则使用右子树的最小节点（从右子树开始的左节点的左节点...直到左节点为null为止）替换将要删除的节点即可
     *    4. 记得删除拯救了地球的那个节点
     *
     * 注意：
     * 删除一个节点，无非就是在三个地方：左子树、根节点、右子树。
     * 如果在左子树或者右子树，则采用递归方式：将当前节点的左子树或者右子树重新赋值，赋值为删除了key节点的新的左子树或者右子树
     * 递归停止的条件为：要删除的key等于根节点，无论如何都要走到这一步。 如果删除根节点是，只有右子树或者左子树，则右子树或者左子树的根节点以移即可，
     * 如果左右子树都存在，则找到右子树的最小节点（右子树的左子树的左子树....的左子树），然后删掉这个最小的节点，然后用这个最小的节点替换根节点，返回这棵处理过后的树
     * @author liyanlong
     * @date 2020-05-21 16:16:01
     * @param key
     * @return 返回删除节点之后的整棵树
     */
    public Node<Key, Value> remove(Key key){
        if(key == null) return root;
        return remove(root, key);
    }

    /**
     * 从node树中删除key节点
     */
    private Node<Key, Value> remove(Node<Key, Value> node, Key key){
        if(node == null){
            //node树为null
            return node;
        }

        int result = key.compareTo(node.key);

        if(result > 0){
            //遍历右子树，然后node.right这棵树删除key以后返回新的树，让新的树作为当前节点的右子树即可
            node.right = remove(node.right, key);
        }else if(result < 0){
            //遍历左子树，一定要注意这个赋值号的精华所在。node.left，即当前节点的左子树重新赋值为删除了key之后返回的左子树，这就是精华
            node.left = remove(node.left, key);
        }else{
            //此时key等于node几点的键，这里要完成具体的删除操作
            size--;

            //删除的节点只有右子树或者左子树，则根节点上移即可，即直接返回key这个节点对应的右子树或者左子树即可。
            if(node.left == null){
                //此时已经完成了删除，所以这里size需要减1
                return node.right;
            }else if(node.right == null){
                //这里也已经完成了删除，所以这里size也需要减1
                return node.left;
            }

            //删除节点的左右子树都存在，则找到右子树的最小节点，替换要删除的节点，然后删除那个右子树最小的节点
            Node<Key, Value> current = node.right;
            Node<Key, Value> minNode = node.right;
            while(current.left != null){
                if(current.left.left == null){
                    //表示已经找到最小节点了，就是current.left
                    minNode = current.left;

                    //删除最小节点
                    current.left = null;
                }else{
                    current = current.left;
                }
            }

            //使用最小节点替换，要删除节点
//                    replace(minNode, node); 事实证明这里有问题，但没仔细研究
            //让node节点的左右子树成为minNode的左右子树（还得判断这个最小值恰好就是要删除的这个节点的右子节点的情况）
            minNode.left = node.left;

            if(node.right != minNode){  //如果这个最小值就是当前要删除的节点的右子树，最小节点就不赋值了
                minNode.right = node.right;
            }
            //让node节点的父节点执行minNode（因为是递归，所以直接返回就会被赋值给上一个节点）
            node = minNode;
        }

        return node;
    }


    /**
     * 使用node1覆盖接管node2， 即用node2替换node1
     * @author liyanlong
     * @date 2020-07-13 15:45:43
     * @param node1 node1
     * @param node2 node2
     * @return void
     */
    private void replace(Node<Key,Value> node1, Node<Key,Value> node2) {
        node1.left = node2.left;
        node1.right = node2.right;
        node1.key = node2.key;
        node1.value = node2.value;
    }
}
