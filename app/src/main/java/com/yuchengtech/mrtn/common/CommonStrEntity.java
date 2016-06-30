package com.yuchengtech.mrtn.common;

import android.os.Parcel;
import android.os.Parcelable;

public class CommonStrEntity implements Parcelable {

    public static final Parcelable.Creator<CommonStrEntity> CREATOR = new Parcelable.Creator<CommonStrEntity>() {

        @Override
        public CommonStrEntity createFromParcel(Parcel source) {
            return new CommonStrEntity(source.readInt(), source.readString());
        }

        @Override
        public CommonStrEntity[] newArray(int size) {
            return new CommonStrEntity[size];
        }

    };
    public int id;
    public String name;

    public CommonStrEntity() {

    }

    public CommonStrEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

}
