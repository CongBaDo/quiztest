package nemo.com.androidquiz.model;

/**
 * Created by doba on 5/26/17.
 */

public class BaseResponse {

    private ErrorCase code;

    public class ErrorCase{
        private String code;
        private String description;

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    public ErrorCase getCode() {
        if(code == null){
            return null;
        }
        return code;
    }
}
