package com.itheima.takeout.ui;

import com.itheima.takeout.model.net.bean.GoodsInfo;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ============================================================
 * Copyright：JackChan和他的朋友们有限公司版权所有 (c) 2017
 * Author：   JackChan
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChan1999
 * GitBook：  https://www.gitbook.com/@alleniverson
 * CSDN博客： http://blog.csdn.net/axi295309066
 * 个人博客： https://jackchan1999.github.io/
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：Takeout
 * Package_Name：com.itheima.takeout
 * Version：1.0
 * time：2017/5/30 13:52
 * des ：购物车管理者
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */

public class ShoppingCartManager {
    private static ShoppingCartManager instance;

    private ShoppingCartManager() {
    }

    public static ShoppingCartManager getInstance() {
        if (instance == null) {
            instance = new ShoppingCartManager();
        }
        return instance;
    }


    /**
     * a)	商品列表（所在分类的标识信息）
     * 增加和减少的操作均会涉及到频繁查询、添加、删除
     * CopyOnWriteArrayList集合进行处理
     * b)	商家信息：标识、名称、logo链接
     * c)	记录并计算商品总价和商品数量
     */

    public CopyOnWriteArrayList<GoodsInfo> goodsInfos = new CopyOnWriteArrayList<>();
    public long sellerId;
    public String name;
    public String url;
    public int sendPrice;

    private Integer totalNum = 0;
    private Integer money = 0;// 保存到分（分）


    /**
     * 添加
     *
     * @param info
     */
    public Integer addGoods(GoodsInfo info) {
        int num = 0;
        // 判断容器中是否含有该商品
        // 如果有做++
        // 如果没有，添加一条记录

        boolean isContain = false;
        for (GoodsInfo item : goodsInfos) {
            if (item.id == info.id) {
                item.count++;
                num = item.count;
                isContain = true;
                break;
            }
        }

        if (!isContain) {
            num = info.count = 1;
            goodsInfos.add(info);
        }

        return num;
    }

    /**
     * 减少操作
     *
     * @param info
     */
    public Integer minusGoods(GoodsInfo info) {
        Integer num = 0;
        // 做- -
        // 判断：减少后数量是为0
        // 为0 做删除商品处理

        for (GoodsInfo item : goodsInfos) {
            if (item.id == info.id) {
                item.count--;
                if (item.count == 0) {
                    goodsInfos.remove(item);
                }
                num = item.count;
                break;
            }
        }
        return num;
    }

    /**
     * 获取商品总数
     * @return
     */
    public Integer getTotalNum() {
        totalNum=0;
        for (GoodsInfo item : goodsInfos) {
            totalNum += item.count;
        }
        return totalNum;
    }

    public Integer getMoney(){
        money=0;
        for (GoodsInfo item : goodsInfos) {
            money += (int)(item.newPrice*100);
        }
        return money;
    }

    /**
     * 情况所有选中的商品
     */
    public void clear() {
        goodsInfos.clear();
    }

    /**
     * 依据标识获取商品数量信息
     * @param id
     */
    public Integer getGoodsIdNum(int id) {
        for (GoodsInfo item : goodsInfos) {
            if (id == item.id){
                return item.count;
            }
        }
        return 0;
    }
}
