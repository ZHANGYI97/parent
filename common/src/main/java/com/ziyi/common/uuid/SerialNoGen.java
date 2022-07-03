package com.ziyi.common.uuid;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ziyi.common.utils.AutoIncreaseCounter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * 序列号生成器
 * 依托于 redis缓存实现
 *
 * @author zhy
 * @date 2022/7/3
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SerialNoGen {

    /**
     * 计数器
     */
    private AutoIncreaseCounter counter;

    /**
     * 生成按天流水号
     *
     * @param prefix    前缀
     * @param serialBit 流水号长度
     * @return 流水号
     */
    public String genDaySerialNo(String prefix, int serialBit) {
        return genSerialNo(prefix, DAILY, serialBit, 0, StrUtil.EMPTY);
    }

    /**
     * 生成按周流水号
     *
     * @param prefix    前缀
     * @param serialBit 流水号长度
     * @return 流水号
     */
    public String genWeekSerialNo(String prefix, int serialBit) {
        return genSerialNo(prefix, WEEK, serialBit, 0, StrUtil.EMPTY);
    }

    /**
     * 生成按月流水号
     *
     * @param prefix    前缀
     * @param serialBit 流水号长度
     * @return 流水号
     */
    public String genMonthSerialNo(String prefix, int serialBit) {
        return genSerialNo(prefix, MONTH, serialBit, 0, StrUtil.EMPTY);
    }

    /**
     * 生成序列号
     *
     * @param prefix    前缀
     * @param strategy  生成策略
     * @param serialBit 序列长度
     * @param randomBit 随机数长度
     * @return 流水号
     */
    public String genSerialNo(String prefix, SerialStrategy strategy, int serialBit, int randomBit, String suffix) {
        Date curDate = new Date();
        // 通过Redis缓存获取当前流水号
        String serialNoPrefix = prefix + strategy.dateInfix(curDate);
        Long curNo = counter.increment(serialNoPrefix, strategy.calcExpire(curDate));
        // 需要追加随机码则添加随机码
        String randomStr = StrUtil.EMPTY;
        if (randomBit > 0) {
            randomStr = RandomUtil.randomNumbers(randomBit);
        }
        return prefix + String.format("%0" + serialBit + "d", curNo) + randomStr + suffix;
    }

    // region 序列策略
    /**
     * 每月
     */
    public static final SerialStrategy MONTH = new SerialStrategy() {
        @Override
        public String dateInfix(Date date) {
            return DateUtil.format(date, "yyMM");
        }

        @Override
        public int calcExpire(Date date) {
            return DateUtil.second(DateUtil.beginOfMonth(DateUtil.nextMonth())) - DateUtil.second(date);
        }
    };

    /**
     * 每天
     */
    public static final SerialStrategy DAILY = new SerialStrategy() {
        @Override
        public String dateInfix(Date date) {
            return DateUtil.format(date, "yyMMdd");
        }

        @Override
        public int calcExpire(Date date) {
            return DateUtil.second(DateUtil.beginOfDay(DateUtil.tomorrow())) - DateUtil.second(date);
        }
    };

    /**
     * 每周
     */
    public static final SerialStrategy WEEK = new SerialStrategy() {
        @Override
        public String dateInfix(Date date) {
            String year = DateUtil.format(DateUtil.endOfWeek(date, true), "yy");
            int week = DateUtil.weekOfYear(DateUtil.endOfWeek(date, true));
            return year + String.format("%02d", week);
        }

        @Override
        public int calcExpire(Date date) {
            return DateUtil.second(DateUtil.beginOfWeek(DateUtil.nextWeek())) - DateUtil.second(date);
        }
    };

    /**
     * 序列号策略
     */
    interface SerialStrategy {
        /**
         * 日期中缀
         *
         * @param date 计算时间
         * @return 日期中缀
         */
        String dateInfix(Date date);

        /**
         * 计算失效
         *
         * @param date 计算时间
         * @return 失效时间
         */
        int calcExpire(Date date);
    }
    // endregion

}
