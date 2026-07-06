package cn.dawnstring.fatality.utils;

import java.util.Random;

public class RandomUtil
{
    private static final Random RANDOM = new Random();

    //传入概率值，返回概率，使用int类型
    //输出小于percent/100的概率
    public static boolean hitProbability(int percent)
    {
        if (percent <= 0)
            return false;
        if (percent >= 100)
            return true;

        return RANDOM.nextInt(100) < percent;
    }
}
