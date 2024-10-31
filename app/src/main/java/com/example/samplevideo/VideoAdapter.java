package com.example.samplevideo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


public class VideoAdapter extends BaseAdapter {
    Context context;
    List<VideoBean.ItemListBean> mDatas;

    /**
     * 构造方法
     * @param context 上下文（一般就是窗口）
     * @param mDatas 储存电影数据源的集合（可直接new一个空列表）
     */

    public VideoAdapter(Context context, List<VideoBean.ItemListBean>mDatas){
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mainlv,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        VideoBean.ItemListBean.DataBean dataBean = mDatas.get(position).getData();
        VideoBean.ItemListBean.DataBean.AuthorBean author = dataBean.getAuthor();
        holder.nameTv.setText(author.getName());
        holder.descTv.setText(author.getDescription());
        String iconURL = author.getIcon();
        if(!TextUtils.isEmpty(iconURL)){
            //icon verified and able to be loaded
            Picasso.with(context).load(iconURL).into(holder.iconIv);
        }

        holder.jzvdStd.setUp(dataBean.getPlayUrl(),dataBean.getTitle(), Jzvd.SCREEN_FULLSCREEN);
        String thumbUrl = dataBean.getCover().getFeed();
        Picasso.with(context).load(thumbUrl).into(holder.jzvdStd.posterImageView);
        holder.jzvdStd.positionInList = position;

        return convertView;
    }

    class ViewHolder{
        JzvdStd jzvdStd;
        ImageView iconIv;
        TextView nameTv, descTv;
        public ViewHolder(View view){
            jzvdStd = view.findViewById(R.id.item_main_jzvd);
            iconIv = view.findViewById(R.id.item_main_iv);
            nameTv = view.findViewById(R.id.item_main_tv_name);
            descTv = view.findViewById(R.id.item_main_tv_des);
        }
    }
}
