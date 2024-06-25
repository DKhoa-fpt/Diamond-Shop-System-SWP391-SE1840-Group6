package vn.fpt.diamond_shop.constants;

public enum StatusDelivery {
    WAITING("waiting for delivery",1),
    DELIVERING("being delivered",2),
    SUCCESS_DELIVERY("successful delivery",3),
    ;


    private String value;
    private int code;

    StatusDelivery(String value, int code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public enum StatusDeliver {
        ACTIVE("active",1),
        LEAVE("leave",2),
        INACTIVE("inactive",3)
        ;


        private String value;
        private int code;

        StatusDeliver(String value, int code) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
    }
}

