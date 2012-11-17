package com.example.embedeed_project;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;


// 주문목록작성을 위한 아이템빈
public class OrderItemBean implements Parcelable{
	public int productCode; // 상품코드
	public int productIndex; // 해당상품인덱스
	public String itemName;// 상품명
	public int amount; // 수량
	public int singlePrice; // 1개 가격
	public int totalPrice; // 총 가격

	public OrderItemBean(int productCode, int productIndex,
			String itemName, int amount, int singlePrice, int totalPrice) {
		this.productCode = productCode;
		this.productIndex = productIndex;
		this.itemName = itemName;
		this.amount = amount;
		this.singlePrice = singlePrice;
		this.totalPrice = totalPrice;
	}
	
	public OrderItemBean(Parcel in){
		this.productCode = in.readInt();
		this.productIndex = in.readInt();
		this.itemName = in.readString();
		this.amount = in.readInt();
		this.singlePrice = in.readInt();
		this.totalPrice = in.readInt();
	}

	public static final Parcelable.Creator<OrderItemBean> CREATOR = new Parcelable.Creator<OrderItemBean>() {
		public OrderItemBean createFromParcel(Parcel in){
			return new OrderItemBean(in);
		}

		@Override
		public OrderItemBean[] newArray(int size) {
			return new OrderItemBean[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(productCode);
		dest.writeInt(productIndex);
		dest.writeString(itemName);
		dest.writeInt(amount);
		dest.writeInt(singlePrice);
		dest.writeInt(totalPrice);
	}
}
