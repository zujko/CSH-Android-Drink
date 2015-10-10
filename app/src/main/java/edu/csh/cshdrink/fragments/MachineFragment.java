package edu.csh.cshdrink.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.csh.androiddrink.R;
import edu.csh.cshdrink.DividerItemDecoration;
import edu.csh.cshdrink.DrinkApplication;
import edu.csh.cshdrink.adapters.ItemAdapter;
import edu.csh.cshdrink.models.BulkMachineData;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MachineFragment extends Fragment {
    public static final String MACHINE_ID = "MACHINE_ID";
    private int machine;
    ItemAdapter adapter;
    @Bind(R.id.machine_item_list) RecyclerView mRecyclerView;

    public static MachineFragment newInstance(int machineId) {
        Bundle args = new Bundle();
        args.putInt(MACHINE_ID, machineId);
        MachineFragment fragment = new MachineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.machine = getArguments().getInt(MACHINE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_machine_layout,container,false);
        ButterKnife.bind(this, rootView);

        adapter = new ItemAdapter(null);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        Call<BulkMachineData> bulkMachineDataCall = DrinkApplication.API.getBulkMachineData();
        bulkMachineDataCall.enqueue(new Callback<BulkMachineData>() {
            @Override
            public void onResponse(Response<BulkMachineData> response, Retrofit retrofit) {
                BulkMachineData.MachineData data = response.body().data;
                switch (machine) {
                    case 1:
                        adapter.addItems(data.littleDrink);
                        break;
                    case 2:
                        adapter.addItems(data.bigDrink);
                        break;
                    case 3:
                        adapter.addItems(data.snack);
                        break;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("MachineFragment","Retrofit Failure");
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
