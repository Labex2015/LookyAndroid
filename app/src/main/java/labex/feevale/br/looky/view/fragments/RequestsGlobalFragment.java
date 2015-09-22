package labex.feevale.br.looky.view.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.GlobalHelp;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.mod.GlobalMod;
import labex.feevale.br.looky.service.utils.CallbackTask;
import labex.feevale.br.looky.service.utils.HttpActions;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.adapters.GlobalHelpAdapter;
import labex.feevale.br.looky.view.custom.LookyDialog;
import labex.feevale.br.looky.view.dialogs.DialogActions;

/**
 * Created by grimmjowjack on 9/21/15.
 */
public class RequestsGlobalFragment extends BaseFragment implements CallbackTask<List<GlobalMod>>,
                                                                    View.OnClickListener {

    private MainActivity activity;
    private GlobalHelpAdapter adapter;
    private RecyclerView requestList;
    private RecyclerView.LayoutManager mLayoutManager;

    public RequestsGlobalFragment() {}

    public RequestsGlobalFragment(MainActivity mainActivity) {
        activity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.global_list_fragment, container, false);
        requestList = (RecyclerView) view.findViewById(R.id.globalHelpList);
        mLayoutManager =  new LinearLayoutManager(getActivity());
        requestList.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new GlobalHelpAdapter(getActivity(), this);
        new LoadRequestGlobal(this).execute();
        this.requestList.setAdapter(adapter);
    }

    @Override
    public void success(final List<GlobalMod> response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.getHelpList().addAll(response);
                adapter.notifyDataSetChanged();
            }
        });
    }

        @Override
        public void error(final MessageResponse response) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onClick(View view) {
            GlobalMod globalHelp = adapter.getHelpList().get(requestList.getChildAdapterPosition(view));
            ((MainActivity)getActivity()).loadDialog(globalHelp.title, globalHelp.description, new DialogActions() {
                @Override
                public void cancelAction() {

                }

                @Override
                public void confirmAction() {

                }
            });
        }


    class LoadRequestGlobal extends AsyncTask<Void, Void, Void> {

        private CallbackTask<List<GlobalMod>> callbackTask;

        public LoadRequestGlobal(CallbackTask<List<GlobalMod>> callbackTask) {
            this.callbackTask = callbackTask;
        }

        @Override
        protected void onPreExecute() {
            activity.manageProgressView(false, "Listando itens.....");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new RequestHandler<ArrayList<GlobalMod>>(new ArrayList<GlobalMod>(), activity,
                    AppVariables.URL_GLOBAL_REQUEST_HELP, RequestHandler.GET) {
                @Override
                protected void error(MessageResponse messageResponse) {
                    callbackTask.error(messageResponse);
                }

                @Override
                protected void close(ArrayList<GlobalMod> globalHelps) {
                    callbackTask.success(globalHelps);
                }

                @Override
                protected ArrayList<GlobalMod> postExecuteCore(String response) {
                    return (ArrayList<GlobalMod>) new JsonUtils().JsonToListGlobalMod(response);
                }
            }.makeRequest();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            activity.manageProgressView(true);
        }
    }
}
