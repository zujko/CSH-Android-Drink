package edu.csh.cshdrink.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.csh.androiddrink.R;
import edu.csh.cshdrink.DividerItemDecoration;
import edu.csh.cshdrink.DrinkApplication;
import edu.csh.cshdrink.ItemClickSupport;
import edu.csh.cshdrink.adapters.ItemAdapter;
import edu.csh.cshdrink.models.BulkMachineData;
import edu.csh.cshdrink.models.Item;
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
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Item item = adapter.mItems.get(position);
                if(item.isEnabled() && item.isAvailable()) {
                    createDropDialog(item);
                } else {
                    Toast.makeText(getContext(),"Item is not available",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    /**
     * Creates the dialog to ask the user if they want to drop a drink
     * @param item
     */
    private void createDropDialog(final Item item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Drop Drink?");
        alert.setMessage("Drop a " + item.item_name + "?\nEnter seconds until drop");
        final AppCompatEditText text = new AppCompatEditText(getActivity());
        text.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(text);
        alert.setPositiveButton("DROP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String delay = text.getText().toString();
                if(delay.equals("") || delay.equals(" ")) {
                    delay = "0";
                }
               //TODO POST to drop drink
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.create().show();
    }
}
