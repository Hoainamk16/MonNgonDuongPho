package trinhhoainam.tttn.com.monngonduongpho.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import trinhhoainam.tttn.com.monngonduongpho.R;
import trinhhoainam.tttn.com.monngonduongpho.adapter.PagerAdapterFood;
import trinhhoainam.tttn.com.monngonduongpho.item.ItemFood;

/**
 * Created by HoaiNam on 05/03/2017.
 */

public class PageFood extends AppCompatActivity {
    ViewPager pager;
    ArrayList<ItemFood> arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_food);

        // set title for actionbar
        getSupportActionBar().setTitle("Chi tiết");

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());


        DataBaseHandler handler = new DataBaseHandler(this);
        arrayList = (ArrayList<ItemFood>) handler.getAllListFood();
        PagerAdapterFood pagerAdapterFood = new PagerAdapterFood(this, arrayList);
        pager.setAdapter(pagerAdapterFood);

        //get position
        int po_id = getIntent().getExtras().getInt("POSITION_ID");

        int idCurrent = 0;

        for (int i = 0; i < arrayList.size(); i++) {
            int idOfList = arrayList.get(i).getId();
            if (po_id == idOfList) {
                idCurrent = i;
                break;
            }
        }

        pager.setCurrentItem(idCurrent);

    }
}
