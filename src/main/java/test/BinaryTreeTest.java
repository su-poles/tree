package test;

import com.poles.BinaryTree;

/**
*********************************************************************
* 
* @author poles
* @date 2020/7/14 6:31 下午
*
*********************************************************************
*/
public class BinaryTreeTest {
    public static void main(String[] args) {
        //1. 创建二叉查找树
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        //2. 插入
        tree.put(50, "恩格斯");
        tree.put(10, "撒西克");
        tree.put(70, "布鲁尔");
        tree.put(60, "恩格斯2");
        tree.put(65, "恩格斯2");
        tree.put(80, "吉本");
        tree.put(75, "吉本");
        tree.put(40, "莉莉丝");
        tree.put(90, "列宁");
        System.out.println("二叉树的元素个数：" + tree.size());


        //3. 获取
//        System.out.println("键值1对应的元素为：" + tree.get(1) );
//        System.out.println("键值2对应的元素为：" + tree.get(8) );
//        System.out.println("键值9对应的元素为：" + tree.get(9) );

        //4. 删除
        tree.remove(70);
        System.out.println("删除后的元素个数为：" + tree.size());
        System.out.println("键值8对应的元素为：" + tree.get(80));
    }
}
