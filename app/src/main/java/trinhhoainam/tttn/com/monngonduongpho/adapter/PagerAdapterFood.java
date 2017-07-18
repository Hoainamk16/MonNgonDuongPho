package trinhhoainam.tttn.com.monngonduongpho.adapter;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import trinhhoainam.tttn.com.monngonduongpho.R;
import trinhhoainam.tttn.com.monngonduongpho.activity.DataBaseHandler;
import trinhhoainam.tttn.com.monngonduongpho.item.ItemFood;
import trinhhoainam.tttn.com.monngonduongpho.maps.TimDuongActivity;
import trinhhoainam.tttn.com.monngonduongpho.util.Utils;

/**
 * Created by HoaiNam on 25/02/2017.
 */

public class PagerAdapterFood extends PagerAdapter implements View.OnClickListener {

    /*------------------------------------------------------------*/
    private Context context;
    private ArrayList<ItemFood> arrayFood;
    private Dialog dialog;
    private int vTri = 0;

    public PagerAdapterFood(Context context, ArrayList<ItemFood> arrayFood) {
        this.context = context;
        this.arrayFood = arrayFood;
    }

    @Override
    public int getCount() {
        return arrayFood.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //   super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.info_food, container, false);

        Holder holder = new Holder();
        holder.txtTieuDe = (TextView) v.findViewById(R.id.txt_tieude);
        holder.txtDiaChi = (TextView) v.findViewById(R.id.txt_diachi);
        holder.txtGiaTien = (TextView) v.findViewById(R.id.txt_gia_tien);
        holder.txtGioMoCua = (TextView) v.findViewById(R.id.txt_giomocua);
        holder.txtNoiDung = (TextView) v.findViewById(R.id.txt_noidung);
        holder.bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);

        holder.imgHinhAnh = (ImageView) v.findViewById(R.id.image_hinhanh);

        holder.txtTieuDe.setText(arrayFood.get(position).getTieuDe());
        holder.txtDiaChi.setText(arrayFood.get(position).getDiaChi());
        holder.txtNoiDung.setText(arrayFood.get(position).getContent());
        holder.txtGioMoCua.setText(arrayFood.get(position).getTimer());
        holder.txtGiaTien.setText(arrayFood.get(position).getPrice());


        Bitmap bm = BitmapFactory.decodeByteArray(arrayFood.get(position).getHinhAnh(), 0, arrayFood.get(position).getHinhAnh().length);
        holder.imgHinhAnh.setImageBitmap(bm);

        Menu menu = holder.bottomNavigationView.getMenu();
        if (arrayFood.get(position).getFavorites() == 1) {
            menu.findItem(R.id.favorites_menu).setIcon(R.drawable.lover);
        }

        holder.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.maps_menu:

                        if (Utils.isInternetAvailable(context) == true) {

                            Intent goToMaps = new Intent(context, TimDuongActivity.class);
                            if (arrayFood.get(position).getLatitude() != null && arrayFood.get(position).getLongitude() != null) {
                                goToMaps.putExtra("LATITUDE", arrayFood.get(position).getLatitude());
                                goToMaps.putExtra("LONGITUDE", arrayFood.get(position).getLongitude());
                                goToMaps.putExtra("TITLE_FOOD", arrayFood.get(position).getTieuDe());
                                goToMaps.putExtra("ADDRESS_FOOD", arrayFood.get(position).getDiaChi());
                            } else {
                                goToMaps.putExtra("LATITUDE", "0");
                                goToMaps.putExtra("LONGITUDE", "0");
                            }
                            context.startActivity(goToMaps);

                            Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
                        } else if (Utils.isInternetAvailable(context) == false) {

                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Thông báo !");
                            builder.setMessage("Để dùng chức năng này, thiết bị của bạn cần phải được kết nối internet !" +
                                    "\nxin vui lòng kết nối internet và thử lại ...");
                            builder.setIcon(android.R.drawable.ic_dialog_info);
                            builder.setCancelable(false);
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();

                        }


