package trinhhoainam.tttn.com.monngonduongpho.fragment;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

public class TrangChuFragment extends Fragment {
    private ArrayList<ItemFood> arrayList;
    private DataBaseHandler dbHandler;
    private ProgressDialog dialog;

    int are = 0;
    private RecyclerView recyclerFood;
    private RecyclerAdapter recyclerAdapter;
    private TextView txtNote;

    public TrangChuFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_trangchu, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Thông báo !");
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        int areaCode = 0;
        Bundle b = getArguments();
        if (b != null) {
            areaCode = b.getInt("AREA_CODE", 0);
            // Toast.makeText(getActivity(), areaCode + "\nTrang chu fragment", Toast.LENGTH_SHORT).show();
        }

        if (areaCode == 0) {
            new LoadListAsyntask().execute();
        } else {
            new LoadLisFood().execute(areaCode);
        }


        return v;
    }

    public class LoadLisFood extends AsyncTask<Integer, Void, List<ItemFood>> {
        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<ItemFood> itemFoods) {
            if (arrayList.size() == 0) {
                txtNote = (TextView) getActivity().findViewById(R.id.txt_area_note);
                txtNote.setVisibility(View.VISIBLE);
            }
            recyclerFood = (RecyclerView) getActivity().findViewById(R.id.recycler_listfood);
            recyclerFood.setHasFixedSize(true);

            StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerFood.setLayoutManager(layoutManager);
            recyclerAdapter = new RecyclerAdapter(arrayList, getActivity());
            recyclerFood.setAdapter(recyclerAdapter);

            super.onPostExecute(itemFoods);
            dialog.dismiss();
        }

        @Override
        protected List<ItemFood> doInBackground(Integer... params) {
            dbHandler = new DataBaseHandler(getActivity());
            arrayList = (ArrayList<ItemFood>) dbHandler.getAreaFood(params[0]);
            return arrayList;
        }
    }

    public class LoadListAsyntask extends AsyncTask<Void, Void, List<ItemFood>> {

        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<ItemFood> itemFoods) {
            recyclerFood = (RecyclerView) getActivity().findViewById(R.id.recycler_listfood);
            recyclerFood.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerFood.setLayoutManager(layoutManager);
            recyclerAdapter = new RecyclerAdapter(arrayList, getActivity());
            recyclerFood.setAdapter(recyclerAdapter);

            super.onPostExecute(itemFoods);
            dialog.dismiss();
        }

        @Override
        protected List<ItemFood> doInBackground(Void... params) {
            dbHandler = new DataBaseHandler(getActivity());
            arrayList = (ArrayList<ItemFood>) dbHandler.getAllListFood();
            return arrayList;
        }
    }
}
