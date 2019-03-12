package johan.com.fibonaccilist.helpers;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GenericAdapter<T> extends RecyclerView.Adapter<GenericAdapter.DataViewHolder<T>> {
    private List<T> items;
    private int itemTemplate;
    private ItemTappedListener action;

    private ViewDataBinding binding;
    public GenericAdapter(List<T> items, int itemTemplate, ViewDataBinding binding, ItemTappedListener action) {
        this.items = items == null ? new ArrayList<T>() : items; this.itemTemplate = itemTemplate; this.binding = binding; this.action = action;
    }
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final DataViewHolder holder = new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(itemTemplate, new FrameLayout(parent.getContext()), false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T item = items.get(holder.getLayoutPosition());
                if(item == null) {
                    Log.i("Logger", "Item is null");
                    return;
                }
                Class<?> type = item.getClass();
                setItem(binding, type, item);
                action.onTapped(null);
                setItem(binding, type,null);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) { holder.setViewModel(items.get(position)); }
    @Override
    public int getItemCount() { return this.items.size(); }
    static void setItem(Object binding, Class<?> type, Object item) {
        if (binding == null) return;
        Method setMethod = null;
        for (Method m: binding.getClass().getMethods()) {
            Class[] paramTypes = m.getParameterTypes();
            if(paramTypes.length == 1
                    && paramTypes[0] == type){
                setMethod = m;
                break;
            }
        }
        if(setMethod != null) { try { setMethod.invoke(binding, item); } catch (Exception e) {}}
    }
    static class DataViewHolder<T> extends RecyclerView.ViewHolder {
        ViewDataBinding binding;
        DataViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
        void setViewModel(T viewModel) {
            setItem(binding, viewModel.getClass(), viewModel);
        }
    }
    public interface ItemTappedListener {
        void onTapped(Object item);
    }

}