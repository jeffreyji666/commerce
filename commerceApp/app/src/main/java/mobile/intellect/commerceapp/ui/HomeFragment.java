package mobile.intellect.commerceapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import mobile.intellect.commerceapp.R;

/**
 * Created by you on 10/3/14.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private RelativeLayout dailyElec;
    private RelativeLayout kitchenElec;
    private RelativeLayout personalHealth;
    private RelativeLayout usefulTool;
    private RelativeLayout mobileComm;
    private RelativeLayout mobileAccessory;
    private RelativeLayout digitAccessory;
    private RelativeLayout intellectDevice;
    private RelativeLayout computer;
    private RelativeLayout computerAccessory;
    private RelativeLayout networkDevice;
    private RelativeLayout officePrinter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.home, container, false);

        dailyElec = (RelativeLayout) mView.findViewById(R.id.daily_elec);
        kitchenElec = (RelativeLayout) mView.findViewById(R.id.kitchen_elec);
        personalHealth = (RelativeLayout) mView.findViewById(R.id.personal_health);
        usefulTool = (RelativeLayout) mView.findViewById(R.id.useful_tool);
        mobileComm = (RelativeLayout) mView.findViewById(R.id.mobile_comm);
        mobileAccessory = (RelativeLayout) mView.findViewById(R.id.mobile_accessory);
        digitAccessory = (RelativeLayout) mView.findViewById(R.id.digit_accessory);
        intellectDevice = (RelativeLayout) mView.findViewById(R.id.intellect_device);
        computer = (RelativeLayout) mView.findViewById(R.id.computer);
        computerAccessory = (RelativeLayout) mView.findViewById(R.id.computer_accessory);
        networkDevice = (RelativeLayout) mView.findViewById(R.id.network_device);
        officePrinter = (RelativeLayout) mView.findViewById(R.id.office_printer);


        dailyElec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        kitchenElec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        personalHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        usefulTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        mobileComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        mobileAccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        digitAccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        intellectDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        computerAccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        networkDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        officePrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return mView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
}
