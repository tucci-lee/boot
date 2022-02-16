package com.tuccicode.boot.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 雪花算法生成id<p>
 * 长度64位<p>
 * 第一位为符号位，0<p>
 * 41位时间戳<p>
 * 10位workid, 0-1023<p>
 * 12位序列号，最大位4095<p>
 * <p>
 * 符号位    时间戳                                     workid   序列号
 * 0        00000000000000000000000000000000000000000 00000000 000000000000
 *
 * @author tucci.lee
 */
public final class SnowFlake implements IdGenerator {

    private final Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 开始时间
     */
    private static final long START_TIME = 1638798165440L;

    /**
     * 初始化的数据
     * workId: workid默认为0
     * sequence: 序列号
     * lastTime: 最后一次生成key的时间
     */
    private long workId = 0;
    private long sequence = 0L;
    private long lastTime = -1L;

    /**
     * 占用的位数
     * TIME_BIT 时间戳占用位数
     * WORK_BIT workid占用位数
     * SEQUENCE_BIT 序列号占用位数
     */
    private static final long TIME_BIT = 41L;
    private static final long WORK_BIT = 10L;
    private static final long SEQUENCE_BIT = 12L;

    /**
     * 左移的位数
     * WORK_LEFT_BIT workid左移12位
     * TIME_LEFT_BIT 时间戳左移22位
     */
    private static final long WORK_LEFT_BIT = SEQUENCE_BIT;
    private static final long TIME_LEFT_BIT = WORK_BIT + SEQUENCE_BIT;

    /**
     * 最大值，即二进制多少位1计算出的十进制
     */
    private static final long SEQUENCE_MAX = ~(-1L << SEQUENCE_BIT);
    private static final long WORK_MAX = ~(-1L << WORK_BIT);

    /**
     * workId默认为0，分布式需要设置不同的workId
     */
    public SnowFlake() {
    }

    public SnowFlake(long workId) {
        if (workId < 0 || workId > WORK_MAX) {
            log.error("workId Cannot be greater than " + WORK_MAX + " and less than 0");
            throw new IllegalArgumentException("workId Cannot be greater than " + WORK_MAX + " and less than 0");
        }
        this.workId = workId;
    }

    @Override
    public synchronized long nextId() {
        long currentTime = System.currentTimeMillis();
        //避免程序运行中，回退系统时间。造成id重复
        if (currentTime < lastTime) {
            log.error("System time cannot be rollback!");
            throw new RuntimeException("System time cannot be rollback!");
        }

        //如果当前时间和最后生成id的时间相同，则sequence+1，
        //如果sequence+1后大于SEQUENCE_MAX，则sequence=0，当前时间等待下一毫秒
        if (currentTime == lastTime) {
            sequence = (sequence + 1) & SEQUENCE_MAX;
            if (sequence == 0L) {
                while (currentTime <= lastTime) {
                    currentTime = System.currentTimeMillis();
                }
            }
        } else {
            // 随机从0和1开始，避免都是从0开始都是偶数
            sequence = (int) (Math.random() * 2);
        }
        //更新最后生成id的时间为当前时间
        lastTime = currentTime;

        return (currentTime - START_TIME) << TIME_LEFT_BIT  //时间戳部分
                | workId << WORK_LEFT_BIT                   //workid部分
                | sequence;                                 //序列号部分;
    }
}