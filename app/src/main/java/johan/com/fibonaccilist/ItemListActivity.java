package johan.com.fibonaccilist;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import johan.com.fibonaccilist.databinding.ActivityItemListBinding;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class ItemListActivity extends Activity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityItemListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_item_list);

        recyclerView = binding.getRoot().findViewById(R.id.data_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), VERTICAL));
        binding.setViewModel( new ItemListViewModel(recyclerView));
    }



}
