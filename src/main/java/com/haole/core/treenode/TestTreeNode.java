package com.haole.core.treenode;

import java.util.Random;

/**
 * ClassName: TestTreeNode
 * Description:
 * Author: shengjunzhao
 * Date: 2018/9/12 14:09
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class TestTreeNode {

    public static void main(String[] args) {
        Random r = new Random(23);
        Integer key = r.nextInt(1000);
        Integer value = r.nextInt(1000);
        TreeNode<Integer, Integer> hd = new TreeNode<>(TreeNode.hash(key), key, value, null);
        TreeNode<Integer, Integer> t, t1 = null;
        t = hd;
        TreeNode<Integer, Integer>[] newTab = (TreeNode<Integer, Integer>[]) new TreeNode[34];
        newTab[0] = t;
        for (int i = 0; i < 33; i++) {
            key = r.nextInt(1000);
            value = r.nextInt(1000);
            t1 = new TreeNode<>(TreeNode.hash(key), key, value, null);
            t.next = t1;
            t1.prev = t;
            newTab[i + 1] = t1;
            t = t1;
        }
//        227=148,false
//        683=247,false
//        18=43,false
//        290=310,false
//        581=595,false
//        94=687,false
//        689=370,false
//        381=886,false
//        124=630,false
//        622=617,false
//        412=297,false
//        244=25,false
//        664=934,false
//        711=130,false
//        113=739,false
//        832=322,false
//        300=47,false
//        809=542,false
//        791=666,false
//        319=744,false
//        453=716,false
//        158=861,false
//        80=677,false
//        231=618,false
//        418=52,false
//        768=655,false
//        400=492,false
//        680=892,false
//        756=584,false
//        749=616,false
//        381=422,false
//        612=904,false
//        117=481,false
//        165=668,false

        for (int i = 0; i < 34; i++) {
            TreeNode<Integer, Integer> tt = newTab[i];
            System.out.println(tt.key + "=" + tt.value + "," + tt.red); //+",left="+tt.left.key+",right="+tt.right.key
        }
        hd.treeify(newTab);
        System.out.println("==================");
        TreeNode<Integer, Integer> root = hd.root();
        hd.print(root,0);

    }
}
