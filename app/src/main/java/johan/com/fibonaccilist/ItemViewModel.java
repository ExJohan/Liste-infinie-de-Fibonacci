package johan.com.fibonaccilist;

import android.databinding.ObservableField;

import java.math.BigInteger;

public class ItemViewModel {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> description = new ObservableField<>();

    public ItemViewModel(BigInteger x){
        assign(x);
    }

    private void assign(BigInteger x) {
        title.set(String.valueOf(x));
    }

}