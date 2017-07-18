package trinhhoainam.tttn.com.monngonduongpho.fragment;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trinhhoainam.tttn.com.monngonduongpho.R;
import trinhhoainam.tttn.com.monngonduongpho.activity.DataBaseHandler;
import trinhhoainam.tttn.com.monngonduongpho.adapter.RecyclerAdapter;
import trinhhoainam.tttn.com.monngonduongpho.item.ItemFood;

/**
 * Created by HoaiNam on 13/02/2017.
 */

public class YeuThichFragment extends Fragment {
    private RecyclerView recyclerFood;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<ItemFood> arrayList;
    private DataBaseHandler dbHandler;
    private TextView txtInfo;

    public YeuThichFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_yeuthich, container, false);
        new LoadFavorite().execute();
        return v;
    }

    public class LoadFavorite extends AsyncTask<Void, Void, List<ItemFood>> {

        @Override
        protected void onPostExecute(List<ItemFood> itemFoods) {
            recyclerFood = (RecyclerView) getActivity().findViewById(R.id.recycler_favorites);
            txtInfo = (TextView) getActivity().findViewById(R.id.txtThongBao);
            if (arrayList.size() == 0) {
                txtInfo.setVisibility(View.VISIBLE);
            }

            recyclerFood.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerFood.setLayoutManager(layoutManager);
            recyclerAdapter = new RecyclerAdapter(arrayList, getActivity());
            recyclerFood.setAdapter(recyclerAdapter);
            super.onPostExecute(itemFoods);
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity(), "load Favorites", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected List<ItemFood> doInBackground(Void... params) {
            dbHandler = new DataBaseHandler(getActivity());
            arrayList = (ArrayList<ItemFood>) dbHandler.getFoodCode("_favorites", 1);
            return arrayList;
        }
    }
}
