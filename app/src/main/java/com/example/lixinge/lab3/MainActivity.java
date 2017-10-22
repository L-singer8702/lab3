package com.example.lixinge.lab3;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.FloatProperty;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView myRV;
    private List<Map<String, Object>> data = new ArrayList<>();
    private rcyCommonAdapter commonAdapter;
    private List<String> weight;

    private FloatingActionButton shoplist;

    private ListView myLV;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        /*商品列表recycleview*/
        myRV = (RecyclerView) findViewById(R.id.goodList);
        myRV.setLayoutManager(new LinearLayoutManager(this));
        myRV.setHasFixedSize(true);

        commonAdapter = new rcyCommonAdapter<Map<String, Object>>(this, R.layout.activity_item, data) {
            @Override
            public void convert(rcyViewHolder holder, Map<String, Object> stringObjectMap) {
                TextView first = holder.getView(R.id.firstletter);
                first.setText(stringObjectMap.get("first").toString());
                TextView name = holder.getView(R.id.goodsname);
                name.setText(stringObjectMap.get("name").toString());
            }
        };

        commonAdapter.setOnItemClickListener(new rcyCommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("name", data.get(position).get("name").toString());
                intent.putExtra("price", data.get(position).get("price").toString());
                intent.putExtra("weight", data.get(position).get("weight").toString());
                intent.setClass(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
                data.remove(position);
                Toast.makeText(MainActivity.this, "移除第" + position + "个商品", Toast.LENGTH_SHORT).show();
                commonAdapter.notifyDataSetChanged();
            }
        });

        myRV.setAdapter(commonAdapter);
        /*商品列表recycleview*/

        /*购物车按钮floatingactionbutton*/
        final boolean[] flag = {false};
        shoplist = (FloatingActionButton) findViewById(R.id.shop);
        shoplist.setImageResource(R.drawable.shoplist);
        shoplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag[0]) {
                   myRV.setVisibility(View.GONE);
                    myLV.setVisibility(View.VISIBLE);
                    shoplist.setImageResource(R.drawable.mainpage);
                    flag[0] = !flag[0];
                }
                else{
                    myLV.setVisibility(View.GONE);
                    myRV.setVisibility(View.VISIBLE);
                    shoplist.setImageResource(R.drawable.shoplist);
                    flag[0] = !flag[0];
                }
            }
        });
        /*购物车按钮floatingactionbutton*/

        /*购物车列表listview*/
        myLV = (ListView) findViewById(R.id.cart);
        myLV.setVisibility(View.INVISIBLE);
        adapter = new SimpleAdapter(this, data, R.layout.activity_item, new String[]{"first", "name", "price"}, new int[]{
                R.id.firstletter, R.id.goodsname, R.id.goodsprice});
        myLV.setAdapter(adapter);
        /*购物车列表listview*/
    }

    protected void initData() {
        String[] firstLetter = new String[]{"E", "A", "D", "K", "W", "M", "F"};
        String[] goodsName = new String[]{"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片",
                "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
        String[] goodsprice = new String[]{"￥5.00 元", "￥59.00 元", "￥79.00 元", "￥2399.00 元", "￥179.00 元",
                "￥14.90 元", "￥132.59 元", "￥141.43 元", "￥139.43 元", "￥28.90 元"};
        String[] goodsweight = new String[]{"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8GB", "重量 2kg",
                "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};
        //ImageView[] itemImg = new ImageView[]{};

        for (int i = 0; i < 10; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("first", firstLetter[i]);
            temp.put("name", goodsName[i]);
            temp.put("price", goodsprice[i]);
            temp.put("weight", goodsweight[i]);
            data.add(temp);
        }
    }

    public abstract static class rcyCommonAdapter<T> extends RecyclerView.Adapter<rcyViewHolder> {
        protected Context mContext;
        protected int mLayoutId;
        protected List<T> mDatas;
        protected LayoutInflater mInflater;
        private OnItemClickListener mOnItemClickListener;

        public rcyCommonAdapter(Context context, int layoutId, List<T> datas) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mLayoutId = layoutId;
            mDatas = datas;
        }

        @Override
        public rcyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rcyViewHolder viewHolder = rcyViewHolder.get(mContext, parent, mLayoutId);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final rcyViewHolder holder, int position) {
            convert(holder, mDatas.get(position));
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onClick(holder.getAdapterPosition());
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                        return false;
                    }
                });
            }
        }

        public abstract void convert(rcyViewHolder holder, T t);

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public interface OnItemClickListener {
            void onClick(int position);

            void onLongClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

    }

}
