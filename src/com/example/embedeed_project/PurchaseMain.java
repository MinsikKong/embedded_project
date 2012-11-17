package com.example.embedeed_project;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseMain extends Activity {
	// 구매(박민성)
	private ArrayList<ProductItemBean> productArray;
	private ArrayList<OrderItemBean> orderArray;
	private ProductItemCustomAdapter productAdapter;
	private OrderItemCustomAdapter orderAdapter;
	private GridView productList;
	private ListView orderList;
	TextView totalPriceText;
	private int totalPrice = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_main);
		
		//총금액 출력부분
		totalPriceText = (TextView)findViewById(R.id.totalPrice);
		setTotalPriceText();
		
		// 버튼처리
		Button backButton = (Button) findViewById(R.id.backButton);
		Button barcodeButton = (Button)findViewById(R.id.barcodeButton);
		Button resetButton = (Button)findViewById(R.id.resetButton);
		Button payButton = (Button)findViewById(R.id.payButton);
		
		//뒤로가기
		backButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		//바코드 인식
		barcodeButton.setOnClickListener(new Button.OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View v) {
				// 오픈소스 "ZXing"을 이용한 "Barcode Scanner"
				// app(https://zxing.googlecode.com/files/BarcodeScanner4.31.apk)을
				// intent로 실행, 결과 받아옴
				final Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.setPackage("com.google.zxing.client.android");
				startActivityForResult(intent, 0);
			}
		});
		
		//구매목록 리셋
		resetButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				int orderAmount;
				int indexOfProduct;
				
				for(int j=0;j<orderArray.size();j++){
					orderAmount = orderArray.get(j).amount;
					indexOfProduct = orderArray.get(j).productIndex;
					if(productArray.get(indexOfProduct)==null){
						Toast.makeText(PurchaseMain.this, "주문상품이 상품목록에 존재하지 않습니다.", Toast.LENGTH_LONG).show();
					} else {
						productArray.get(indexOfProduct).stock += orderAmount;
					}
				}
				orderArray.removeAll(orderArray);
				if(orderArray.isEmpty()){
					Toast.makeText(PurchaseMain.this, "리셋 되었습니다.", Toast.LENGTH_SHORT).show();
				}

				orderList.setAdapter(orderAdapter);
				productList.setAdapter(productAdapter);
				totalPrice = 0;
				setTotalPriceText();
			}
		});
		
		//구매화면으로
		payButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PurchaseMain.this,
						PurchasePay.class);
				startActivity(intent);
			}
		});
		
		// 리스트 처리
		productList = (GridView) findViewById(R.id.productList);
		orderList = (ListView) findViewById(R.id.orderList);
		productArray = new ArrayList<ProductItemBean>();
		orderArray = new ArrayList<OrderItemBean>();

		ProductItemBean item = new ProductItemBean(14124, "까페모카", "8801056956011", 50, 12);
		ProductItemBean item2 = new ProductItemBean(23125, "아메리카노", "4007817504598", 30, 7);
		ProductItemBean item3 = new ProductItemBean(31545, "까라멜 마끼아토", "1234567890123", 20, 5);
		
		productArray.add(item);
		productArray.add(item2);
		productArray.add(item3);

		productAdapter = new ProductItemCustomAdapter(this, R.layout.product_gridview, productArray);
		orderAdapter = new OrderItemCustomAdapter(this, R.layout.order_listview, orderArray);
		productList.setAdapter(productAdapter);
		orderList.setAdapter(orderAdapter);

		// 상품 리스트 선택시 주문 리스트로 이동
		productList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int i,
					long id) {
				addProductToOrder(i);
			}
		});

		// 구매리스트 선택시 구매 수량 감소
		orderList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int i,
					long id) {
				int selctedAmount = orderArray.get(i).amount;
				int selectedProductCode = orderArray.get(i).productCode;
				int selectedProductIndex = orderArray.get(i).productIndex;
				if(selctedAmount>1){
					orderArray.get(i).amount--;								//주문내역의 저장된 제품값 1감소
					orderArray.get(i).totalPrice -= orderArray.get(i).singlePrice;
					productArray.get(selectedProductIndex).stock++;//주문내역에 저장된 제품의 인덱스값 받아와서 재고 1증가
					totalPrice -= orderArray.get(i).singlePrice;
				} else if(selctedAmount==1){
					//현재 수량이 1일때 리스트에서 삭제 시킴
					for(int j=0;j<productArray.size();j++){
						if(productArray.get(j).productCode==selectedProductCode){
							//상품 배열속의 상품코드가 선택된 주문의 상품코드와 일치하면
							orderArray.remove(i);						  //선택된 주문내역 삭제
							productArray.get(selectedProductIndex).stock++;//해당 재고 1증가
							totalPrice -= productArray.get(selectedProductIndex).price;
						}
					}
				} else {
					Toast.makeText(PurchaseMain.this, "뭔가 문제가 있는것 같다.",
							Toast.LENGTH_LONG);
				}
				orderList.setAdapter(orderAdapter);
				productList.setAdapter(productAdapter);
				setTotalPriceText();
			}

		});
		
	}
	//해당 상품을 주문목록에 넣어줌
	private void addProductToOrder(int productIndex){
		Boolean isProductExist = false;
		int itemIndexInOrder = 0;//주문목록내의 인덱스
		if (productArray.get(productIndex).stock > 0) {
			// 현재 재고가 존재함
			for (int j = 0; j < orderArray.size(); j++) {
				// 현재 상품목록에 선택된 상품이 있는지 확인
				if (orderArray.get(j).productCode == productArray
						.get(productIndex).productCode) {
					isProductExist = true;
					itemIndexInOrder = j;
				}
			}
			if (!isProductExist) {
				// 현재 주문내역에 상품이 존재하지 않으면 상품 추가하고 상품재고1내림
				OrderItemBean item = new OrderItemBean(productArray
						.get(productIndex).productCode,
						productIndex,
						productArray.get(productIndex).productName, 1,
						productArray.get(productIndex).price,
						productArray.get(productIndex).price);
				orderArray.add(item);
				productArray.get(productIndex).stock--;
			} else {
				// 현재 상품이 존재하면 상품수량 1늘리고 상품 총 가격 바꾸고 상품재고 1내림
				orderArray.get(itemIndexInOrder).amount++;
				orderArray.get(itemIndexInOrder).totalPrice = orderArray
						.get(itemIndexInOrder).amount
						* orderArray.get(itemIndexInOrder).singlePrice;
				productArray.get(productIndex).stock--;
			}
			orderList.setAdapter(orderAdapter);
			productList.setAdapter(productAdapter);
			totalPrice += productArray.get(productIndex).price;
			setTotalPriceText();
		} else {
			Toast.makeText(PurchaseMain.this, "재고가 없습니다.",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	//바코드를 통해 해당 상품의 인덱스를 가져옴(없을시 -1)
	private int findProductIndexByBarcode(String productCode){
		for(int i=0;i<productArray.size();i++){
			if(productCode.equals(productArray.get(i).productBarCode)){
				return i;
			}
		}
		return -1;
	}
	
	//현재 총금액을 텍스트로 세팅해줌
	private void setTotalPriceText(){
		totalPriceText.setText(totalPrice+"원");
	}
	
	// 주문목록작성을 위한 아이템빈
	class OrderItemBean {
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
	}

	// 상품 선택을 위한 아이템빈
	class ProductItemBean {
		public int productCode; // 상품코드
		public String productName;// 상품명
		public String productBarCode;
		public int price; // 가격
		public int stock; // 재고

		public ProductItemBean(int productCode, String productName, String productBarcode, int price,
				int stock) {
			this.productCode = productCode;
			this.productName = productName;
			this.price = price;
			this.stock = stock;
			this.productBarCode = productBarcode;
		}
	}

	public class OrderItemCustomAdapter extends BaseAdapter {
		Context context;
		LayoutInflater inflater;

		ArrayList<OrderItemBean> arrayList = new ArrayList<OrderItemBean>();

		TextView itemNameText, amountText, singlePriceText, totalPriceText;

		private int layout;

		public OrderItemCustomAdapter(Context context, int layout,
				ArrayList<OrderItemBean> arrayList) {
			this.context = context;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layout = layout;
			this.arrayList = arrayList;
		}

		public int getCount() {
			return arrayList.size();
		}

		public Object getItem(int position) {
			return arrayList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int i, View convertView, ViewGroup parent) {
			final int finalPosition = i;
			if (convertView == null) {
				convertView = inflater.inflate(layout, parent, false);
			}
			itemNameText = (TextView) convertView
					.findViewById(R.id.orderItemName);
			amountText = (TextView) convertView.findViewById(R.id.amount);
			singlePriceText = (TextView) convertView
					.findViewById(R.id.singlePrice);
			totalPriceText = (TextView) convertView
					.findViewById(R.id.totalPrice);
			itemNameText.setText(arrayList.get(i).itemName);
			amountText.setText("" + arrayList.get(i).amount);
			singlePriceText.setText("" + arrayList.get(i).singlePrice);
			totalPriceText.setText("" + arrayList.get(i).totalPrice);
			return convertView;
		}
	}

	public class ProductItemCustomAdapter extends BaseAdapter {
		Context context;
		LayoutInflater Inflater;

		ArrayList<ProductItemBean> arrayList = new ArrayList<ProductItemBean>();

		TextView productNameText, priceText, stockText;

		private int layout;

		public ProductItemCustomAdapter(Context context, int layout,
				ArrayList<ProductItemBean> arrayList) {
			this.context = context;
			Inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layout = layout;
			this.arrayList = arrayList;
		}

		public int getCount() {
			return arrayList.size();
		}

		public Object getItem(int position) {
			return arrayList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int i, View convertView, ViewGroup parent) {
			final int finalPosition = i;
			if (convertView == null) {
				convertView = Inflater.inflate(layout, parent, false);
			}
			productNameText = (TextView) convertView
					.findViewById(R.id.productName);
			priceText = (TextView) convertView.findViewById(R.id.stock);
			stockText = (TextView) convertView.findViewById(R.id.price);
			productNameText.setText(productArray.get(i).productName);
			priceText.setText("" + productArray.get(i).stock);
			stockText.setText("" + productArray.get(i).price);
			return convertView;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		//바코드 인식결과에 따라 상품을 추가함
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String scannedCode = intent.getStringExtra("SCAN_RESULT"); 		//바코드 스캔결과 받아옴
				int scannedProductIndex = findProductIndexByBarcode(scannedCode);//해당 스캔결과로 상품목록의 인덱스 찾기
				if(scannedProductIndex==-1){
					//스캔이 결과 해당 상품이 목록에 없을시
					Toast.makeText(PurchaseMain.this, "해당 상품이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
				} else {
					//상품이 목록에 있을시
					addProductToOrder(scannedProductIndex);//주문목록에 추가
				}
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(PurchaseMain.this, "Cancel",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}