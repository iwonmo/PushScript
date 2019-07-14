public class PushType {
    private String _Data;
    private Long _Time;

    public PushType(String Data_, Long Time_) {
        this._Data = Data_;
        this._Time = Time_;
    }

    public Long get_Time() {
        return this._Time;
    }

    public String get_Data() {
        return this._Data;
    }
}
