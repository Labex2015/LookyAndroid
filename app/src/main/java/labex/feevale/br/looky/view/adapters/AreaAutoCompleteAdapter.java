package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Area;

/**
 * Created by 0126128 on 07/07/2015.
 */
public class AreaAutoCompleteAdapter extends BaseAdapter implements Filterable{

    private List<Area> areas;
    private List<Area> areasBackUp;
    private Activity activity;

    private Area areaSelected;

    public AreaAutoCompleteAdapter(List<Area> areas, Activity activity) {
        this.areas = areas;
        this.areasBackUp = areas;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return areas.size();
    }

    @Override
    public Object getItem(int i) {
        return areas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdapterViewHolder holder = null;
        if(view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.area_item_autocomplete, viewGroup, false);
            holder = new AdapterViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.areaAutocompTextView);
            view.setTag(holder);
        }else{
            holder = (AdapterViewHolder) view.getTag();
        }
        holder.textView.setText(areas.get(i).getName());
        return view;
    }


    @Override
    public Filter getFilter() {
        final BaseAdapter adapter = this;
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                List<Area> areasFilter = new ArrayList<Area>();
                for(Area a : areas) {
                    if (a.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        areasFilter.add(a);
                }
                filterResults.values = areasFilter;
                filterResults.count = areasFilter.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    areas = (List<Area>) filterResults.values;
                    adapter.notifyDataSetChanged();
                } else {
                    areas = areasBackUp;
                    adapter.notifyDataSetInvalidated();
                }
            }
        };
    }

    class AdapterViewHolder {
        public TextView textView;
    }
}
