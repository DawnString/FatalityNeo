package cn.dawnstring.fatality.item;

/**
 * 翅膀飞行数据
 * @param maxHorizontalSpeed 最大水平速度
 * @param horizontalAcceleration 水平加速度
 * @param horizontalDrag 无输入时减速系数
 * @param maxVerticalSpeed 最大垂直速度
 * @param upwardAcceleration 上升加速度（每 tick）
 * @param maxFlightTime 最大飞行时间（tick）
 * @param glideSpeed 滑翔缓降速度（每 tick 最大下落）
 */
public record WingStats(
        double maxHorizontalSpeed,
        double horizontalAcceleration,
        double horizontalDrag,
        double maxVerticalSpeed,
        double upwardAcceleration,
        int maxFlightTime,
        double glideSpeed
) {
}
