package andronerds.com.contestapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import andronerds.com.contestapp.R;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/27/15
 */
public class SettingsListAdapter extends ArrayAdapter
{
    private final int BLUETOOTH_CONNECTION_ROW = 0;

    public SettingsListAdapter(Context context, int resource, ArrayList<String> objects)
    {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.d("GET_VIEW", "List view is being instantiated.");
        View row = convertView;
        LayoutInflater inflater = null;
        int type = getItemViewType(position);

        if(row == null)
        {
            if(type == BLUETOOTH_CONNECTION_ROW)
            {
                Log.d("ROW_VIEW", "Bluetooth row is instantiating");
                inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.settings_bluetooth_obd, null);
            }
        }

        if(type == BLUETOOTH_CONNECTION_ROW)
        {
            TextView connectStatusText = (TextView) row.findViewById(R.id.bluetooth_status);
            connectStatusText.setText("This is working");
        }
        return row;
    }


    @Override
    public int getItemViewType(int position)
    {
        if(position == 0)
        {
            return BLUETOOTH_CONNECTION_ROW;
        }
        return BLUETOOTH_CONNECTION_ROW;
    }
}
