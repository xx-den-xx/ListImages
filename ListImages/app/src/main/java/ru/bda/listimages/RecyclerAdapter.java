package ru.bda.listimages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<String> mImages;
    private Context mContext;
    private int mCount;

    public RecyclerAdapter(Context context,List<String> images, int count) {
        this.mImages = images;
        this.mContext = context;
        this.mCount = count;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int widhtScreen =  ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        int changedWidth = widhtScreen / mCount;
        holder.imageView.setMaxWidth(changedWidth);
        holder.imageView.setMaxHeight(changedWidth);
        Picasso.with(mContext)
                .load(mImages.get(position))
                .centerCrop()
                .resize(changedWidth, changedWidth)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public void setImagesList(List<String> images) {
        this.mImages = images;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
