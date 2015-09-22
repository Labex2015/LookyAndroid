package labex.feevale.br.looky.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.model.UserProfile;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.adapters.ResultSearchItemListAdapter;
import labex.feevale.br.looky.view.adapters.SubjectsSpinnerAdapter;

/**
 * Created by grimmjowjack on 8/17/15.
 */
public class SearchHelpFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText searchEditText;
    private Spinner spinner;
    private SubjectsSpinnerAdapter spinnerAdapter;

    private View newRequestHelpPanel;
    private Button globalButton;

    private RecyclerView resultList;
    private ResultSearchItemListAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<Subject> subjects = new ArrayList<>();
    private Subject defaultSubject;
    private JsonUtils jsonUtils;
    private SearchItem searchItem;


    private Boolean changed;
    private User user;

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
        resultList = (RecyclerView) view.findViewById(R.id.searchResultList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        resultList.setLayoutManager(mLayoutManager);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.search_panel);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        spinner = (Spinner) toolbar.findViewById(R.id.subjectsSearchSpinner);

        searchEditText = (EditText) toolbar.findViewById(R.id.searchHelpEditText);
        searchEditText.setOnEditorActionListener(searchActionListener());

        newRequestHelpPanel = view.findViewById(R.id.newRequestHelpPanel);
        newRequestHelpPanel.setVisibility(View.GONE);
        globalButton = (Button) newRequestHelpPanel.findViewById(R.id.newRequestHelpButton);
        globalButton.setOnClickListener(loadGlobalHelpListener());

        return view;
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("PROFILES", (ArrayList<UserProfile>) adapter.getProfiles());
        outState.putParcelableArrayList("SUBJECTS", (ArrayList<Subject>) subjects);
        outState.putParcelable("ITEM_SEARCH", searchItem);
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

        if(searchItem.param != null && searchItem.param.length() > 0) {
            String address = AppVariables.SEARCH_HELP.replace("#ID", user.getId().toString())
                            .replace("#PARAM", searchItem.param)
                            .replace("#SUBJECT", searchItem.subject + "")
                            .replace("#POSITION", searchItem.position + "")
                            .replace("#MAX", searchItem.max + "");

            new SearchHelpTask(getActivity(), address).makeRequest();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ResultSearchItemListAdapter(getActivity(), this);
        spinnerAdapter = new SubjectsSpinnerAdapter(subjects,getActivity());

        resultList.setAdapter(adapter);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        user = new SharedPreferencesUtils().getUser(getActivity());
        verifyPanel();
    }

    private void verifyPanel() {
        if(searchEditText.getText().length() > 0 && adapter.getProfiles().isEmpty())
            newRequestHelpPanel.setVisibility(View.VISIBLE);
        else
            newRequestHelpPanel.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        this.searchItem.param = searchEditText.getText().toString();
        this.searchItem.subject = ((Subject) spinnerAdapter.getItem(position)).getId();
        performSearch(searchItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        this.searchItem.subject = null;
    }

    @Override
    public void onClick(View view) {
        adapter.loadProfile(resultList.getChildAdapterPosition(view));
    }

    private View.OnClickListener loadGlobalHelpListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGlobalHelpPanel();
            }
        };
    }

    private void openGlobalHelpPanel(){
        ((MainActivity)getActivity()).changeFragment(new ReqGlobalHelpFragment(searchItem.param, new TaskExtraAction<MessageResponse>() {
            @Override
            public void onPostExecuteSuccess(MessageResponse... params) {
                if (params != null && params.length > 0)
                    setMessage(params[0].getMsg());

                ((MainActivity) getActivity()).changeFragment(new MainFragment());
            }

            @Override
            public void onPostExecuteError(MessageResponse param) {
                setMessage(param.getMsg());
            }
        }));
    }

    private void setMessage(final String message){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public class SearchHelpTask extends RequestHandler<List<UserProfile>>{

        protected SearchHelpTask(Context context, String url) {
            super(new ArrayList<UserProfile>(), context, url, RequestHandler.GET);
        }

        @Override
        protected void error(MessageResponse messageResponse) {
            Toast.makeText(getActivity(), messageResponse.getMsg(), Toast.LENGTH_LONG).show();
            adapter.setProfiles(new ArrayList<UserProfile>());
            verifyPanel();
        }

        @Override
        protected List<UserProfile> postExecuteCore(String response) {
            return jsonUtils.jsonToUserProfileList(response);
        }

        @Override
        protected void close(List<UserProfile> userProfiles) {
            if(userProfiles != null)
                adapter.setProfiles(userProfiles);
            verifyPanel();
        }
    }


}
