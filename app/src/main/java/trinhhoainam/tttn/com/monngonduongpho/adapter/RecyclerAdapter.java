package trinhhoainam.tttn.com.monngonduongpho.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import trinhhoainam.tttn.com.monngonduongpho.R;
import trinhhoainam.tttn.com.monngonduongpho.activity.PageFood;
import trinhhoainam.tttn.com.monngonduongpho.item.ItemFood;

/**
 * Created by HoaiNam on 03/03/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<ItemFood> itemFoods;
    Context context;
    int lastPosition = -1;

    public RecyclerAdapter(ArrayList<ItemFood> itemFoods, Context context) {
        this.itemFoods = itemFoods;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.item_list_food, parent, false);
        return new ViewHolder(item, context, itemFoods);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtTitle.setText(itemFoods.get(position).getTieuDe());
        holder.txtAddress.setText(itemFoods.get(position).getDiaChi());
        Bitmap bm = BitmapFactory.decodeByteArray(itemFoods.get(position).getHinhAnh(), 0, itemFoods.get(position).getHinhAnh().length);
        holder.imgHinh.setImageBitmap(bm);


        //animation
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        } else if (position < lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return itemFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle, txtAddress;
        ImageView imgHinh;
        Context ctx;
        ArrayList<ItemFood> list;

        public ViewHolder(final View itemView, Context ctx, ArrayList<ItemFood> list) {
            super(itemView);
            this.ctx = ctx;
            this.list = list;

            itemView.setOnClickListener(this);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title_food_listfood);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address_food_listfood);
            imgHinh = (ImageView) itemView.findViewById(R.id.img_hinh_mon_an_listfood);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            int ido = list.get(pos).getId();
            Intent sendID = new Intent(context, PageFood.class);
            sendID.putExtra("POSITION_ID", ido);
            context.startActivity(sendID);
        }
    }
}
