
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 结构管理类 同步锁会锁住整个Class 非静态下会锁住整个类 导致其余的操作等待
 */
public class PushTypeClass {
    private ConcurrentHashMap<String, PushType> _PushType = new ConcurrentHashMap();

    public   void set(String _key, String _Data, Long _Time, Long _Utime, String _Type) {
        PushType pushType = new PushType(_key, _Data, _Time, _Utime, _Type);
        /** 如果Key已经存在则直接更新 */
        _PushType.put(_key, pushType);
    }

    public   PushType get(String key) {
        return _PushType.get(key);
    }

    public   long getCount() {
        return _PushType.size();
    }

    public   void removeKey(String _key) {
        _PushType.remove(_key);
    }

    public   void clear() {
        _PushType.clear();
    }

    /**
     * 循环 并且返回过期事件
     */
    public   void forEach(ValueFuctionRetList valueFuction) {
        List<PushType> list = new ArrayList<PushType>();
        for (Map.Entry<String, PushType> entry : _PushType.entrySet()) {
            PushType values = entry.getValue();
            valueFuction.value(entry.getKey(), values.get_Data(), values.get_Time(), values.get_Utime(), values.get_Type(), list);
        }
        /** 处理过期事件 */
        for (PushType pushType__ : list) {
            _PushType.remove(pushType__.get_Key());
            if (pushType__.get_Type().equals("uptime"))
                _PushType.put(pushType__.get_Key(), new PushType(pushType__.get_Key(), pushType__.get_Data(), pushType__.get_Time() + pushType__.get_Utime(), pushType__.get_Utime(), pushType__.get_Type()));
            pushType__ = null;
        }
        list = null;
    }

    /**
     * 循环不处理事件
     */
    public   void forEach(ValueFuction valueFuction) {
        for (Map.Entry<String, PushType> entry : _PushType.entrySet()) {
            PushType values = entry.getValue();
            valueFuction.value(entry.getKey(), values.get_Data(), values.get_Time(), values.get_Utime(), values.get_Type());
        }
    }

}
