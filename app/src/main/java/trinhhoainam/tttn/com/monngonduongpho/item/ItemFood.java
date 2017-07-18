package trinhhoainam.tttn.com.monngonduongpho.item;

import java.io.Serializable;

/**
 * Created by HoaiNam on 13/02/2017.
 */

public class ItemFood implements Serializable {
    private int id;
    private byte[] hinhAnh;
    private String tieuDe, diaChi, timer, price, content;
    private int favorites;
    private String latitude, longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ItemFood(int id, byte[] hinhAnh, String tieuDe, String diaChi, String timer, String price, String content, int favorites, String latitude, String longitude) {
        this.id = id;
        this.hinhAnh = hinhAnh;
        this.tieuDe = tieuDe;
        this.diaChi = diaChi;
        this.timer = timer;
        this.price = price;
        this.content = content;
        this.favorites = favorites;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ItemFood(int id, byte[] hinhAnh, String tieuDe, String diaChi) {
        this.id = id;
        this.hinhAnh = hinhAnh;
        this.tieuDe = tieuDe;
        this.diaChi = diaChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
