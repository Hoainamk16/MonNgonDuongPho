package trinhhoainam.tttn.com.monngonduongpho.item;

/**
 * Created by HoaiNam on 11/02/2017.
 */

public class ItemSlidingMenu {
    private int mIcons;
    private String mTitle;

    public ItemSlidingMenu(int mIcons, String mTitle) {
        this.mIcons = mIcons;
        this.mTitle = mTitle;
    }

    public int getmIcons() {
        return mIcons;
    }

    public void setmIcons(int mIcons) {
        this.mIcons = mIcons;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
