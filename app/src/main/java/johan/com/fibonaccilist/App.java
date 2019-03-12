package johan.com.fibonaccilist;

import android.app.Application;
import android.databinding.DataBindingUtil;
import johan.com.fibonaccilist.helpers.RecyclerViewDataBinding;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataBindingUtil.setDefaultComponent(new android.databinding.DataBindingComponent() {
                                                @Override
                                                public RecyclerViewDataBinding getRecyclerViewDataBinding() {
                                                    return new RecyclerViewDataBinding();
                                                }
                                            }
        );
    }
}
