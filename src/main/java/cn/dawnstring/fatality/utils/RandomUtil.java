package cn.dawnstring.fatality.utils;

import java.util.Random;

/**
 * 随机数工具
 */
public class RandomUtil
{
    private static final Random RANDOM = new Random();

    /**
     * 概率计算
     * @param percent 你要的概率*100
     * @return 返回percent/100的概率
     */
    public static boolean hitProbability(int percent)
    {
        if (percent <= 0)
            return false;
        if (percent >= 100)
            return true;

        return RANDOM.nextInt(100) < percent;
    }
}
