package com.example.yinshuai.sharinganimation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yinshuai.sharinganimation.model.MyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    private static Context mContext;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
    }

    /**
     * 初始化View
     */
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter m = new MyAdapter(mContext);
        recyclerView.setAdapter(m);

        m.setOnItemOnclick(new MyAdapter.OnItemOnclick() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onclick(String url, String name, View image, View names) {
                Intent in = new Intent();
                Bundle b = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                        Pair.create((View) image, "image"),
                        Pair.create((View) names, "name")).toBundle();
                in.setClass(MainActivity.this, ActivityDetails.class);
                in.putExtra("url", url);
                in.putExtra("name", name);
                startActivity(in, b);
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onlongcolick(String url, String name, View image, View names) {
                Intent in = new Intent();
                Bundle b = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, Pair.create((View) image, "image"), Pair.create((View) names, "name")).toBundle();
                in.setClass(MainActivity.this, ActivityDetails.class);
                in.putExtra("url", url);
                in.putExtra("name", name);
                startActivity(in, b);
            }


        });
    }

    /**
     * recyclerAdapter
     */
    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<MyModel> list;
        private OnItemOnclick on;

        public interface OnItemOnclick {
            void onclick(String url, String name, View image, View names);

            void onlongcolick(String url, String name, View image, View names);
        }

        public void setOnItemOnclick(OnItemOnclick on) {
            this.on = on;
        }

        public MyAdapter(Context context) {
            list = getData();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final MyModel m = list.get(position);
            holder.name.setText(m.getName());
            holder.sub.setText(m.getSub());
            holder.time.setText(m.getTime());
            Glide.with(mContext).load(m.getUrl()).into(holder.imageView);


            if (on != null) {
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        on.onclick(m.getUrl(), m.getName(), holder.imageView, holder.name);
                    }
                });

                holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        on.onlongcolick(m.getUrl(), m.getName(), holder.imageView, holder.name);
                        return true;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView name;
            public TextView time;
            public TextView sub;
            public LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);
                name = (TextView) itemView.findViewById(R.id.name);
                time = (TextView) itemView.findViewById(R.id.time);
                sub = (TextView) itemView.findViewById(R.id.sub);
                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }


    /**
     * 初始化数据
     *
     * @return 数据集合
     */
    public static List<MyModel> getData() {
        List<MyModel> list = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 20; i++) {
            MyModel myModel = new MyModel();
            myModel.setName("标题" + i);
            myModel.setTime("2017-8-" + i);
            myModel.setSub("这只是一个标题，用来凑字数的");

            int number = rand.nextInt(5);
            Log.e("-----", "number:" + number);
            switch (number) {
                case 0:
                    myModel.setUrl("http://k2.jsqq.net/uploads/allimg/1704/7_170420145644_11.jpg");
                    break;
                case 1:
                    myModel.setUrl("http://img.wmtp.net/wp-content/uploads/2015/04/9860c41b0ef41bd59c5f7b7055da81cb38db3da0.png");
                    break;
                case 2:
                    myModel.setUrl("http://www.fuhaodq.com/d/file/qqtx/20160727/jjcjystn1gjsa3i.jpg");
                    break;
                case 3:
                    myModel.setUrl("http://img.qqbody.com/uploads/allimg/201311/19-080150_954.jpg");
                    break;
                case 4:
                    myModel.setUrl("http://img1.touxiang.cn/uploads/20121017/17-080933_507.jpg");
                    break;
            }
            list.add(myModel);
        }

        return list;
        //http://k2.jsqq.net/uploads/allimg/1704/7_170420145644_11.jpg
        //http://img.wmtp.net/wp-content/uploads/2015/04/9860c41b0ef41bd59c5f7b7055da81cb38db3da0.png
        //http://www.fuhaodq.com/d/file/qqtx/20160727/jjcjystn1gjsa3i.jpg
        //http://img1.touxiang.cn/uploads/20121017/17-080933_507.jpg
        //http://img.qqbody.com/uploads/allimg/201311/19-080150_954.jpg
    }

}
