package johan.com.fibonaccilist;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.math.BigInteger;

public class ItemListViewModel {
    public ObservableField<String> screenTitle = new ObservableField<>();
    public ObservableArrayList<ItemViewModel> items = new ObservableArrayList<>();
    public int count = -1;
    int scrollcount = 0;
    RecyclerView recyclerView;

    //Il était obligatoire d'utiliser des BigInteger, les nombres étant beaucoup trop grands pour de simples int.
    BigInteger a = BigInteger.valueOf(1);
    BigInteger b = BigInteger.valueOf(0);
    BigInteger temp;
    String isTapped;
    int currentPos;
    int fiveHundredMax = 480;


    //Je génère les nombres de Fibonacci dans la liste 500 par 500.
    public ItemListViewModel(RecyclerView theRecyclerView) {
        recyclerView = theRecyclerView;
        screenTitle.set("Suite de Fibonacci infinie !");

        //500 premiers éléments
        generateFiveHItem();

            //A chaque fois que je scroll la liste, cette fonction va checker si on se rapproche de 500. Si on est a 440 ou plus, on va générer 500 nombres de plus.
            //Au départ je voulais générer les nombres en même temps que l'on scroll au fur et a mesure mais cela posait trop de problèmes au niveau du placement de
            //la position des éléments dans le recyclerview.
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                    public void onScrolled (RecyclerView recyclerView,int dx, int dy){
                        currentPos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        Log.i("Scroll", "Position : " + currentPos);
                        if (currentPos >= fiveHundredMax - 40) {
                            recyclerView.stopNestedScroll();
                            recyclerView.stopScroll();
                            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(fiveHundredMax, 0);
                            if(currentPos >= fiveHundredMax){
                                generateAndScroll();
                                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(fiveHundredMax-500, 0);
                            }
                        }
                    }
            });

        }

    public void generateAndScroll(){
        Log.i("Scroll", "currentPos == fiveHundredMax");
        fiveHundredMax += 500;
        generateFiveHItem();
        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(fiveHundredMax - 500, 0);
    }

    public void generateFiveHItem(){
        for(int i= 0; i<= 500; i++) {
            generateItem();
        }

    }

    public void generateItem() {

        temp = a;
        a = a.add(b);
        b = temp;
        count++;
        items.add(count, new ItemViewModel(b));
    }

    public void onItemTapped(Object item){
        isTapped = ((ItemViewModel)item).title.get();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).title.get() == isTapped) {
                i++;
                Toast.makeText(recyclerView.getContext(),
                        "Nombre de Fibonacci n°" + i, Toast.LENGTH_LONG).show();
            }
        }
    }
}
