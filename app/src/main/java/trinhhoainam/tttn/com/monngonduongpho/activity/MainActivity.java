package trinhhoainam.tttn.com.monngonduongpho.activity;

import android.app.Dialog;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import trinhhoainam.tttn.com.monngonduongpho.R;
import trinhhoainam.tttn.com.monngonduongpho.adapter.NavigationAdapter;
import trinhhoainam.tttn.com.monngonduongpho.fragment.DoChienFragment;
import trinhhoainam.tttn.com.monngonduongpho.fragment.DoNuongFragment;
import trinhhoainam.tttn.com.monngonduongpho.fragment.GiaiKhatFragment;
import trinhhoainam.tttn.com.monngonduongpho.fragment.TrangChuFragment;
import trinhhoainam.tttn.com.monngonduongpho.fragment.YeuThichFragment;
import trinhhoainam.tttn.com.monngonduongpho.item.ItemSlidingMenu;
import trinhhoainam.tttn.com.monngonduongpho.maps.MapsActivity;
import trinhhoainam.tttn.com.monngonduongpho.maps.TimDuongActivity;
import trinhhoainam.tttn.com.monngonduongpho.util.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<ItemSlidingMenu> listItem;
    private NavigationAdapter navigationAdapter;
    private ListView lvMenu;
    private Spinner spinnerDiaDiem;
    private TableRow tbKhuVuc;
    private Dialog dialog;
    private Boolean doubleClick = false;
    private ArrayList<String> listDiaDiem;
    private LinearLayout linearLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleButton;

    Fragment fragment = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbKhuVuc = (TableRow) findViewById(R.id.table_khuvuc);
        /* Spinner */
        spinnerDiaDiem = (Spinner) findViewById(R.id.spinner_khuvuc);
        listDiaDiem = new ArrayList<String>();
        listDiaDiem.addAll(Arrays.asList(getResources().getStringArray(R.array.quanhuyen)));
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listDiaDiem);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiaDiem.setAdapter(spinAdapter);

        spinnerDiaDiem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String area = position + "";
                Bundle bundle = new Bundle();
                bundle.putInt("AREA_CODE", position);
                fragment = new TrangChuFragment();
                fragment.setArguments(bundle);
                int fragmentManager = getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, fragment).commit();

                // Toast.makeText(MainActivity.this, area + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        linearLayout = (LinearLayout) findViewById(R.id.layout_menu_left);
        lvMenu = (ListView) findViewById(R.id.lv_menu_left);
        listItem = new ArrayList<ItemSlidingMenu>();
        addMenu();
        navigationAdapter = new NavigationAdapter(MainActivity.this, listItem);
        lvMenu.setAdapter(navigationAdapter);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);

        //display icon open /close sliding
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(listItem.get(0).getmTitle());                          // set title
        lvMenu.setItemChecked(0, true);                                 //item selected
        drawerLayout.closeDrawer(linearLayout);                          //close sliding

        replaceFrag(0);
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replaceFrag(position);
                setTitle(listItem.get(position).getmTitle());   //set title
                lvMenu.setItemChecked(position, true); // item selected
                drawerLayout.closeDrawer(linearLayout);
            }
        });
        toggleButton = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(toggleButton);


    }

    @Override
    public void onBackPressed() {
        if (doubleClick) {
            super.onBackPressed();
            finish();
            return;
        }
        this.doubleClick = true;
        Toast.makeText(this, "Please press back again to exit ", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClick = false;
            }
        }, 2000);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggleButton.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggleButton.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggleButton.onConfigurationChanged(newConfig);
    }

    private void replaceFrag(int hso) {

        switch (hso) {
            case 0:
                fragment = new TrangChuFragment();
                tbKhuVuc.setVisibility(View.VISIBLE);
                spinnerDiaDiem.setSelection(0);
                break;
            case 1:
                fragment = new YeuThichFragment();
                tbKhuVuc.setVisibility(View.GONE);
                break;
            case 2:
                fragment = new GiaiKhatFragment();
                break;
            case 3:
                fragment = new DoNuongFragment();
                break;
            case 4:
                fragment = new DoChienFragment();
                break;
            case 5:
                nextToMaps();

                break;
            case 6:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                  shareIntent.setType("message/rfc822");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                /*
                dialog = new Dialog(this, R.style.CustomAnimation);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.share_app);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(layoutParams);

                LinearLayout layoutSMS, layoutFacebook, layoutEmail, layoutGmail, layoutZalo, layoutTwitter, layoutGplus;

                layoutEmail = (LinearLayout) dialog.findViewById(R.id.share_app_email);
                layoutFacebook = (LinearLayout) dialog.findViewById(R.id.share_app_face);
                layoutGmail = (LinearLayout) dialog.findViewById(R.id.share_app_gmail);
                layoutGplus = (LinearLayout) dialog.findViewById(R.id.share_app_gplus);
                layoutSMS = (LinearLayout) dialog.findViewById(R.id.share_app_sms);
                layoutTwitter = (LinearLayout) dialog.findViewById(R.id.share_app_twitter);
                layoutZalo = (LinearLayout) dialog.findViewById(R.id.share_app_zalo);
                Button btnShaApp = (Button) dialog.findViewById(R.id.btn_share_app_dimiss);
                layoutEmail.setOnClickListener(this);
                layoutFacebook.setOnClickListener(this);
                layoutGmail.setOnClickListener(this);
                layoutGplus.setOnClickListener(this);
                layoutSMS.setOnClickListener(this);
                layoutTwitter.setOnClickListener(this);
                layoutZalo.setOnClickListener(this);
                btnShaApp.setOnClickListener(this);

                dialog.setCancelable(false);
                dialog.show();
                */
                break;
            case 7:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.info_app);
                dialog.setTitle("Thông tin ứng dụng !");
                Button btnDialog = (Button) dialog.findViewById(R.id.btn_ok_info);
                btnDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
                break;
            case 8:
                showExitDialog();
            default:
                //  frag = new TrangChuFragment();
                break;
        }
        if (null != fragment) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            FragmentTransaction replace = transaction.replace(R.id.layout_main, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }

    private void nextToMaps() {
        if (Utils.isInternetAvailable(this) == true) {
            Intent goToMaps = new Intent(MainActivity.this, TimDuongActivity.class);
            goToMaps.putExtra("LATITUDE", "0");
            goToMaps.putExtra("LONGITUDE", "0");
            startActivity(goToMaps);
        } else {
            Toast.makeText(this, "Bạn cần có kết nối internet", Toast.LENGTH_SHORT).show();
        }
    }


    private void showExitDialog() {
        dialog = new Dialog(this, R.style.CustomAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_exits);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        Button btnOK = (Button) dialog.findViewById(R.id.btn_ok_exit);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel_exit);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void addMenu() {
        listItem.add(new ItemSlidingMenu(R.drawable.house, "Trang Chủ"));
        listItem.add(new ItemSlidingMenu(R.drawable.lover, "Yêu Thích"));
        listItem.add(new ItemSlidingMenu(R.drawable.coffee, "Giải Khát"));
        listItem.add(new ItemSlidingMenu(R.drawable.barbecue, "Đồ Nướng"));
        listItem.add(new ItemSlidingMenu(R.drawable.tempura, "Đồ Chiên"));
        listItem.add(new ItemSlidingMenu(R.drawable.map, "Tìm Đường"));
        listItem.add(new ItemSlidingMenu(R.drawable.social, "Chia Sẻ"));
        listItem.add(new ItemSlidingMenu(R.drawable.information, "Thông Tin Ứng Dụng!"));
        listItem.add(new ItemSlidingMenu(R.drawable.exit, "Thoát"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_app_email:
                sendEmail("Mon Ngon Duong Pho", "developer Hoai Nam");
                Toast.makeText(this, "share_app_eamil", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_app_face:
                Toast.makeText(this, "share_app_face", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_app_gmail:
                Toast.makeText(this, "share_app_gmail", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_app_gplus:
                Toast.makeText(this, "share_app_gplus", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_app_sms:
                Toast.makeText(this, "share_app_sms", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_app_twitter:
                Toast.makeText(this, "share_app_twitter", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_app_zalo:
                Toast.makeText(this, "share_app_zalo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_share_app_dimiss:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    public void sendEmail(String subject, String text) {
        String[] TO = {""};
        String[] CC = {""};
        Intent mainIntent = new Intent(Intent.ACTION_SEND);
        mainIntent.setData(Uri.parse("mailto:"));
        mainIntent.setType("text/plain");
        mainIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        mainIntent.putExtra(Intent.EXTRA_CC, CC);
        mainIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mainIntent.putExtra(Intent.EXTRA_TEXT, text);

        try {
            startActivity(Intent.createChooser(mainIntent, "Send email..."));
            //   finish();

        } catch (Exception e) {
            Toast.makeText(this, e.toString() + "", Toast.LENGTH_SHORT).show();
        }
    }
}
