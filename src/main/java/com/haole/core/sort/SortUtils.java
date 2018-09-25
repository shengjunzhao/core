package com.haole.core.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shengjunzhao on 2018/9/1.
 */
public class SortUtils {


    private static <T extends Comparable<? super T>> void swap(List<T> list, int i, int j) {
        T m;
        m = list.get(i);
        list.set(i, list.get(j));
        list.set(j, m);
    }

    /**
     * 简单选择排序是最简单直观的一种算法，基本思想为每一趟从待排序的数据元素中选择最小（或最大）的一个元素作为首元素，
     * 直到所有元素排完为止，简单选择排序是不稳定排序。
     * 时间复杂度为O(n^2)
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void selectSort(List<T> list) {
        selectSort(list, 0, list.size() - 1);
    }

    public static <T extends Comparable<? super T>> void selectSort(List<T> list, int left, int right) {
        T m;
        for (int i = left; i < right; i++) {
            //每一趟循环比较时，min用于存放较小元素的数组下标，这样当前批次比较完毕最终存放的就是此趟内最小的元素的下标，
            // 避免每次遇到较小元素都要进行交换。
            int min = i;
            for (int j = i + 1; j <= right; j++) {
                if (list.get(j).compareTo(list.get(min)) == -1)
                    min = j;
            }
            // 进行交换，如果min发生变化，则进行交换
            if (min != i) {
                m = list.get(i);
                list.set(i, list.get(min));
                list.set(min, m);
            }
        }
    }

    /**
     * 冒泡排序的基本思想是，对相邻的元素进行两两比较，顺序相反则进行交换，这样，每一趟会将最小或最大的元素“浮”到顶端，
     * 最终达到完全有序
     * 时间复杂度为O(n^2)
     * 冒泡排序性能还还是稍差于简单选择排序的
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void bubbleSort(List<T> list) {
        bubbleSort(list, 0, list.size() - 1);
    }

    public static <T extends Comparable<? super T>> void bubbleSort(List<T> list, int left, int right) {
        T m;
        for (int i = left; i < right; i++) {
            //设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已然完成。
            boolean flag = true;
            for (int j = 0; j < right - i; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) == 1) {
                    m = list.get(j);
                    list.set(i, list.get(j + 1));
                    list.set(j + 1, m);
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    /**
     * 直接插入排序基本思想是每一步将一个待排序的记录，插入到前面已经排好序的有序序列中去，直到插完所有元素为止。
     * 时间复杂度为O(n^2)
     * 插入排序还是要优于上面两种排序的
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void insertionSort(List<T> list) {
        insertionSort(list, 0, list.size() - 1);
    }

    public static <T extends Comparable<? super T>> void insertionSort(List<T> list, int left, int right) {
        T m;
        for (int i = left + 1; i <= right; i++) {
            int j = i;
            while (j > 0 && (list.get(j).compareTo(list.get(j - 1)) == -1)) {
                m = list.get(j);
                list.set(i, list.get(j - 1));
                list.set(j - 1, m);
                j--;
            }
        }
    }

    /**
     * 希尔排序是希尔（Donald Shell）于1959年提出的一种排序算法。希尔排序也是一种插入排序，
     * 它是简单插入排序经过改进之后的一个更高效的版本，也称为缩小增量排序，同时该算法是冲破O(n2）的第一批算法之一
     * <p>
     * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，
     * 每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
     * <p>
     * https://www.cnblogs.com/chengxiao/p/6104371.html
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void shellSort(List<T> list) {
        shellSort(list, 0, list.size() - 1);
    }

    public static <T extends Comparable<? super T>> void shellSort(List<T> list, int left, int right) {
        int len = right - left + 1;
        T m;
        //增量gap，并逐步缩小增量
        for (int gap = len / 2; gap > 0; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for (int i = gap; i < right + 1; i++) {
                int j = i;
                while (j - gap >= 0 && list.get(left + j).compareTo(list.get(left + j - gap)) < 0) {
                    //插入排序采用交换法
                    m = list.get(left + j);
                    list.set(left + j, list.get(left + j - gap));
                    list.set(left + j - gap, m);
                    j -= gap;
                }
            }
        }
    }

    /**
     * 快速排序是一种效率非常高的算法，在快速排序的算法里面用到“二分”的思想，
     * 每次循环后都以准基数为分界左边是比准基数小的，右边是比准基数大的。
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void quickSort(List<T> list) {
        quickSort(list, 0, list.size() - 1);
    }

    public static <T extends Comparable<? super T>> void quickSort(List<T> list, int left, int right) {
        int i, j;
        T temp, t;
        if (left > right)
            return;
        temp = list.get(left);
        i = left;
        j = right;
        while (i != j) {
            while (list.get(j).compareTo(temp) >= 0 && i < j)
                j--;
            while (list.get(i).compareTo(temp) <= 0 && i < j)
                i++;
            if (i < j) {
                t = list.get(i);
                list.set(i, list.get(j));
                list.set(j, t);
            }
        }
        list.set(left, list.get(i));
        list.set(i, temp);
        quickSort(list, left, i - 1);
        quickSort(list, i + 1, right);
    }

    /**
     * 堆排序
     * 堆排序是利用堆这种数据结构而设计的一种排序算法，堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)，
     * 它也是不稳定排序
     * <p>
     * 堆是具有以下性质的完全二叉树：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
     * 或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
     * 大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
     * 小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
     * 堆排序的基本思想是：将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。将其与末尾元素进行交换，
     * 此时末尾就为最大值。然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。
     * 如此反复执行，便能得到一个有序序列了
     * <p>
     * https://www.cnblogs.com/chengxiao/p/6129630.html#4055277
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void headSort(List<T> list) {
        headSort(list, 0, list.size() - 1);
    }

    public static <T extends Comparable<? super T>> void headSort(List<T> list, int left, int right) {
        //1.构建大顶堆
        int len = right - left + 1;
        for (int i = len / 2 - 1; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(list, i, right + 1);
        }
        //2.调整堆结构+交换堆顶元素与末尾元素
        for (int j = len - 1; j > 0; j--) {
            swap(list, 0, j);//将堆顶元素与末尾元素进行交换
            adjustHeap(list, 0, j);//重新对堆进行调整
        }
    }

    public static <T extends Comparable<? super T>> void adjustHeap(List<T> list, int left, int right) {
        T temp = list.get(left);//先取出当前元素i
        for (int k = left * 2 + 1; k < right; k = k * 2 + 1) {//从i结点的左子结点开始，也就是2i+1处开始
            if (k + 1 < right && list.get(k).compareTo(list.get(k + 1)) == -1) {//如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            if (list.get(k).compareTo(temp) == 1) {//如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                list.set(left, list.get(k));
                left = k;
            } else {
                break;
            }
        }
        list.set(left, temp);//将temp值放到最终的位置
    }

    public static <T extends Comparable<? super T>> void mergeSort(List<T> list) {
        List<T> temp = new ArrayList<>(list.size());
        int i = 0;
        int len = list.size();
        while (i++<=len)
            temp.add(null);
        mergeSort(list, 0, list.size() - 1, temp);
    }

    private static <T extends Comparable<? super T>> void mergeSort(List<T> list, int left, int right, List<T> temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(list, left, mid, temp);//左边归并排序，使得左子序列有序
            mergeSort(list, mid + 1, right, temp);//右边归并排序，使得右子序列有序
            merge(list, left, mid, right, temp);//将两个有序子数组合并操作
        }
    }

    private static <T extends Comparable<? super T>> void merge(List<T> list, int left, int mid, int right, List<T> temp) {
        int i = left;//左序列指针
        int j = mid + 1;//右序列指针
        int t = 0;//临时数组指针
        while (i <= mid && j <= right) {
            if (list.get(i).compareTo(list.get(j)) <= 0) {
//                temp.add(list.get(i++));
                temp.set(t++, list.get(i++));
            } else {
//                temp.add(list.get(j++));
                temp.set(t++, list.get(j++));
            }
        }
        while (i <= mid) {//将左边剩余元素填充进temp中
//            temp.add(list.get(i++));
            temp.set(t++, list.get(i++));
        }
        while (j <= right) {//将右序列剩余元素填充进temp中
//            temp.add(list.get(j++));
            temp.set(t++, list.get(j++));
        }
        t = 0;
        //将temp中的元素全部拷贝到原数组中
        while (left <= right) {
            list.set(left++, temp.get(t++));
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        List<Integer> list = Arrays.asList(arr);
//        quickSort(list);
//        selectSort(list);
//        bubbleSort(list);
//        insertionSort(list);
//        shellSort(list);
//        headSort(list);
        mergeSort(list);
        for (Integer i : list)
            System.out.print(i + ",");
    }
}
