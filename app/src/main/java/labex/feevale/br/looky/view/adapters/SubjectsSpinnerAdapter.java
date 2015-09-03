package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Subject;

/**
 * Created by grimmjowjack on 8/27/15.
 */
public class SubjectsSpinnerAdapter implements SpinnerAdapter{

    List<Subject> subjects;
    private Context activity;

    public SubjectsSpinnerAdapter(List<Subject> subjects, Context activity) {
        this.subjects = subjects;
        this.activity = activity;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup viewGroup) {
        View rowView =  ((Activity)activity).getLayoutInflater().inflate(R.layout.item_subject_list, viewGroup, false);

        TextView subject = (TextView)rowView.findViewById(R.id.subjectTextView);
        subject.setText(subjects.get(position).getName());

        if(subjects.get(position).getDegree() != null) {
            TextView degree = (TextView) rowView.findViewById(R.id.subjectDegreeTextView);
            degree.setText(subjects.get(position).getDegree().getName());
        }
        return rowView;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects.addAll(subjects);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView =  null;
        if(subjects.get(position).getId() > 0) {
            rowView = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_subject_list, viewGroup, false);
            TextView subject = (TextView) rowView.findViewById(R.id.subjectTextView);
            subject.setText(subjects.get(position).getName());

            if (subjects.get(position).getDegree() != null) {
                TextView degree = (TextView) rowView.findViewById(R.id.subjectDegreeTextView);
                degree.setText(subjects.get(position).getDegree().getName());
            }
        }else{
            rowView = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_subject_list_empty, viewGroup, false);
        }
        return rowView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return subjects.size();
    }

    @Override
    public boolean isEmpty() {
        return subjects.isEmpty();
    }
}
