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

public class GiaiKhatFragment extends Fragment {
    private ArrayList<ItemFood> giaiKhatList;
    private RecyclerView recyclerGiaiKhat;
    private RecyclerAdapter adapterGiaiKhat;
    private TextView txtTrangThai;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_giai_khat, container, false);
        txtTrangThai = (TextView) v.findViewById(R.id.txt_giai_khat);
        recyclerGiaiKhat = (RecyclerView) v.findViewById(R.id.recycler_giaikhat);

        new LoadGiaiKhat().execute();
        return v;
    }

    public class LoadGiaiKhat extends AsyncTask<Void, Void, ArrayList<ItemFood>> {
        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity(), "Load giai khat", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<ItemFood> list) {
            super.onPostExecute(list);

            recyclerGiaiKhat.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerGiaiKhat.setLayoutManager(layoutManager);
            adapterGiaiKhat = new RecyclerAdapter(giaiKhatList, getActivity());
            recyclerGiaiKhat.setAdapter(adapterGiaiKhat);

            if (giaiKhatList.size() == 0) {
                txtTrangThai.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected ArrayList<ItemFood> doInBackground(Void... params) {
            giaiKhatList = (ArrayList<ItemFood>) new DataBaseHandler(getActivity()).getFoodCode("_food_code", 1);
            return giaiKhatList;
        }
    }

}
