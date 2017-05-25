package nemo.com.androidquiz.restmanager;

/**
 * Created by bado on 5/25/17.
 */

public class CommonInterface<T> {
    public interface ModelResponse<T> {
        void onSuccess(T result);
        void onFail();
    }

}
