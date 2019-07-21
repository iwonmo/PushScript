public class PushType {
    private String _Key;
    private String _Data; /** 数据 */
    private Long _Time; /** 过期时间 */
    private Long _Utime; /** 过期时间 */
    private String _Type; /** 事件类型 none:过期删除 uptime:过期时,更新过期时间 */

    public PushType(String key_,String Data_, Long Time_,Long Utime_,String Type_) {
        this._Data = Data_;
        this._Time = Time_;
        this._Utime=Utime_;
        this._Type=Type_;
        this._Key=key_;
    }

    public String get_Key() {
        return _Key;
    }
    public Long get_Utime() {
        return _Utime;
    }

    public String get_Type() {
        return _Type;
    }

    public Long get_Time() {
        return this._Time;
    }

    public String get_Data() {
        return this._Data;
    }
}
