
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 结构管理类 同步锁会锁住整个Class 非静态下会锁住整个类 导致其余的操作等待
 */
public class PushTypeClass {
    private ConcurrentHashMap<String, PushType> _PushType = new ConcurrentHashMap();

    public synchronized void set(String key, String _Data, Long _Time,Long _Utime,String _Type) {
        PushType pushType = new PushType(_Data, _Time,_Utime,_Type);
        /** 如果Key已经存在则直接更新 */
        _PushType.put(key, pushType);
    }

    public synchronized PushType get(String key) {
        return _PushType.get(key);
    }

    public synchronized long getCount() {
        return _PushType.size();
    }

    public synchronized void removeKey(String _key) {
        _PushType.remove(_key);
    }

    public synchronized void clear(){
        _PushType.clear();
    }

    public synchronized void forEach(ValueFuction valueFuction) {
        for (Map.Entry<String, PushType> entry : _PushType.entrySet()) {
            PushType values = entry.getValue();
            valueFuction.value(entry.getKey(), values.get_Data(), values.get_Time(),values.get_Utime(),values.get_Type());
        }
    }

}
