package labex.feevale.br.looky.view.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Area;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.Subject;
import labex.feevale.br.looky.service.impl.ManagerKnowledge;
import labex.feevale.br.looky.service.utils.ExtraAction;
import labex.feevale.br.looky.service.utils.HttpActions;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.adapters.AreaAutoCompleteAdapter;
import labex.feevale.br.looky.view.adapters.CourseAutoCompleteAdapter;
import labex.feevale.br.looky.view.adapters.KnowledgesUserAdapter;
import labex.feevale.br.looky.view.filters.AlphabeticInputTextFilter;

/**
 * Created by 0126128 on 06/07/2015.
 */
public class KnowledgeFragment extends BaseFragment implements HttpActions<Knowledge>, ExtraAction<Knowledge>{


    private KnowledgesUserAdapter knowledgesUserAdapter;
    private BaseAdapter listAdapter;

    private ListView knowledgeListView;
    private LinearLayout panel;
    private FloatingActionButton addKnowledgeButton;
    private AutoCompleteTextView areaTextView, subjectTextView;

    private List<Area> areas;
    private List<Subject> subjects;
    private List<Knowledge> knowledges;

    private Area selectedArea;
    private Subject selectedSubject;
    private Knowledge knowledgeSelected;

    private CourseAutoCompleteAdapter courseAutoCompleteAdapter;
    private AreaAutoCompleteAdapter areaAutoCompleteAdapter;


    public KnowledgeFragment() {
    }

    public KnowledgeFragment(List<Knowledge> knowledges, List<Area> areas, List<Subject> subjects) {
        this.knowledges = knowledges;
        this.areas = areas;
        this.subjects = subjects;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            this.knowledges = savedInstanceState.getParcelableArrayList("KNOWLEDGES");
            this.areas = savedInstanceState.getParcelableArrayList("AREAS");
            this.subjects = savedInstanceState.getParcelableArrayList("SUBJECTS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.knowledge_fragment, container, false);
        panel = (LinearLayout)view.findViewById(R.id.knowledgeFormPanel);

        knowledgeListView = (ListView) view.findViewById(R.id.knowledgeUserListView);

        areaTextView = (AutoCompleteTextView) view.findViewById(R.id.areaText);
        areaTextView.setThreshold(1);
        areaAutoCompleteAdapter = new AreaAutoCompleteAdapter(areas, getActivity());
        areaTextView.setAdapter(areaAutoCompleteAdapter);
        areaTextView.setFilters(new InputFilter[]{new AlphabeticInputTextFilter()});
        areaTextView.setOnItemClickListener(selectAreaEventListener());


        subjectTextView = (AutoCompleteTextView) view.findViewById(R.id.subjectText);
        subjectTextView.setThreshold(1);

        courseAutoCompleteAdapter = new CourseAutoCompleteAdapter(subjects, getActivity());
        subjectTextView.setAdapter(courseAutoCompleteAdapter);
        subjectTextView.setFilters(new InputFilter[]{new AlphabeticInputTextFilter()});
        subjectTextView.setOnItemClickListener(selectCourseEventListener());

        addKnowledgeButton = (FloatingActionButton) view.findViewById(R.id.addKnowledgeButton);
        addKnowledgeButton.setOnClickListener(saveKnowledgeEventListener());

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("KNOWLEDGES", (ArrayList<Knowledge>) knowledges);
        outState.putParcelableArrayList("AREAS", (ArrayList<Area>) areas);
        outState.putParcelableArrayList("SUBJECTS", (ArrayList<Subject>) subjects);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            this.knowledges = savedInstanceState.getParcelableArrayList("KNOWLEDGES");
            this.knowledgeListView.setAdapter(knowledgesUserAdapter);

            this.areas = savedInstanceState.getParcelableArrayList("AREAS");
            areaAutoCompleteAdapter = new AreaAutoCompleteAdapter(areas, getActivity());

            this.subjects = savedInstanceState.getParcelableArrayList("SUBJECTS");
            courseAutoCompleteAdapter = new CourseAutoCompleteAdapter(subjects, getActivity());
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState == null){
            this.knowledgesUserAdapter = new KnowledgesUserAdapter(knowledges, getActivity(), this);
            this.knowledgeListView.setAdapter(knowledgesUserAdapter);
            setRetainInstance(true);
        }

    }

    private AdapterView.OnItemClickListener selectCourseEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSubject = (Subject) adapterView.getItemAtPosition(i);
                subjectTextView.setText(selectedSubject.getName());
            }
        };
    }

    private AdapterView.OnItemClickListener selectAreaEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedArea = (Area) adapterView.getItemAtPosition(i);
                areaTextView.setText(selectedArea.getName());
            }
        };
    }

    private View.OnClickListener saveKnowledgeEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).closeKeyboard();
                if(areaTextView.getText().length() > 0)
                    saveKnowledge();
                else
                    areaTextView.setError(getActivity().getString(R.string.knowledge_form_area_required));
            }
        };
    }

    private void saveKnowledge(){
        if(selectedArea == null || !selectedArea.getName()
                .equals(areaTextView.getText().toString())){
            selectedArea = new Area(null, areaTextView.getText().toString());
        }
        Knowledge knowledge = new Knowledge(selectedArea, selectedSubject);
        knowledge.setIdUser(new SharedPreferencesUtils().getUSer(getActivity()).getId());
        processRequest(knowledge);
    }

    private void processRequest(Knowledge knowledge) {
        new ManagerKnowledge(ManagerKnowledge.INSERT, getActivity(), this, knowledge).execute();
    }

    private void clear(){
        subjectTextView.setText("");
        areaTextView.setText("");
        this.selectedSubject = null;
        this.selectedArea = null;
    }

    @Override
    public void afterDelete(MessageResponse messageResponse) {
        if(messageResponse != null && messageResponse.getStatus()) {
            this.knowledgesUserAdapter.getKnowledges().remove(knowledgeSelected);
        }
        notify(messageResponse);
    }

    @Override
    public void afterInsertUpdate(Knowledge knowledge) {
        clear();
        notify(null);
        this.knowledgesUserAdapter.getKnowledges().add(knowledge);
    }

    @Override
    public void afterList(Knowledge knowledge) {
        //TODO: implementar com a paginação
    }

    @Override
    public void fail(MessageResponse messageResponse) {
        notify(messageResponse);
    }

    private void notify(final MessageResponse messageResponse){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(messageResponse != null)
                    Toast.makeText(getActivity(), messageResponse.getMsg(), Toast.LENGTH_LONG).show();

                knowledgesUserAdapter.notifyDataSetChanged();
                knowledgeSelected = null;

            }
        });

    }

    @Override
    public void executeAction(Knowledge... params) {
        if(params != null && params.length > 0){
            knowledgeSelected = params[0];
            new ManagerKnowledge(ManagerKnowledge.DELETE, getActivity(), this,knowledgeSelected)
                    .execute();
        }
    }

}
