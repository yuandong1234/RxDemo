package com.yuong.rx;

public class DataBean {
    public String reason;
    public Data result;

    public class Data {
        public String stat;

        @Override
        public String toString() {
            return "Data{" +
                    "stat='" + stat + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
