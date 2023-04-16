package com.lianyi.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TokenBean implements Parcelable {

    /**
     * access_token : 86206333-0c2f-40e7-9d15-553567f83f56
     * token_type : bearer
     * refresh_token : 9d847a3d-c1c0-4d7a-bf39-09d92396c383
     * expires_in : 3599
     * scope : read write
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String  userName;
    private String passWord;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeString(this.token_type);
        dest.writeString(this.refresh_token);
        dest.writeInt(this.expires_in);
        dest.writeString(this.scope);
        dest.writeString(this.userName);
        dest.writeString(this.passWord);
    }

    public TokenBean() {
    }

    protected TokenBean(Parcel in) {
        this.access_token = in.readString();
        this.token_type = in.readString();
        this.refresh_token = in.readString();
        this.expires_in = in.readInt();
        this.scope = in.readString();
        this.userName = in.readString();
        this.passWord = in.readString();
    }

    public static final Parcelable.Creator<TokenBean> CREATOR = new Parcelable.Creator<TokenBean>() {
        @Override
        public TokenBean createFromParcel(Parcel source) {
            return new TokenBean(source);
        }

        @Override
        public TokenBean[] newArray(int size) {
            return new TokenBean[size];
        }
    };
}
