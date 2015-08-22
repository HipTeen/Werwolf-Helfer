package thorsten.yahya.werwolf.listViewUtil;

/**
 * Created by HipTeen on 29.07.2015.
 */

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import thorsten.yahya.werwolf.R;

public class MyAdapter extends ArrayAdapter<Model> {

    private final Context context;
    private final List<Model> modelsArrayList;

    public MyAdapter(Context context, List<Model> modelsArrayList) {

        super(context, R.layout.target_item, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        rowView = inflater.inflate(R.layout.target_item, parent, false);

        // 3. Get icon,title & counter views from the rowView
        TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
        final EditText numberPicker = (EditText) rowView.findViewById(R.id.number_picker);
        final RadioGroup radioGroup = (RadioGroup) rowView.findViewById(R.id.segmented2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton viewById = (RadioButton) group.findViewById(checkedId);
                CharSequence selectedOption = viewById.getText();
                if (selectedOption.equals("Mehr")) {
                    modelsArrayList.get(position).setCount(Integer.parseInt(numberPicker.getText().toString()));
                    numberPicker.setEnabled(true);
                } else {
                    modelsArrayList.get(position).setCount(Integer.parseInt(selectedOption.toString()));
                    numberPicker.setEnabled(false);
                }
            }
        });

        numberPicker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int intValue = 0;
                String value = numberPicker.getText().toString();
                if (!value.equals("")) {
                    intValue = Integer.parseInt(value);
                }
                modelsArrayList.get(position).setCount(intValue);
                radioGroup.check(R.id.number_role_more);
            }
        });
        // 4. Set the text for textView
        titleView.setText(modelsArrayList.get(position).getTitle());

        // 5. retrn rowView
        return rowView;
    }


}