                        break;
                    case R.id.favorites_menu:
                        int favorite = arrayFood.get(position).getFavorites();
                        switch (favorite) {
                            case 0:
                                favorite = 1;
                                new DataBaseHandler(context).UpdateFavorites(arrayFood.get(position).getId(), favorite);
                                arrayFood.get(position).setFavorites(favorite);

                                Toast.makeText(context, "Đã thêm vào yêu thích !", Toast.LENGTH_SHORT).show();
                                item.setIcon(R.drawable.lover);
                                break;
                            case 1:
                                favorite = 0;
                                new DataBaseHandler(context).UpdateFavorites(arrayFood.get(position).getId(), favorite);
                                arrayFood.get(position).setFavorites(favorite);
                                Toast.makeText(context, "Đã xoá khỏi yêu thích !", Toast.LENGTH_SHORT).show();
                                item.setIcon(R.drawable.likein);
                                break;
                            default:
                                break;

                        }
                        break;
                    case R.id.share_menu:
                        vTri = position;
                        showDialogShare();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        container.addView(v);
        return v;

    }

    private void showDialogShare() {
        dialog = new Dialog(context, R.style.CustomAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.share_food);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.setCancelable(false);

        ImageView imgGmail, imgGplus, imgFace, imgSMS;
        imgFace = (ImageView) dialog.findViewById(R.id.share_food_face);
        imgGmail = (ImageView) dialog.findViewById(R.id.share_food_gmail);
        imgGplus = (ImageView) dialog.findViewById(R.id.share_food_gplus);
        imgSMS = (ImageView) dialog.findViewById(R.id.share_food_sms);

        imgFace.setOnClickListener(this);
        imgGmail.setOnClickListener(this);
        imgGplus.setOnClickListener(this);
        imgSMS.setOnClickListener(this);
        Button btnDimis = (Button) dialog.findViewById(R.id.btn_share_food_dimiss);
        btnDimis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static boolean checkConnect(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_food_face:
                shareFace();
                break;
            case R.id.share_food_gmail:
                shareGmail();
                break;
            case R.id.share_food_gplus:
                shareGPlus();
                break;
            case R.id.share_food_sms:
                shareSms();
                break;
            default:
                break;
        }
    }

    private void shareFace() {

        try {
            String facebookScheme = "fb://profile/" + "hoainama4";
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
            context.startActivity(facebookIntent);
        } catch (ActivityNotFoundException e) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=" + "hoainama4"));
            context.startActivity(facebookIntent);
        }

    }

    private void shareGPlus() {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/109342017591291584508/posts")));

    }

    private void shareGmail() {
        String loiMoi = "Giới thiệu cho bạn món ăn :\n";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/html");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, arrayFood.get(vTri).getTieuDe());
        intent.putExtra(Intent.EXTRA_TEXT, loiMoi + arrayFood.get(vTri).getTieuDe() + "\n" + arrayFood.get(vTri).getDiaChi());
        context.startActivity(Intent.createChooser(intent, "Send Email"));
        dialog.dismiss();
    }

    private void shareSms() {

        String loiMoi = "Giới thiệu cho bạn món ăn :\n";
        String sh = loiMoi + arrayFood.get(vTri).getTieuDe() + "\n" + arrayFood.get(vTri).getDiaChi();
        Intent share_sms = new Intent(Intent.ACTION_VIEW);
        share_sms.setData(Uri.parse("sms:"));
        share_sms.setType("vnd.android-dir/mms-sms");
        share_sms.putExtra("sms_body", sh);
        share_sms.putExtra("address", "");
        context.startActivity(share_sms);
        dialog.dismiss();
    }

    public class Holder {
        private TextView txtTieuDe, txtDiaChi, txtGioMoCua, txtGiaTien, txtNoiDung;
        private ImageView imgHinhAnh;
        private BottomNavigationView bottomNavigationView;
    }


}
