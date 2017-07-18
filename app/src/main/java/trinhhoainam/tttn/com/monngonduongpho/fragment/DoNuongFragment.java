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

import trinhhoainam.tttn.com.monngonduongpho.R;
import trinhhoainam.tttn.com.monngonduongpho.activity.DataBaseHandler;
import trinhhoainam.tttn.com.monngonduongpho.adapter.RecyclerAdapter;
import trinhhoainam.tttn.com.monngonduongpho.item.ItemFood;

/**
 * Created by HoaiNam on 24/02/2017.
 */

public class DoNuongFragment extends Fragment {
    private RecyclerAdapter adapterDoNuong;
    private ArrayList<ItemFood> listDoNuong;
    private TextView txtDoNuong;
    private RecyclerView recyclerDoNuong;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_do_nuong, container, false);
        txtDoNuong = (TextView) v.findViewById(R.id.txt_do_nuong);
        recyclerDoNuong = (RecyclerView) v.findViewById(R.id.recycler_donuong);

        new LoadDoNuong().execute();
        return v;
    }

    public class LoadDoNuong extends AsyncTask<Void, Void, ArrayList<ItemFood>> {
        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity(), "Load Do Nuong", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<ItemFood> list) {
            recyclerDoNuong.setHasFixedSize(true);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerDoNuong.setLayoutManager(manager);
            adapterDoNuong = new RecyclerAdapter(listDoNuong, getActivity());
            recyclerDoNuong.setAdapter(adapterDoNuong);

            if (listDoNuong.size() == 0) {
                txtDoNuong.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(list);
        }

        @Override
        protected ArrayList<ItemFood> doInBackground(Void... params) {
            listDoNuong = (ArrayList<ItemFood>) new DataBaseHandler(getActivity()).getFoodCode("_food_code", 2);
            return listDoNuong;
        }
    }
}
