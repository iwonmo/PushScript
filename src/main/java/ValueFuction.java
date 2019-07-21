import java.util.List;

/** 循环接口方法 */
@FunctionalInterface
interface ValueFuction {
    public void value(String _key, String _data, Long _time, Long _utime, String _type);
}
/** 循环且获取过期事件 */
@FunctionalInterface
interface ValueFuctionRetList {
    public void value(String _key, String _data, Long _time, Long _utime, String _type, List<PushType> list);
}


