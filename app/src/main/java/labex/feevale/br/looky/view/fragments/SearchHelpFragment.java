package labex.feevale.br.looky.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.dao.SubjectDao;
import labex.feevale.br.looky.model.SearchItem;
import labex.feevale.br.looky.model.Subject;
import labex.feevale.br.looky.model.UserProfile;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.adapters.ResultSearchItemListAdapter;
import labex.feevale.br.looky.view.adapters.SubjectsSpinnerAdapter;

/**
 * Created by grimmjowjack on 8/17/15.
 */
public class SearchHelpFragment extends BaseFragment implements AdapterView.OnItemSelectedListener{

    private EditText searchEditText;
    private ListView resultList;
    private Spinner spinner;
    private SubjectsSpinnerAdapter spinnerAdapter;
    private ResultSearchItemListAdapter adapter;

    List<Subject> subjects = new ArrayList<>();
    private Subject defaultSubject;
    private JsonUtils jsonUtils;
    private SearchItem searchItem;

    public SearchHelpFragment() {

        jsonUtils = new JsonUtils();
        searchItem = new SearchItem();

        defaultSubject = new Subject(0L,"");
        subjects.add(defaultSubject);
        subjects.addAll(new SubjectDao().listAll());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        resultList = (ListView) view.findViewById(R.id.searchResultList);
        spinner = (Spinner) view.findViewById(R.id.subjectsSearchSpinner);

        searchEditText = (EditText) view.findViewById(R.id.searchHelpEditText);
        searchEditText.setOnEditorActionListener(searchActionListener());
        searchEditText.setOnFocusChangeListener(searchFocusActionListener());
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("PROFILES", (ArrayList<UserProfile>) adapter.getProfiles());
        outState.putParcelableArrayList("SUBJECTS", (ArrayList<Subject>) subjects);
        outState.putParcelable("ITEM_SEARCH", searchItem);
    }


    private View.OnFocusChangeListener searchFocusActionListener() {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(searchEditText.getText().length() > 0)
                    performSearch(searchItem.resetPositions(searchEditText.getText().toString()));
            }
        };
    }

    private TextView.OnEditorActionListener searchActionListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    ((MainActivity)getActivity()).closeKeyboard();
                    performSearch(searchItem.resetPositions(searchEditText.getText().toString()));
                    return true;
                }
                return false;
            }
        };
    }

    private void performSearch(SearchItem searchItem) {
        if(searchItem.param != null && searchItem.param.length() > 0)
            new SearchHelpTask(getActivity(), jsonUtils.searchItemToJson(searchItem)).makeRequest();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ResultSearchItemListAdapter(getActivity());
        spinnerAdapter = new SubjectsSpinnerAdapter(subjects,getActivity());

        resultList.setOnItemClickListener(adapter);
        resultList.setAdapter(adapter);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        this.searchItem.subject = ((Subject)spinnerAdapter.getItem(position)).getId();
        performSearch(searchItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        this.searchItem.subject = null;
    }

    public class SearchHelpTask extends RequestHandler<List<UserProfile>>{

        protected SearchHelpTask(Context context, String params) {
            super(new ArrayList<UserProfile>(), context, AppVariables.SEARCH_HELP, RequestHandler.GET, params);
        }

        @Override
        protected void error(MessageResponse messageResponse) {
            Toast.makeText(getActivity(), messageResponse.getMsg(), Toast.LENGTH_LONG).show();
            adapter.setProfiles(new ArrayList<UserProfile>());
        }

        @Override
        protected List<UserProfile> postExecuteCore(String response) {
            return jsonUtils.jsonToUserProfileList(response);
        }

        @Override
        protected void close(List<UserProfile> userProfiles) {
            if(userProfiles != null)
                adapter.setProfiles(userProfiles);
        }
    }



}
