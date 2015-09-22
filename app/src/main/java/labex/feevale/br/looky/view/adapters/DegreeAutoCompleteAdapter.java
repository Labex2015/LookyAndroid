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
import labex.feevale.br.looky.model.Degree;

import labex.feevale.br.looky.service.utils.Observer;
import labex.feevale.br.looky.service.utils.Subject;
/**
 * Created by grimmjowjack on 9/16/15.
 */
public class DegreeAutoCompleteAdapter extends BaseAdapter implements Filterable, Subject {


    private List<Degree> degrees;
    private Activity activity;
    private List<Degree> coursesBackUp;
    private Degree selected;

    private Observer observer;

    public DegreeAutoCompleteAdapter(List<Degree> degrees, Activity activity) {
        this.degrees = degrees;
        this.coursesBackUp = degrees;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return degrees.size();
    }

    @Override
    public Object getItem(int i) {
        return degrees.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.degree_autocomplete, null);
        TextView degree = (TextView) view.findViewById(R.id.degreeAutoCompleteProfile);
        degree.setText(degrees.get(position).getName());
        return view;
    }

    @Override
    public Filter getFilter() {
        final BaseAdapter adapter = this;
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                List<Degree> degreesFiltered = new ArrayList<Degree>();
                for(Degree d : degrees) {
                    if (d.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        degreesFiltered.add(d);

                    if(d.getName().equals(charSequence.toString()))
                       selected = d;
                }
                filterResults.values = degreesFiltered;
                filterResults.count = degreesFiltered.size();

                notifyObservers();
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    degrees = (List<Degree>) filterResults.values;
                    adapter.notifyDataSetChanged();
                } else {
                    degrees = coursesBackUp;
                    adapter.notifyDataSetInvalidated();
                }
            }
        };
    }

    @Override
    public void register(Observer obj) {
        this.observer = obj;
    }

    @Override
    public void unregister(Observer obj) {
        this.observer = null;
    }

    @Override
    public void notifyObservers() {
        this.observer.update();
    }

    @Override
    public Object getUpdate(Observer obj) {
        return selected;
    }
}
