package com.example.xunyou.test;

public class Heap {
    private int[] data;//存储堆的数组
    private int size;//堆中元素的数量
    public Heap(int capacity){
        data = new int[capacity];//初始化数组
        size = 0;//初始化数量
    }

    public void sel(){
        for (int i=0 ; i< data.length;i++){
            System.out.println(data[i]);

        }
    }


    /**
     * 插入元素
     */
    public void insert(int value) throws Exception{
        if(size == data.length)
            throw new Exception("堆已满");
        else{
            data[size] = value;//将新插入的元素放在堆的末尾
            int i = size;
            size ++;
            while(i > 0){//对堆进行调整，直至满足条件
                int p = (i - 1) / 2;
                if(data[i] < data[p]){
                    int temp = data[i];
                    data[i] = data[p];
                    data[p] = temp;
                    i = p;
                }
                else
                    break;
            }
        }
    }

    /**
     * 删除堆中的元素
     * @return
     * @throws Exception
     */
    public int delMin() throws Exception{
        int res;
        if(size == 0)
            throw new Exception("为空");
        else{
            res = data[0];//返回索引为0的元素
            size -- ;
            data[0] = data[size];//将堆中最后一个元素填充至索引为0的位置
            int i = 0;
            while(2 * i + 1 < size){//对堆进行调整
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                if(right < size && data[right] < data[left] && data[right] < data[i]){
                    int temp = data[i];
                    data[i] = data[right];
                    data[right] = temp;
                    i = right;
                }
                else if(data[left] < data[i] && (right >= size || data[right] >= data[left])){
                    int temp = data[i];
                    data[i] = data[left];
                    data[left] = temp;
                    i = left;
                }
                else
                    break;
            }
        }
        return res;
    }

    //测试
    public static void main(String[] args) throws Exception {
        Heap heap = new Heap(10);
        heap.insert(9);
        heap.insert(5);
        heap.insert(4);
        heap.insert(3);
        heap.insert(6);
        heap.insert(2);
        heap.sel();
//        System.out.println(heap.delMin());
//        System.out.println(heap.delMin());
//        System.out.println(heap.delMin());
//        System.out.println(heap.delMin());
//        System.out.println(heap.delMin());
//        System.out.println(heap.delMin());
    }
}
