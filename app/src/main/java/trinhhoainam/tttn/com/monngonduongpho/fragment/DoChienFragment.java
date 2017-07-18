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

public class DoChienFragment extends Fragment {
    private RecyclerView recyclerDoChien;
    private RecyclerAdapter adapterDoChien;
    private ArrayList<ItemFood> listDoChien;
    private TextView txtDoChien;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_do_chien, container, false);


        new LoadDoChien().execute();
        return v;
    }

    public class LoadDoChien extends AsyncTask<Void, Void, ArrayList<ItemFood>> {
        @Override
        protected void onPreExecute() {
//            Toast.makeText(getActivity(),listDoChien.size()+ "Load do chien", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<ItemFood> list) {
            txtDoChien = (TextView) getActivity().findViewById(R.id.txt_do_chien);
            recyclerDoChien = (RecyclerView) getActivity().findViewById(R.id.recycler_dochien);
            recyclerDoChien.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerDoChien.setLayoutManager(layoutManager);
            adapterDoChien = new RecyclerAdapter(listDoChien, getActivity());
            recyclerDoChien.setAdapter(adapterDoChien);
            Toast.makeText(getActivity(),listDoChien.size()+ "Load do chien", Toast.LENGTH_SHORT).show();
            if (listDoChien.size() == 0) {
                txtDoChien.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(list);
        }

        @Override
        protected ArrayList<ItemFood> doInBackground(Void... params) {
            listDoChien = (ArrayList<ItemFood>) new DataBaseHandler(getActivity()).getFoodCode("_food_code", 3);
            return listDoChien;
        }
    }
}
