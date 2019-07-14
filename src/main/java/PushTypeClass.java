import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** 结构管理类 */
public class PushTypeClass {
    private ConcurrentHashMap<String, PushType> _PushType = new ConcurrentHashMap();

    public void set(String key, String _Data, Long _Time) {
        PushType pushType = new PushType(_Data, _Time);
        _PushType.put(key, pushType);
    }

    public PushType get(String key) {
        return _PushType.get(key);
    }

    public long getCount() {
        return _PushType.size();
    }

    public void removeKey(String _key) {
        _PushType.remove(_key);
    }

    public synchronized void forEach(ValueFuction valueFuction) {
        for (Map.Entry<String, PushType> entry : _PushType.entrySet()) {
            PushType values = entry.getValue();
            valueFuction.value(entry.getKey(), values.get_Data(), values.get_Time());
        }
    }

}
