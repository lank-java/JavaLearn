package com.lank.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author lank
 * @since 2020/10/27 16:08
 * redis缓存穿透布隆过滤器demo,使用guava的BloomFilter,缺点不能删除，存在误判
 */
public class BloomFilterDemo {

    private static final int total = 1000000;

    /**
     * funnel 数据类型
     * expectedInsertions 预期插入数据
     * fpp 错误率，默认0.03
     *strategy 哈希算法
     *
     */
    //错误率越大，所需空间和时间越小，错误率越小，所需空间和时间约大
    //private static final BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),total);
    private static final BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),total,0.001);


    public static void main(String[] args) {

        //1、把一百万数据添加到过滤器中
        for (int i=0;i<total;i++){
            bloomFilter.put(i);
        }

        //匹配在过滤器中的值，是否有匹配不上的
        for (int i=0;i<total;i++){
            if (!bloomFilter.mightContain(i)){
                System.out.println("数据"+i+"没有成功匹配上");
            }
        }

        int count = 0;
        for (int i=total;i<total+10000;i++){
            if (bloomFilter.mightContain(i)){
                count ++;
            }
        }
        System.out.println("误判数为："+count);
    }

}
