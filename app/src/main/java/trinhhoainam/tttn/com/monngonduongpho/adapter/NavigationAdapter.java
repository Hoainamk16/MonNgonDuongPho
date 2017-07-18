package trinhhoainam.tttn.com.monngonduongpho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import trinhhoainam.tttn.com.monngonduongpho.R;
import trinhhoainam.tttn.com.monngonduongpho.item.ItemSlidingMenu;

/**
 * Created by HoaiNam on 11/02/2017.
 */

public class NavigationAdapter extends BaseAdapter {
    private Context mContext;
    private List<ItemSlidingMenu> listMenu;

    public NavigationAdapter(Context mContext, List<ItemSlidingMenu> listMenu) {
        this.mContext = mContext;
        this.listMenu = listMenu;
    }

    @Override
    public int getCount() {
        return listMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return listMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;
        if (v == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.item_navigation, null);
            viewHolder.txtTitle = (TextView) v.findViewById(R.id.txt_title);
            viewHolder.imvIcon = (ImageView) v.findViewById(R.id.imv_icons);
            v.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        ItemSlidingMenu itemSlidingMenu = listMenu.get(position);
        viewHolder.txtTitle.setText(itemSlidingMenu.getmTitle());
        viewHolder.imvIcon.setImageResource(itemSlidingMenu.getmIcons());

        return v;
    }

    public static class ViewHolder {
        TextView txtTitle;
        ImageView imvIcon;
    }
}
