package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.mod.GlobalMod;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.custom.RoundedImageView;

/**
 * Created by grimmjowjack on 9/21/15.
 */
public class GlobalHelpAdapter extends RecyclerView.Adapter<GlobalHelpAdapter.ViewHolder>{

    private Activity activity;
    private List<GlobalMod> helpList = new ArrayList<>();
    private View.OnClickListener listener;
    private User user;

    public GlobalHelpAdapter(Activity activity, View.OnClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        user = new SharedPreferencesUtils().getUser(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.global_request_item, parent, false);

        TextView username = (TextView)view.findViewById(R.id.globalItemUserName);
        TextView title = (TextView)view.findViewById(R.id.titleGlobalTextView);
        TextView description = (TextView)view.findViewById(R.id.descriptionGlobalTextView);
        RoundedImageView picture =  (RoundedImageView)view.findViewById(R.id.globalItemUserPicture);
        ImageButton deleteItem = (ImageButton) view.findViewById(R.id.globalDeleteItemButton);

        return new ViewHolder(view,username,description,title,picture, deleteItem, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(getHelpList().get(position).title);
        holder.description.setText(getHelpList().get(position).description);

        if(user.getId() == getHelpList().get(position).user.getId()) {
            holder.deleteItem.setVisibility(View.VISIBLE);
            holder.username.setText(R.string.you);
        }else{
            holder.username.setText(getHelpList().get(position).user.getUsername());
        }
        String url = getHelpList().get(position).user.getPicturePath();
//        if(url  != null && !url.isEmpty())
//            Picasso.with(activity).load(url).resize(100, 100)
//                    .centerCrop().into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }

    public List<GlobalMod> getHelpList() {
        return helpList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView title;
        public TextView description;
        public RoundedImageView picture;
        public ImageButton deleteItem;

        public ViewHolder(View itemView, TextView username,
                          TextView description, TextView title, RoundedImageView picture,
                          ImageButton  deleteItem, View.OnClickListener listener) {
            super(itemView);
            this.username = username;
            this.description = description;
            this.picture = picture;
            this.title = title;
            this.deleteItem = deleteItem;
            itemView.setOnClickListener(listener);
            this.deleteItem.setOnClickListener(listener);
        }
    }
}

