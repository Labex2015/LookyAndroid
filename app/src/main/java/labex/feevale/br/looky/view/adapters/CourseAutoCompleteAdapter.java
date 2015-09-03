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
import labex.feevale.br.looky.model.Subject;

/**
 * Created by 0126128 on 07/07/2015.
 */
public class CourseAutoCompleteAdapter extends BaseAdapter implements Filterable{

    private List<Subject> subjects, coursesBackUp;
    private Activity activity;

    public CourseAutoCompleteAdapter(List<Subject> subjects, Activity activity) {
        this.subjects = subjects;
        this.coursesBackUp = subjects;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int i) {
        return subjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.course_item_autocomplete, null);
        TextView course = (TextView) view.findViewById(R.id.courseAutocompleteTextView);
        TextView degree = (TextView) view.findViewById(R.id.degreeAutocompleteTextView);

        course.setText(subjects.get(i).getName());
        degree.setText(subjects.get(i).getDegree() != null ? subjects.get(i).getDegree().getName() : "");
        return view;
    }

    @Override
    public Filter getFilter() {
        final BaseAdapter adapter = this;
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                List<Subject> coursesFiltered = new ArrayList<Subject>();
                for(Subject c : subjects) {
                    if (c.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        coursesFiltered.add(c);
                }
                filterResults.values = coursesFiltered;
                filterResults.count = coursesFiltered.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    subjects = (List<Subject>) filterResults.values;
                    adapter.notifyDataSetChanged();
                } else {
                    subjects = coursesBackUp;
                    adapter.notifyDataSetInvalidated();
                }
            }
        };
    }
}
